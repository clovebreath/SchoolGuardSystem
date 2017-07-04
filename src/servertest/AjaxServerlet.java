package servertest;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AjaxServerlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String all_data = "id: " + (String)request.getParameter("id") + 
				"\nid_image: " + (String)request.getParameter("id_img") + 
				"\ncap_image: " + (String)request.getParameter("cap_img") + 
				"\ntime_stamp: " + (String)request.getParameter("time_stamp");
		System.out.println(all_data);
		response.getWriter().write(all_data);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
