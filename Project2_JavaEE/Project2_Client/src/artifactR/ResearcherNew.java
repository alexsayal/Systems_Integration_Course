
package artifactR;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for researcherNew complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="researcherNew">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="citations" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="interests" type="{http://ws/}interestNew" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publications" type="{http://ws/}articleNew" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="university" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "researcherNew", propOrder = {
    "citations",
    "id",
    "interests",
    "name",
    "publications",
    "university"
})
public class ResearcherNew {

    protected int citations;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<InterestNew> interests;
    protected String name;
    @XmlElement(nillable = true)
    protected List<ArticleNew> publications;
    protected String university;

    /**
     * Gets the value of the citations property.
     * 
     */
    public int getCitations() {
        return citations;
    }

    /**
     * Sets the value of the citations property.
     * 
     */
    public void setCitations(int value) {
        this.citations = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the interests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InterestNew }
     * 
     * 
     */
    public List<InterestNew> getInterests() {
        if (interests == null) {
            interests = new ArrayList<InterestNew>();
        }
        return this.interests;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the publications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArticleNew }
     * 
     * 
     */
    public List<ArticleNew> getPublications() {
        if (publications == null) {
            publications = new ArrayList<ArticleNew>();
        }
        return this.publications;
    }

    /**
     * Gets the value of the university property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniversity() {
        return university;
    }

    /**
     * Sets the value of the university property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniversity(String value) {
        this.university = value;
    }
    
    @Override
	public String toString() {
		String print = "Researcher ID" + id + ": " + name + "\n" + 
					   "           University: " + university + "  ||  Number of Citations: " + citations + "\n" +
					   "           Interests: " + interests;
		return print;
	}	

}
