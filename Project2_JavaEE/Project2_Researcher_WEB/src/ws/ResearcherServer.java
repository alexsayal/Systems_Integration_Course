package ws;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import common.ResearcherNew;
import ejb.EJBReaderRemote;

@WebService
public class ResearcherServer {
	
	@EJB
	private EJBReaderRemote bean;
	
	public ResearcherServer(){};
	
	@WebMethod
	public List<ResearcherNew> readAllR(){
		return bean.readAllResearchers();
	}
	
	@WebMethod
	public List<ResearcherNew> readSortR(int attribute, int mode){
		return bean.readSortResearchers(attribute, mode);
	}
	
	@WebMethod
	public List<ResearcherNew> readNR(int number){
		return bean.readNResearchers(number);
	}
	
	@WebMethod
	public ResearcherNew selectR(String name){
		return bean.selectResearcher(name);
	}	
}