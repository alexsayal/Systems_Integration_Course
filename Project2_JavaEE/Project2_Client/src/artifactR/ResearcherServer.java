
package artifactR;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ResearcherServer", targetNamespace = "http://ws/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ResearcherServer {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<artifactR.ResearcherNew>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "readSortR", targetNamespace = "http://ws/", className = "artifactR.ReadSortR")
    @ResponseWrapper(localName = "readSortRResponse", targetNamespace = "http://ws/", className = "artifactR.ReadSortRResponse")
    public List<ResearcherNew> readSortR(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns artifactR.ResearcherNew
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "selectR", targetNamespace = "http://ws/", className = "artifactR.SelectR")
    @ResponseWrapper(localName = "selectRResponse", targetNamespace = "http://ws/", className = "artifactR.SelectRResponse")
    public ResearcherNew selectR(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @return
     *     returns java.util.List<artifactR.ResearcherNew>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "readAllR", targetNamespace = "http://ws/", className = "artifactR.ReadAllR")
    @ResponseWrapper(localName = "readAllRResponse", targetNamespace = "http://ws/", className = "artifactR.ReadAllRResponse")
    public List<ResearcherNew> readAllR();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<artifactR.ResearcherNew>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "readNR", targetNamespace = "http://ws/", className = "artifactR.ReadNR")
    @ResponseWrapper(localName = "readNRResponse", targetNamespace = "http://ws/", className = "artifactR.ReadNRResponse")
    public List<ResearcherNew> readNR(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

}
