package failkidz.fkzteam.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import failkidz.fkzteam.beans.FixtureHandler;
import failkidz.fkzteam.beans.ScoreHandler;

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
		//Always show fixture schedule
		fh.getFixtures();
		
		if(request.getParameter("action") != null){
			if(request.getParameter("action").equals("generategames")){
				ScoreHandler sh = new ScoreHandler();
				int numberOfTeams = sh.generateEmptyScoreBoard();
				fh.createFixtures(numberOfTeams);
				System.out.println("Done generating fixtures");
				
			}
			else if(request.getParameter("action").equals("registergame")){
				//Fetch the input from the form
				int homeId = Integer.parseInt(request.getParameter("homeId"));
				int awayId = Integer.parseInt(request.getParameter("awayId"));
				int homeScore = Integer.parseInt(request.getParameter("homescore"));
				int awayScore = Integer.parseInt(request.getParameter("awayscore"));
				//Now let the fixture handler do the rest of the work
				fh.addResult(homeId, awayId, homeScore, awayScore);
			}
		} 

		request.setAttribute("fixturehandler", fh);

		try{
			RequestDispatcher rd = request.getRequestDispatcher("/Fixture.jsp");
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