package failkidz.fkzteam.controllers;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

	/**
	 * Default constructor. 
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		try{
			if(!(Boolean)req.getSession().getAttribute("login") ){ //If not logged in
				if( exclude(req.getRequestURI()) ){ //Check if page is allowed while not logged in
					chain.doFilter(request, response); //OK show page
				} else {
					request.getRequestDispatcher("index.jsp").forward(request, response); //nope, redirect to login
				}
			} else {
				// pass the request along the filter chain
				chain.doFilter(request, response); //Person is logged in. Allow everything
			}
		} catch(NullPointerException e){ //Person does not have a session yet. Can't be logged on.
			request.getRequestDispatcher("index.jsp").forward(request, response); 
		}
	}
	
	public boolean exclude(String uri){
		return (uri.contains("index") || uri.contains("Scoreboard"));
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
