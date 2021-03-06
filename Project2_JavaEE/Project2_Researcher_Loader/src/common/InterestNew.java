package common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "INTERESTS_TABLE")
public class InterestNew implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interestid")
	private Long id;

	@ManyToMany(mappedBy ="interests")
	private List<ResearcherNew> researchers;

	private String topic;

	public InterestNew(){};

	public InterestNew(String topic){
		this.topic = topic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ResearcherNew> getResearchers() {
		return researchers;
	}

	public void setResearchers(List<ResearcherNew> researchers) {
		this.researchers = researchers;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0); return hash;
	}

	@Override
	public String toString() {
		return topic;
	}

}