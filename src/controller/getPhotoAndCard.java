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
//		BufferedReader br =request.getReader();
//	    String inputLine;
//	    String str = "";
//	    try {
//	      while ((inputLine = br.readLine()) != null) {
//	        str += inputLine;
//	      }
//	      br.close();
//	    } catch (IOException e) {
//	      System.out.println("IOException: " + e);
//	    }
//	    
//	    System.out.print(str);
//	    Gson gson = new Gson();
//	    Map<String, String> map = new HashMap<String, String>();  
//	    map.put("key1", "value1");  
//	    map.put("key2", "value2");  
//	    map.put("key3", "value3");  	    
//	    String jsonString = gson.toJson(map);  
//	   
//	    	Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
//   	        Type type = new TypeToken<Map<String, String>>() {}.getType();  
//   	        Map<String, Object> map2 = gson2.fromJson(str.toString(), type); 
	    
	    
	    if(request.getParameter("identity").equals("school")){
	    	System.out.print(request.getParameter("message"));
	    	out.print(request.getParameter("message"));
	    }else if(request.getParameter("identity").equals("society")){
	    	System.out.print(request.getParameter("message"));
	    	out.print(request.getParameter("message"));
	    }
	    else{
	    	System.out.print("identity illegal");
	    }

	}
}
