package failkidz.fkzteam.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import failkidz.fkzteam.beans.ScoreHandler;

/**
 * Servlet implementation class Scoreboard
 */
@WebServlet("/Scoreboard")
public class Scoreboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Scoreboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String redirectTo = "/index.jsp";
		
		//Check if the user is logged in.
		//if(request.getSession().getAttribute("login") == new Boolean(true)){
			ScoreHandler sh = new ScoreHandler();
			sh.getRows();
			request.setAttribute("scorehandler", sh);
			redirectTo = "/Scoreboard.jsp";
		//}
		
		//Now redirect the to the right page
		try{
			RequestDispatcher rd = request.getRequestDispatcher(redirectTo);
			rd.forward(request, response);
		}
		catch(ServletException e){
			System.out.print(e.getMessage());
		}
		catch(IOException e){
			System.out.print(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
