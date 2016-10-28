
package artifactR;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the artifactR package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SelectR_QNAME = new QName("http://ws/", "selectR");
    private final static QName _ReadNRResponse_QNAME = new QName("http://ws/", "readNRResponse");
    private final static QName _ReadAllRResponse_QNAME = new QName("http://ws/", "readAllRResponse");
    private final static QName _SelectRResponse_QNAME = new QName("http://ws/", "selectRResponse");
    private final static QName _ReadAllR_QNAME = new QName("http://ws/", "readAllR");
    private final static QName _ReadSortR_QNAME = new QName("http://ws/", "readSortR");
    private final static QName _ReadSortRResponse_QNAME = new QName("http://ws/", "readSortRResponse");
    private final static QName _ReadNR_QNAME = new QName("http://ws/", "readNR");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: artifactR
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectR }
     * 
     */
    public SelectR createSelectR() {
        return new SelectR();
    }

    /**
     * Create an instance of {@link ReadNRResponse }
     * 
     */
    public ReadNRResponse createReadNRResponse() {
        return new ReadNRResponse();
    }

    /**
     * Create an instance of {@link ReadAllRResponse }
     * 
     */
    public ReadAllRResponse createReadAllRResponse() {
        return new ReadAllRResponse();
    }

    /**
     * Create an instance of {@link SelectRResponse }
     * 
     */
    public SelectRResponse createSelectRResponse() {
        return new SelectRResponse();
    }

    /**
     * Create an instance of {@link ReadAllR }
     * 
     */
    public ReadAllR createReadAllR() {
        return new ReadAllR();
    }

    /**
     * Create an instance of {@link ReadSortR }
     * 
     */
    public ReadSortR createReadSortR() {
        return new ReadSortR();
    }

    /**
     * Create an instance of {@link ReadSortRResponse }
     * 
     */
    public ReadSortRResponse createReadSortRResponse() {
        return new ReadSortRResponse();
    }

    /**
     * Create an instance of {@link ReadNR }
     * 
     */
    public ReadNR createReadNR() {
        return new ReadNR();
    }

    /**
     * Create an instance of {@link ResearcherNew }
     * 
     */
    public ResearcherNew createResearcherNew() {
        return new ResearcherNew();
    }

    /**
     * Create an instance of {@link InterestNew }
     * 
     */
    public InterestNew createInterestNew() {
        return new InterestNew();
    }

    /**
     * Create an instance of {@link ArticleNew }
     * 
     */
    public ArticleNew createArticleNew() {
        return new ArticleNew();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectR }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "selectR")
    public JAXBElement<SelectR> createSelectR(SelectR value) {
        return new JAXBElement<SelectR>(_SelectR_QNAME, SelectR.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadNRResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readNRResponse")
    public JAXBElement<ReadNRResponse> createReadNRResponse(ReadNRResponse value) {
        return new JAXBElement<ReadNRResponse>(_ReadNRResponse_QNAME, ReadNRResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllRResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readAllRResponse")
    public JAXBElement<ReadAllRResponse> createReadAllRResponse(ReadAllRResponse value) {
        return new JAXBElement<ReadAllRResponse>(_ReadAllRResponse_QNAME, ReadAllRResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "selectRResponse")
    public JAXBElement<SelectRResponse> createSelectRResponse(SelectRResponse value) {
        return new JAXBElement<SelectRResponse>(_SelectRResponse_QNAME, SelectRResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllR }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readAllR")
    public JAXBElement<ReadAllR> createReadAllR(ReadAllR value) {
        return new JAXBElement<ReadAllR>(_ReadAllR_QNAME, ReadAllR.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadSortR }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readSortR")
    public JAXBElement<ReadSortR> createReadSortR(ReadSortR value) {
        return new JAXBElement<ReadSortR>(_ReadSortR_QNAME, ReadSortR.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadSortRResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readSortRResponse")
    public JAXBElement<ReadSortRResponse> createReadSortRResponse(ReadSortRResponse value) {
        return new JAXBElement<ReadSortRResponse>(_ReadSortRResponse_QNAME, ReadSortRResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadNR }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readNR")
    public JAXBElement<ReadNR> createReadNR(ReadNR value) {
        return new JAXBElement<ReadNR>(_ReadNR_QNAME, ReadNR.class, null, value);
    }

}
