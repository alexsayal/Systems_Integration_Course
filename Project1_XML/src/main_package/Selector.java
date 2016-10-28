package main_package;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import generated.*;

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

public class Selector {

	public static void main(String[] args) throws JAXBException {
		//--Initialize
		JAXBContext JC = JAXBContext.newInstance(Catalog.class);
		Unmarshaller unmarshaller = JC.createUnmarshaller();
		Marshaller marshaller = JC.createMarshaller();
		Scanner scan = new Scanner(System.in);
		SchemaFactory schemafactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = null;

		System.out.println("|| ========== SELECTOR ========== ||");
		
		//--Files
		File xml_input = new File("src/Files/Source.xml");
		File xsd_input = new File("src/Files/Source_XSD.xsd");
		File xml_output = new File("src/Files/Output.xml");

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
			System.exit(0);
		}

		//--Unmarshal
		Catalog catalog = (Catalog) unmarshaller.unmarshal(xml_input);
		unmarshaller.setSchema( schema ); //Validação

		Catalog out_catalog = (Catalog) unmarshaller.unmarshal(xml_input); //Catalog a ser reduzido.

		//--User interaction
		int num = catalog.getResearcher().size(); //Numero de researchers

		List<Researcher> list = catalog.getResearcher(); //Lista de Researchers
		ArrayList<String> unis = new ArrayList<String>(); //Lista de Universidades
		ArrayList<String> topics = new ArrayList<String>(); //Lista de Interesses

		// For each researcher
		for(int i=0;i<num;i++){
			String uni = list.get(i).getUniversity();
			List<String> topic = list.get(i).getInterests().getTopic();

			if (!unis.contains(uni))
				unis.add(uni);
			for(int ii=0;ii<topic.size();ii++){
				if (!topics.contains(topic.get(ii)))
					topics.add(topic.get(ii));
			}
		}
		Collections.sort(topics);
		Collections.sort(unis);
		
		// Print
		System.out.println("");
		printColumns(unis,topics); //Método para print em colunas na consola
		System.out.println("---------------------------------");

		// User Scan
		System.out.print("Inserir Instituition: ");
		String inst = scan.nextLine().toLowerCase();
		
		ArrayList<String> interests_array = new ArrayList<String>();
		String interest = new String();
		System.out.println("Inserir Interesses, seguidos de Enter:");
		
		System.out.print("Interesse 1: ");
		interest = scan.nextLine().toLowerCase();
		
		while(interest.length()!=0){ //Ao inserir enter, prossegue
			interests_array.add(interest);
			System.out.print("Interesse seguinte: ");
			interest = scan.nextLine().toLowerCase();
		}
		
		String condition;
		if (inst.length()==0 || interests_array.size()==0){ //Se alguns deles for nulo, o OR é implicito.
			condition = "OR";
		}
		else{
			System.out.print("Inserir AND / OR: ");
			condition = scan.nextLine().toUpperCase();
		}

		ArrayList<Integer> indexes = new ArrayList<Integer>(); //Indices dos researchers a remover

		//--For each researcher	
		for(int j=0;j<num;j++){
			String test_uni = list.get(j).getUniversity().toLowerCase(); //Universidade do researcher j
			List<String> test_topic = list.get(j).getInterests().getTopic(); //Interesses do researcher j
			boolean check_uni = false;
			boolean check_int = false;

			if(test_uni.equals(inst) || (inst.length()==0 && interests_array.size()==0)){
				check_uni = true;
			}

			for(int ii=0;ii<test_topic.size();ii++){
				if (interests_array.contains(test_topic.get(ii).toLowerCase())){
					check_int = true;
				}
			}
			if(condition.equals("AND")){
				if(check_uni==false || check_int==false){
					indexes.add(j);
				}
			}
			else if(condition.equals("OR")){
				if(check_uni == false && check_int == false){
					indexes.add(j);
				}
			}
		}

		//--Remove unwanted researchers
		for(int ii=indexes.size()-1;ii>-1;ii--)
			out_catalog.removeResearcher(indexes.get(ii));

		//--Marshal
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(out_catalog, xml_output);
		scan.close();
		System.out.println("Output.xml file created.");
	}
	
	/**
     * Prints the elements of both Arraylist in two separate columns
     * 
     * @parameters
     *     Possible objects are
     *     {@link ArrayList }
     *     
     */
	public static void printColumns(ArrayList<String> U, ArrayList<String> I) {
		int dif = I.size() - U.size();
		for(int i=0;i<dif;i++){
			U.add("");
		}
		System.out.printf("%-35.35s    %-2.2s    %-40.40s%n","UNIVERSITIES:" ,"||", "INTERESTS:");
		System.out.printf("%-35.35s    %-2.2s    %-40.40s%n","-------------------------" ,"||", "-------------------------");
		for(int i=0;i<I.size();i++){
			System.out.printf("%-35.35s    %-2.2s    %-40.40s%n",U.get(i) ,"||", I.get(i));
		}
	}
}