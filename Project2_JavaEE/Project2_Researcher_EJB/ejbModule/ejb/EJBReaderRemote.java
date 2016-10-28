package ejb;

import java.util.List;

import javax.ejb.Remote;

import common.ResearcherNew;

@Remote
public interface EJBReaderRemote {
	/**
	 * Select all researchers in the DB
	 * @return List of ResearcherNew
	 */
	public List<ResearcherNew> readAllResearchers();
	
	/**
	 * Select all researchers in the DB sorted by the attribute and mode
	 * @param attribute 1-ID 2-Name 3-University
	 * @param mode 1-ASC 2-DESC
	 * @return List of ResearcherNew
	 */
	public List<ResearcherNew> readSortResearchers(int attribute, int mode);
	
	/** Select N researchers in the DB sorted by the number of citations
	 * @param number N
	 * @return List of ResearcherNew
	 */
	public List<ResearcherNew> readNResearchers(int number);
	
	/** Select the researcher with the specific name
	 * @param name
	 * @return ResearcherNew
	 */
	public ResearcherNew selectResearcher(String name);
}
