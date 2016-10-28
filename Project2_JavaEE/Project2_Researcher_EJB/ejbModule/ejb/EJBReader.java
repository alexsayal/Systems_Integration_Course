package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import common.ResearcherNew;

@Stateless
public class EJBReader implements EJBReaderRemote{
	
	public EJBReader(){};
	
	@PersistenceContext(unitName = "RPersistence") 
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<ResearcherNew> readAllResearchers(){
		Query Q;
		Q = em.createQuery("SELECT s FROM ResearcherNew s");
		
		List<ResearcherNew> L = (List<ResearcherNew>) Q.getResultList();
		System.out.println("EJB Server Responded.");
		return L;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResearcherNew> readSortResearchers(int attribute, int mode){
		Query Q;
		String[] at = {"id","name","university"};
		String[] md = {"ASC","DESC"};
		Q = em.createQuery("SELECT s FROM ResearcherNew s ORDER BY s." + at[attribute-1] + " " + md[mode-1]);
		
		List<ResearcherNew> L = (List<ResearcherNew>) Q.getResultList();
		System.out.println("EJB Server Responded.");
		return L;
	}
	
	@SuppressWarnings("unchecked")
	public List<ResearcherNew> readNResearchers(int number){
		Query Q;
		Q = em.createQuery("SELECT s FROM ResearcherNew s ORDER BY s.citations DESC");
		Q.setMaxResults(number);
		
		List<ResearcherNew> L = (List<ResearcherNew>) Q.getResultList();
		System.out.println("EJB Server Responded.");
		return L;
	}
	
	@SuppressWarnings("unchecked")
	public ResearcherNew selectResearcher(String name){
		Query B = em.createQuery("SELECT s FROM ResearcherNew s WHERE s.name='" + name + "'");
		List<ResearcherNew> L = (List<ResearcherNew>) B.getResultList();
		System.out.println("EJB Server Responded.");
		if(L.size()>0){
			return L.get(0);
		}
		else{
			return null;
		}
	}
}