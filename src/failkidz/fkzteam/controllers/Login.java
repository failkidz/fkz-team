package failkidz.fkzteam.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import failkidz.fkzteam.beans.LoginHandler;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("action").equals("loginUser")){
			LoginHandler lh = new LoginHandler();
			boolean validUser = lh.verifyUser((String)request.getParameter("username"), (String)request.getParameter("password"));
			request.getSession().setAttribute("login", validUser);
		}
		else if(request.getParameter("action").equals("listUsers")){
			
		}
		
		try{
			RequestDispatcher rd = request.getRequestDispatcher("");
			rd.forward(request, response);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}