package jpa;

import common.ArticleNew;
import common.InterestNew;
import common.Researcher;
import common.ResearcherNew;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Catalog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class JPAResearcherLoader {
	public static void main(String[] args) throws JAXBException {
		//--Initialize
		JAXBContext JC = JAXBContext.newInstance(Catalog.class);
		Unmarshaller unmarshaller = JC.createUnmarshaller();

		SchemaFactory schemafactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = null;
		
		//--Files
		File xml_input = new File("src/Files/Source.xml");
		File xsd_input = new File("src/Files/Source_XSD.xsd");
		
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
		
		System.out.println("Researchers Loader Initiated.");
		
		//--Unmarshal
		Catalog catalog = (Catalog) unmarshaller.unmarshal(xml_input);

		List<Researcher> list = catalog.getResearcher(); //Researchers list

		//--Convert
		ArrayList<ResearcherNew> new_list = convertResearcher(list); //Convert from Researcher to ResearcherNew
		
		//--Load to DB
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceResearcher");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		for (ResearcherNew st : new_list){
			em.persist(st);
		}
		tx.commit();

		//--Close an application-managed entity manager.
		em.close();
		
		//--Close the factory, releasing any resources that it holds.
		emf.close();

		System.out.println("Researchers Load Sucessfull.");
	}

	/** Convert objects of type Researcher into entities of type ResearcherNew
	 * @param lista List of Researcher
	 * @return ArrayList of ResearcherNew
	 */
	public static ArrayList<ResearcherNew> convertResearcher(List<Researcher> lista){
		ArrayList<InterestNew> distinctinterests = distinctInterests(lista); //List all distinct interests available
		ArrayList<ArticleNew> distinctarticles = distinctArticles(lista); //List all distinct article titles available
		ArrayList<ResearcherNew> lista_new = new ArrayList<ResearcherNew>();
		for(int i=0; i<lista.size(); i++){
			String name = lista.get(i).getName();
			String univ = lista.get(i).getUniversity();
			
			List<String> intlist = lista.get(i).getInterests().getTopic();
			List<InterestNew> inter = new ArrayList<InterestNew>();
			
			for(int ii=0;ii<intlist.size();ii++){
				int index = compareInterests(distinctinterests , new InterestNew(intlist.get(ii)));
				inter.add(distinctinterests.get(index));
			}
			
			int cit = 0;
			List<ArticleNew> publications = new ArrayList<ArticleNew>();
			
			for(int j=0; j<3; j++){
				int index = compareArticles(distinctarticles, new ArticleNew(lista.get(i).getArticles().getArticle().get(j).getTitle()));
				publications.add(distinctarticles.get(index));
				
				cit = cit + lista.get(i).getArticles().getArticle().get(j).getCited().intValue();
			}
			ResearcherNew R = new ResearcherNew(name,univ,inter,publications,cit);
			lista_new.add(R);
		}
		return lista_new;	
	}
	
	/** Generates a list of InterestsNew objects containing all available distinct interests
	 * @param lista
	 * @return ArrayList of InterestNew
	 */
	public static ArrayList<InterestNew> distinctInterests(List<Researcher> lista){
		ArrayList<InterestNew> list = new ArrayList<InterestNew>();
		for(int i=0; i<lista.size(); i++){
			List<String> intlist = lista.get(i).getInterests().getTopic();
			for(int ii=0;ii<intlist.size();ii++){
				if(!list.contains(intlist.get(ii))){
					list.add(new InterestNew(intlist.get(ii)));
				}	
			}
		}
		return list;
	}
	
	/** Generates a list of ArticleNew objects containing all available distinct article titles
	 * @param lista
	 * @return ArrayList of ArticleNew
	 */
	public static ArrayList<ArticleNew> distinctArticles(List<Researcher> lista){
		ArrayList<ArticleNew> list = new ArrayList<ArticleNew>();
		ArrayList<String> auxlist = new ArrayList<String>();
		for(int i=0; i<lista.size(); i++){
			for(int ii=0;ii<3;ii++){
				String auxtitle = lista.get(i).getArticles().getArticle().get(ii).getTitle();
				if(!auxlist.contains(auxtitle)){
					auxlist.add(auxtitle);
					list.add(new ArticleNew(auxtitle));
				}	
			}
		}
		return list;
	}
	
	/** Compares the list of distinct interests with a test interest
	 * @param distinct List of distinct interests
	 * @param test Interest to be compared
	 * @return int index (of wanted interests in the distinct interests list distinct)
	 */
	public static int compareInterests(List<InterestNew> distinct , InterestNew test){
		int index = -1;
		for (int i=0 ; i<distinct.size() ; i++){
			if(distinct.get(i).getTopic().toLowerCase().equals(test.getTopic().toLowerCase())){
				index = i;
				return index;
			}
		}
		return index;
	}
	
	/** Compares the list of distinct articles titles with a test title
	 * @param distinct List of distinct articles
	 * @param test Article to be compared
	 * @return int index (of wanted articles in distinct)
	 */
	public static int compareArticles(List<ArticleNew> distinct , ArticleNew test){
		int index = -1;
		for (int i=0 ; i<distinct.size() ; i++){
			if(distinct.get(i).getTitle().toLowerCase().equals(test.getTitle().toLowerCase())){
				index = i;
				return index;
			}
		}
		return index;
	}
}