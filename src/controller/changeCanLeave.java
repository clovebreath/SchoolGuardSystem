package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dbTools.dbTools;

/**
 * Servlet implementation class getResult
 */
@WebServlet("/changeCanLeave")
public class changeCanLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	dbTools dbTool;
	Gson gson2 ;
	Type type  ;
	Map<String,String> container;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeCanLeave() {
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
		showParams(request);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		Map<String,String> sending=new HashMap<>();
		sending.put("id", id);
		sending.put("status", status);
		System.out.println(gson2.toJson(sending));
		String record=dbTool.setStudentCanleave(gson2.toJson(sending));
System.out.println(record);
		out.println(record);	
		

	}
	private void showParams(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        Set<Map.Entry<String, String>> set = map.entrySet();
        System.out.println("------------------------------");
        for (Map.Entry entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("------------------------------");
    }
}
