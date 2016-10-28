package common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PUBLICATIONS_TABLE")
public class PublicationBank implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "publicationid")
	private Long id;
	
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String title;
    private long cost;
    private String fulltext;

    public PublicationBank(){};
    
    public PublicationBank(String title, long cost, String fulltext){
    	this.title = title;
    	this.cost = cost;
    	this.fulltext = fulltext;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public String getFulltext() {
		return fulltext;
	}

	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}
    
	public String toString(){
		return "========== Full Text of ordered Publication received! ========== \nTitle: " + this.title + 
				"\nFull Text: " + this.fulltext + "\n===============================================================";
	}
    
}
