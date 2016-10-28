package jpa;

import java.io.File;
import java.io.IOException;
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
import common.Publication;
import common.PublicationBank;
import commonClients.Catalogclient;
import commonClients.Client;
import commonClients.ClientBank;

public class Loader {

	public static void main(String[] args) throws JAXBException {
		//--Initialize
		JAXBContext JC = JAXBContext.newInstance(Catalog.class);
		Unmarshaller unmarshaller = JC.createUnmarshaller();

		JAXBContext JC2 = JAXBContext.newInstance(Catalogclient.class);
		Unmarshaller unmarshaller2 = JC2.createUnmarshaller();

		SchemaFactory schemafactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
		Schema schema = null;

		//--Files
		File xml_input = new File("src/Files/Source.xml");
		File xsd_input = new File("src/Files/SourceXSD.xsd");
		File xml_input2 = new File("src/Files/SourceClients.xml");
		File xsd_input2 = new File("src/Files/SourceClientsXSD.xsd");

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
		try {
			schema = schemafactory.newSchema(xsd_input2);
			Validator validator = schema.newValidator();
			try {
				validator.validate(new StreamSource(xml_input2));
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
		Catalogclient catalog2 = (Catalogclient) unmarshaller2.unmarshal(xml_input2);

		List<Publication> list = catalog.getPublication();
		List<Client> list2 = catalog2.getClient();

		List<PublicationBank> new_list = convertPub(list);
		List<ClientBank> new_list2 = convertClient(list2);

		//--Load to DB
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceforPubBank");
		EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("PersistenceforResBank");
		EntityManager em = emf.createEntityManager();
		EntityManager em2 = emf2.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		EntityTransaction tx2 = em2.getTransaction();

		tx2.begin();
		for (ClientBank st2 : new_list2){
			em.persist(st2);
		}
		tx2.commit();

		tx.begin();
		for (PublicationBank st : new_list){
			em.persist(st);
		}
		tx.commit();

		//--Close an application-managed entity manager.
		em.close();
		em2.close();

		//--Close the factory, releasing any resources that it holds.
		emf.close();
		emf2.close();

		System.out.println("Load Sucessfull.");
	}

	public static List<PublicationBank> convertPub(List<Publication> list){
		List<PublicationBank> list2 = new ArrayList<PublicationBank>();
		for(int i=0;i<list.size();i++){
			String title = list.get(i).getTitle();
			long cost = list.get(i).getCost().longValue();
			String fulltext = list.get(i).getFulltext();

			list2.add(new PublicationBank(title,cost,fulltext));
		}
		return list2;
	}

	public static List<ClientBank> convertClient(List<Client> list){
		List<ClientBank> list2 = new ArrayList<ClientBank>();
		for(int i=0;i<list.size();i++){
			String name = list.get(i).getName();
			long balance = list.get(i).getBalance().longValue();

			list2.add(new ClientBank(name,balance));
		}
		return list2;
	}
}