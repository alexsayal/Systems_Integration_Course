package ejb;

import java.util.List;

import javax.ejb.Remote;

import common.PublicationNew;

@Remote
public interface EJBReaderPubRemote {
	/**
	 * Select all publications in the DB
	 * @return List of PublicationNew
	 */
	public List<PublicationNew> readAllPublications();
	
	/**
	 * Select all publications in the DB sorted by title
	 * @return List of PublicationNew
	 */
	public List<PublicationNew> readSortPublications();
	
	/**
	 * Select publications which title is in wanted
	 * @param wanted
	 * @return List of PublicationNew
	 */
	public List<PublicationNew> readWantedPublications(List<String> wanted);
	
	/** Select the publication with the specific title
	 * @param title
	 * @return PublicationNew
	 */
	public PublicationNew selectPublication(String title);
}
