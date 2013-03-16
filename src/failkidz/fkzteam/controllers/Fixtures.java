package failkidz.fkzteam.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import failkidz.fkzteam.beans.FixtureHandler;

/**
 * Servlet implementation class Fixtures
 */
@WebServlet("/Fixtures")
public class Fixtures extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fixtures() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FixtureHandler fh = new FixtureHandler();
		if(request.getParameter("action") != null){
			if(request.getParameter("action").equals("generategames")){
				// TODO - Do later on
			}
		}
		
		//always show fixture schedule
		fh.getFixtures();
		request.setAttribute("fixturehandler", fh);
		
		try{
			RequestDispatcher rd = request.getRequestDispatcher("");
			rd.forward(request, response);
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
