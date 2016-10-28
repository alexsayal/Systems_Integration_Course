package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import common.PublicationNew;

@Stateless
public class EJBReaderPub implements EJBReaderPubRemote {

	public EJBReaderPub(){};
	
	@PersistenceContext(unitName = "R2Persistence")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<PublicationNew> readAllPublications(){
		Query Q;
		Q = em.createQuery("SELECT s FROM PublicationNew s");
		
		List<PublicationNew> L = (List<PublicationNew>) Q.getResultList();
		System.out.println("EJB Server Responded.");
		return L;
	}
	
	@SuppressWarnings("unchecked")
	public List<PublicationNew> readSortPublications(){
		Query Q;
		Q = em.createQuery("SELECT s FROM PublicationNew s ORDER BY s.title ASC");
		
		List<PublicationNew> L = (List<PublicationNew>) Q.getResultList();
		System.out.println("EJB Server Responded.");
		return L;
	}
	
	@SuppressWarnings("unchecked")
	public List<PublicationNew> readWantedPublications(List<String> wanted){
		Query Q;
		String q = "(";
		for(int i=0;i<wanted.size();i++){
			if(i>=1 && i!=wanted.size()) q = q + " OR ";
			q = q + " s.title='" + wanted.get(i) + "'";

		}
		Q = em.createQuery("SELECT s FROM PublicationNew s WHERE" + q + ")");

		List<PublicationNew> L = (List<PublicationNew>) Q.getResultList();
		System.out.println("EJB Server Responded.");
		return L;
	}
	
	@SuppressWarnings("unchecked")
	public PublicationNew selectPublication(String title){
		Query B = em.createQuery("SELECT s FROM PublicationNew s WHERE s.title='" + title + "'");
		List<PublicationNew> L = (List<PublicationNew>) B.getResultList();
		System.out.println("EJB Server Responded.");
		if(L.size()>0){
			return L.get(0);
		}
		else{
			return null;
		}
	}
}