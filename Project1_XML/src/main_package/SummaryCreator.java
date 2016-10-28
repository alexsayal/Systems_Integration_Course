package main_package;
import generated_sum.*;
import generated_sum.ObjectFactory;
import generated.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class SummaryCreator {

	public static void main(String[] args) throws JAXBException {
		//--Initialise
		JAXBContext JC = JAXBContext.newInstance(Catalog.class); //For the original xml structure
		JAXBContext JC2 = JAXBContext.newInstance(CatalogSummary.class); //For the new xml stucture
		Unmarshaller unmarshaller = JC.createUnmarshaller();
		Marshaller marshaller = JC2.createMarshaller();
		SchemaFactory schemafactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = null;

		System.out.println("|| ========== SUMMARY CREATOR ========== ||");
		
		//--Files
		File xml_input = new File("src/Files/Output.xml");
		File xsd_input = new File("src/Files/Source_XSD.xsd");
		File xml_output = new File("src/Files/Final_Output.xml");

		//--Validation
		try {
			schema = schemafactory.newSchema(xsd_input);
			Validator validator = schema.newValidator();
			try {
				validator.validate(new StreamSource(xml_input));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("XML and XSD Validated Successfully.");
		} catch (SAXException e1) {
			System.out.println("Error in XML: " + e1.getMessage());
			if(e1.getMessage().equals("cvc-complex-type.2.4.b: The content of element 'catalog' is not complete. One of '{researcher}' is expected.")){ //XML sem informação
				System.out.println("Final_Output.xml file created without information.");
			} else //Erro de estrutura XML-XSD
				System.exit(0);
		}

		//--Unmarshal
		Catalog catalog = (Catalog) unmarshaller.unmarshal(xml_input);
		unmarshaller.setSchema( schema );

		//--Creator
		ObjectFactory fabrica = new ObjectFactory();
		CatalogSummary catalog_sum = fabrica.createCatalogSummary(); //New catalog with new structure

		List<Researcher> list = catalog.getResearcher(); //Lista de researchers
		ArrayList<String> topics = new ArrayList<String>(); //Lista de interesses

		int num = catalog.getResearcher().size();
		int num_citations = 0;
		int rescit[][] = new int[2][num]; //Matriz com indices dos researchers (l1) e respectivo numero de citacoes (l2)
		
		// Create list of interests and count citations
		for(int i=0;i<num;i++){
			num_citations = 0;
			List<String> topic = list.get(i).getInterests().getTopic();
			for(int ii=0;ii<topic.size();ii++){
				if (!topics.contains(topic.get(ii)))
					topics.add(topic.get(ii));
			}
			int num_art = list.get(i).getArticles().getArticle().size();
			
			for(int j=0; j<num_art; j++){
				num_citations = num_citations + list.get(i).getArticles().getArticle().get(j).getCited().intValue();
			}
			rescit[0][i] = i;
			rescit[1][i] = num_citations;
		}
		Collections.sort(topics);
		
		// Add Interests to catalog
		int num_top = topics.size();
		InterestsSummary aux_list = fabrica.createInterestsSummary();
		
		for(int i=0;i<num_top;i++){
			Interest aux = new Interest();
			ResearcherSummary listresaux = new ResearcherSummary();
			ArrayList<Res> resaux = new ArrayList<Res>(); //Lista de researchers em Res
			ArrayList<String> name_compare = new ArrayList<String>(); //Lista de nomes em String
			
			//Name of topic
			aux.setNameInt(topics.get(i));
			
			//For the rest
			for(int ii=0;ii<num;ii++){
				List<String> topic2 = list.get(ii).getInterests().getTopic();
				
				for(int iii=0;iii<topic2.size();iii++){
					if(topic2.get(iii).equals(topics.get(i))){
						name_compare.add(list.get(ii).getName());
						Collections.sort(name_compare);
						int ind_alph_order = name_compare.indexOf(list.get(ii).getName()); //Know where to add the researcher to the Array

						Res resaux_i = new Res();		
						resaux_i.setNameRes(list.get(ii).getName());
						resaux_i.setUniRes(list.get(ii).getUniversity());
						resaux_i.setArticles(list.get(ii).getArticles());
						
						resaux.add(ind_alph_order,resaux_i); //Add researcher by the order defined by ind_alph_order
					}
				}
			}
		
			for(int j=0;j<resaux.size();j++){
				listresaux.getRes().add(resaux.get(j)); //Adicionar lista de researchers ao ResearcherSummary
			}
			
			aux.setResearcherSummary(listresaux); // Add to Interest
			aux_list.getInterest().add(aux); //Add to Interest Summary
		}
		catalog_sum.setInterestsSummary(aux_list); //Add to Catalog
		
		// Add statistics
		catalog_sum.setNumcitations(BigInteger.valueOf(IntStream.of(rescit[1]).sum()));
		catalog_sum.setNumresearchers(BigInteger.valueOf(num));
		
		int[] max = {0,0,0};
		int[] index = {0,0,0};
		Topthree top = new Topthree();
		int a = 0;
		if(num>3)
			a = 3;
		else
			a = num;
		
		for(int b=0; b<a;b++){
			for (int jj = 0; jj < num; jj++) {
				if ( rescit[1][jj] > max[b]) {
					max[b] = rescit[1][jj];
					index[b] = rescit[0][jj];
				}
			}
			top.getTopname().add(list.get(index[b]).getName());
			rescit[1][index[b]] = 0;
		}
		catalog_sum.setTopthree(top);
		
		//--Marshal
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(catalog_sum, xml_output);
		if(num!=0) System.out.println("Final_Output.xml file created.");
	}
}