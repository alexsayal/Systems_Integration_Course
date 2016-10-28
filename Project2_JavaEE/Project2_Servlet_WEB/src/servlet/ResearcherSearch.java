package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ResearcherNew;
import ejb.EJBReaderRemote;

/**
 * Servlet implementation class ResearcherSearch
 */
@WebServlet("/ResearcherSearch")
public class ResearcherSearch extends HttpServlet {
	@EJB
	private EJBReaderRemote bean2;
	
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		List<ResearcherNew> names = bean2.readSortResearchers(2, 1);
		List<String> names_string = new ArrayList<String>();
		
		for(int i=0;i<names.size();i++){
			names_string.add(names.get(i).getName());
		}
		
		request.setAttribute("today", new Date());
        request.setAttribute("myListOfResearchers", names_string);
        request.setAttribute("numberResearchers", names.size());
		request.getRequestDispatcher("/displayR.jsp").forward(request, response);
	}
}