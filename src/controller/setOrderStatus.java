package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import dbTools.dbTools;

/**
 * Servlet implementation class setOrderStatus
 */
@WebServlet("/setOrderStatus")
public class setOrderStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	dbTools dbTool;
	Gson gson2 ;
	Type type  ;
	Map<String,String> container;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setOrderStatus() {
        super();
        // TODO Auto-generated constructor stub
		gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		type = new TypeToken<Map<String, Object>>() {}.getType();  
		dbTool=new dbTools();
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
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id=request.getParameter("id");
		String status=request.getParameter("order");
		Map<String,String> sending=new HashMap<>();
		sending.put("id", id);
		sending.put("order", status);
		System.out.println(gson2.toJson(sending));
		String record=dbTool.setOrder(gson2.toJson(sending));
System.out.println(record);
		out.println(record);	
		

	}

}
