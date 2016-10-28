package ws;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import common.PublicationNew;
import ejb.EJBReaderPubRemote;

@WebService
public class PublicationServer {
	
	@EJB
	private EJBReaderPubRemote bean;
	
	public PublicationServer(){};
	
	@WebMethod
	public List<PublicationNew> readAllP(){
		return bean.readAllPublications();
	}
	
	@WebMethod
	public List<PublicationNew> readWantedP(List<String> wanted){
		return bean.readWantedPublications(wanted);
	}
	
	@WebMethod
	public List<PublicationNew> readSortP(){
		return bean.readSortPublications();
	}
	
	@WebMethod
	public PublicationNew selectP(String title){
		return bean.selectPublication(title);
	}
}