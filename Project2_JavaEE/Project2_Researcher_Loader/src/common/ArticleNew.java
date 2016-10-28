package common;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLES_TABLE")
public class ArticleNew implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "articleid")
	private Long id;
	
	@ManyToMany(mappedBy ="publications")
	private Collection<ResearcherNew> researchers;
	
	private String title;
	
	public ArticleNew(){};
	
	public ArticleNew(String title){
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<ResearcherNew> getResearchers() {
		return researchers;
	}

	public void setResearchers(Collection<ResearcherNew> researchers) {
		this.researchers = researchers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0); return hash;
	}
	
	@Override
	public String toString() {
		return "Article title: " + title;
	}
	
}
