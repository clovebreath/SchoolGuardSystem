package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class getPhotoAndCard
 */
@WebServlet("/getPhotoAndCard")
public class getPhotoAndCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getPhotoAndCard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

    	Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
	    Type type = new TypeToken<Map<String, String>>() {}.getType();  
	    Map<String,String> container=new HashMap<String,String>();
	    if(request.getParameter("identity").equals("school")){
	    	String requestMessage=request.getParameter("message");
	    	System.out.print("+++"+request.getParameter("identity")+"+++");
	    	System.out.print(requestMessage);
		    Map<String, String> map2 = gson2.fromJson(requestMessage, type); 
		    //只传过来一张图片，教职工或学生
   	        
		    
		    container.put("result", "success");
		    out.print(gson2.toJson(container));
	    }else if(request.getParameter("identity").equals("society")){
	    	String requestMessage=request.getParameter("message");
	    	System.out.print("+++"+request.getParameter("identity")+"+++");
	    	System.out.print(requestMessage);
		    Map<String, String> map2 = gson2.fromJson(requestMessage, type); 
		    
		    
		    container.put("result", "success");
		    out.print(gson2.toJson(container));
	    }
	    else{
	    	System.out.print("identity illegal");
	    	out.print("identity illega");
	    }

	}
}
