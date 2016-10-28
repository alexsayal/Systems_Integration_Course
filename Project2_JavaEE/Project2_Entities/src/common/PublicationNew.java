package common;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PUBLICATIONS_TABLE")
public class PublicationNew implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "publicationid")
	private Long id;
	
	private String title;
	
	private BigInteger cited;
	private BigInteger year;
	private String pub;
	
	public PublicationNew(){};
	
	public PublicationNew(String t, BigInteger c, BigInteger y, String p){
		this.title = t;
		this.cited = c;
		this.year = y;
		this.pub = p;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public BigInteger getCited() {
		return cited;
	}
	public void setCited(BigInteger cited) {
		this.cited = cited;
	}
	public BigInteger getYear() {
		return year;
	}
	public void setYear(BigInteger year) {
		this.year = year;
	}
	public String getPub() {
		return pub;
	}
	public void setPub(String pub) {
		this.pub = pub;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0); return hash;
	}
	
	@Override
	public String toString() {
		String print = "Publication ID" + id + ": " + title + "\n" +
					   "            Number of Citations: " + cited + "  ||  Year: " + year + "\n" + 
					   "            Published in: " + pub;
		return print;
	}
}