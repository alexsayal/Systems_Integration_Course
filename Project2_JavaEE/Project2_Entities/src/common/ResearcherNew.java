package common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RESEARCHERS_TABLE")
public class ResearcherNew implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "researcherid")
	private Long id;
	
	private String name;
	private String university;
	
	@ManyToMany(cascade=CascadeType.PERSIST , fetch=FetchType.EAGER)
	@JoinTable(name = "join_researchers_interests",
		joinColumns = @JoinColumn(name = "researcherid"),
		inverseJoinColumns = @JoinColumn(name = "interestid"))
	private List <InterestNew> interests;
	
	@ManyToMany(cascade=CascadeType.PERSIST , fetch=FetchType.EAGER)
	@JoinTable(name = "join_researchers_articles",
	joinColumns = @JoinColumn(name = "researcherid"),
	inverseJoinColumns = @JoinColumn(name = "articleid"))
	private List <ArticleNew> publications;
	
	private int citations;
	
	public ResearcherNew(){}
	
	public ResearcherNew(String name, String university, List<InterestNew> interests, List<ArticleNew> publications, int citations){
		this.name = name; 
		this.university = university;
		this.interests = interests;
		this.publications = publications;
		this.citations = citations;
	}

	public int getCitations() {
		return citations;
	}

	public void setCitations(int citations) {
		this.citations = citations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public List<ArticleNew> getPublications() {
		return publications;
	}

	public void setPublications(List<ArticleNew> publications) {
		this.publications = publications;
	}

	public List<InterestNew> getInterests() {
		return interests;
	}

	public void setInterests(List<InterestNew> interests) {
		this.interests = interests;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0); return hash;
	}
	
	@Override
	public String toString() {
		String print = "Researcher ID" + id + ": " + name + "\n" + 
					   "           University: " + university + "  ||  Number of Citations: " + citations + "\n" +
					   "           Interests: " + interests;
		return print;
	}	
}