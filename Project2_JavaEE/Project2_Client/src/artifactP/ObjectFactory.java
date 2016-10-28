
package artifactP;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the artifactP package. 
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

    private final static QName _ReadSortPResponse_QNAME = new QName("http://ws/", "readSortPResponse");
    private final static QName _SelectP_QNAME = new QName("http://ws/", "selectP");
    private final static QName _ReadAllP_QNAME = new QName("http://ws/", "readAllP");
    private final static QName _ReadAllPResponse_QNAME = new QName("http://ws/", "readAllPResponse");
    private final static QName _ReadWantedP_QNAME = new QName("http://ws/", "readWantedP");
    private final static QName _ReadWantedPResponse_QNAME = new QName("http://ws/", "readWantedPResponse");
    private final static QName _ReadSortP_QNAME = new QName("http://ws/", "readSortP");
    private final static QName _SelectPResponse_QNAME = new QName("http://ws/", "selectPResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: artifactP
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadSortPResponse }
     * 
     */
    public ReadSortPResponse createReadSortPResponse() {
        return new ReadSortPResponse();
    }

    /**
     * Create an instance of {@link SelectP }
     * 
     */
    public SelectP createSelectP() {
        return new SelectP();
    }

    /**
     * Create an instance of {@link ReadAllP }
     * 
     */
    public ReadAllP createReadAllP() {
        return new ReadAllP();
    }

    /**
     * Create an instance of {@link ReadAllPResponse }
     * 
     */
    public ReadAllPResponse createReadAllPResponse() {
        return new ReadAllPResponse();
    }

    /**
     * Create an instance of {@link ReadWantedP }
     * 
     */
    public ReadWantedP createReadWantedP() {
        return new ReadWantedP();
    }

    /**
     * Create an instance of {@link ReadWantedPResponse }
     * 
     */
    public ReadWantedPResponse createReadWantedPResponse() {
        return new ReadWantedPResponse();
    }

    /**
     * Create an instance of {@link ReadSortP }
     * 
     */
    public ReadSortP createReadSortP() {
        return new ReadSortP();
    }

    /**
     * Create an instance of {@link SelectPResponse }
     * 
     */
    public SelectPResponse createSelectPResponse() {
        return new SelectPResponse();
    }

    /**
     * Create an instance of {@link PublicationNew }
     * 
     */
    public PublicationNew createPublicationNew() {
        return new PublicationNew();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadSortPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readSortPResponse")
    public JAXBElement<ReadSortPResponse> createReadSortPResponse(ReadSortPResponse value) {
        return new JAXBElement<ReadSortPResponse>(_ReadSortPResponse_QNAME, ReadSortPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "selectP")
    public JAXBElement<SelectP> createSelectP(SelectP value) {
        return new JAXBElement<SelectP>(_SelectP_QNAME, SelectP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readAllP")
    public JAXBElement<ReadAllP> createReadAllP(ReadAllP value) {
        return new JAXBElement<ReadAllP>(_ReadAllP_QNAME, ReadAllP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readAllPResponse")
    public JAXBElement<ReadAllPResponse> createReadAllPResponse(ReadAllPResponse value) {
        return new JAXBElement<ReadAllPResponse>(_ReadAllPResponse_QNAME, ReadAllPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadWantedP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readWantedP")
    public JAXBElement<ReadWantedP> createReadWantedP(ReadWantedP value) {
        return new JAXBElement<ReadWantedP>(_ReadWantedP_QNAME, ReadWantedP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadWantedPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readWantedPResponse")
    public JAXBElement<ReadWantedPResponse> createReadWantedPResponse(ReadWantedPResponse value) {
        return new JAXBElement<ReadWantedPResponse>(_ReadWantedPResponse_QNAME, ReadWantedPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadSortP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "readSortP")
    public JAXBElement<ReadSortP> createReadSortP(ReadSortP value) {
        return new JAXBElement<ReadSortP>(_ReadSortP_QNAME, ReadSortP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "selectPResponse")
    public JAXBElement<SelectPResponse> createSelectPResponse(SelectPResponse value) {
        return new JAXBElement<SelectPResponse>(_SelectPResponse_QNAME, SelectPResponse.class, null, value);
    }

}
