package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class getResult
 */
@WebServlet("/getResult")
public class getResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getResult() {
        super();
        // TODO Auto-generated constructor stub
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
		
		BufferedReader br =request.getReader();
	    String inputLine;
	    String str = "";
	    try {
	      while ((inputLine = br.readLine()) != null) {
	        str += inputLine;
	      }
	      br.close();
	    } catch (IOException e) {
	      System.out.println("IOException: " + e);
	    }
	    
	    System.out.print(str);
	    
//	    Gson gson = new Gson();
//	    Map<String, String> map = new HashMap<String, String>();  
//	    map.put("key1", "value1");  
//	    map.put("key2", "value2");  
//	    map.put("key3", "value3");  	    
//	    String jsonString = gson.toJson(map);  
	   
	    	Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
   	        Type type = new TypeToken<Map<String, String>>() {}.getType();  
   	        Map<String, String> map2 = gson2.fromJson(str, type); 
   	        
   	        if(map2.size()==1){//只传过来一张图片，教职工或学生
   	        	response.sendRedirect("success1.jsp");
   	        }else if(map2.size()==4){//传过来身份证号，姓名，两张图片
   	        	response.sendRedirect("success2.jsp");
   	        }else{//数据有误
   	        	
   	        }
   	        
   	        System.out.println(map2.size());
   	        
	}

}
