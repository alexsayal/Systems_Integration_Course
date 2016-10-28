package jpa;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

import common.Catalog;
import common.PublicationNew;
import common.Researcher;

public class JPAPublicationsLoader {
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
		
		System.out.println("Publications Loader Initiated.");

		//--Unmarshal
		Catalog catalog = (Catalog) unmarshaller.unmarshal(xml_input);

		List<Researcher> list = catalog.getResearcher(); //List of Researchers
		
		//--Convert
		ArrayList<PublicationNew> new_list = convertPublication(list);

		//--Load to DB
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistencePublications");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		for (PublicationNew st : new_list){
			em.persist(st);
		}
		tx.commit();

		//--Close an application-managed entity manager.
		em.close();
		
		//--Close the factory, releasing any resources that it holds.
		emf.close();
		
		System.out.println("Publications Load Sucessfull.");
	}
	
	/** Convert objects of type Researcher into entities of type PublicationNew
	 * @param art List of Researcher
	 * @return ArrayList of PublicationNew
	 */
	public static ArrayList<PublicationNew> convertPublication(List<Researcher> art){
		ArrayList<PublicationNew> lista_new = new ArrayList<PublicationNew>();
		ArrayList<String> distincttitles = new ArrayList<String>(); //List all distinct titles
		for(int i=0; i<art.size(); i++){
			for(int j=0; j<3; j++){
				String title = art.get(i).getArticles().getArticle().get(j).getTitle();
								
				BigInteger year = art.get(i).getArticles().getArticle().get(j).getYear();
				BigInteger cited = art.get(i).getArticles().getArticle().get(j).getCited();
				String pub = art.get(i).getArticles().getArticle().get(j).getPublication();
				
				if(!distincttitles.contains(title)){
					distincttitles.add(title);
					PublicationNew P = new PublicationNew(title,cited,year,pub);
					lista_new.add(P);
				}
			}
		}
		return lista_new;
	}
//	
//	/** Generates a list of AuthorNew objects containing all available distinct authors
//	 * @param lista List of Researcher
//	 * @return ArrayList of AuthorNew
//	 */
//	private static ArrayList<AuthorNew> distinctAuthors(List<Researcher> lista) {
//		ArrayList<AuthorNew> list = new ArrayList<AuthorNew>();
//		ArrayList<String> auxlist = new ArrayList<String>();
//		for(int i=0; i<lista.size(); i++){
//			for(int j=0;j<3;j++){
//				List<String> auxtitle = lista.get(i).getArticles().getArticle().get(j).getAuthors().getAuthor();
//				for(int ii=0;ii<auxtitle.size();ii++){
//					if(!auxlist.contains(auxtitle.get(ii))){
//						auxlist.add(auxtitle.get(ii));
//						list.add(new AuthorNew(auxtitle.get(ii)));
//					}	
//				}	
//			}
//		}
//		return list;
//	}
//	
//	/** Compares the list of distinct authors with a test author
//	 * @param distinct List of distinct authors
//	 * @param test author to be compared
//	 * @return int index (of wanted authors in the distinct authors list distinct)
//	 */
//	public static int compareAuthors(List<AuthorNew> distinct , AuthorNew test){
//		int index = -1;
//		for (int i=0 ; i<distinct.size() ; i++){
//			if(distinct.get(i).getName().toLowerCase().equals(test.getName().toLowerCase())){
//				index = i;
//				return index;
//			}
//		}
//		return index;
//	}
}