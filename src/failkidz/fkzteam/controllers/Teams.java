package failkidz.fkzteam.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import failkidz.fkzteam.beans.LoginHandler;
import failkidz.fkzteam.beans.TeamHandler;

/**
 * Servlet implementation class Teams
 */
@WebServlet("/Teams")
public class Teams extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Teams() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TeamHandler th = (TeamHandler) request.getSession().getAttribute("TeamHandler");
		if(th == null){
			th = new TeamHandler();
			request.getSession().setAttribute("TeamHandler", th);
		}
		
		if(request.getParameter("action") != null){
			if(request.getParameter("action").equals("addPlayer")){
				th.addPlayer(request.getParameter("playerName"));
			}
			else if(request.getParameter("action").equals("addTeam")){
				th.addTeam(request.getParameter("teamName"));
			}
			else if(request.getParameter("action").equals("genTeams")){
				th.createTeams();
			}
		}
		
		try{
			RequestDispatcher rd = request.getRequestDispatcher("Teams.jsp");
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
