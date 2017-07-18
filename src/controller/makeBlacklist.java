package controller;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dbTools.dbTools;

/**
 * Servlet implementation class makeBlacklist
 */
@WebServlet("/makeBlacklist")
public class makeBlacklist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	dbTools dbTool;
	Gson gson2 ;
	Type type  ;
	Map<String,String> container;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public makeBlacklist() {
        super();
        // TODO Auto-generated constructor stub
		gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		type = new TypeToken<Map<String, Object>>() {}.getType();  
		dbTool=new dbTools();
		container=new HashMap();
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
		//showParams(request);
		String contactId="w6578";
		String note="无。";
		container.put("id",request.getParameter("id"));
		container.put("contactid",contactId);
		container.put("note", note);
		String res=dbTool.dParent(gson2.toJson(container));
		PrintWriter out = response.getWriter();
		System.out.println(gson2.toJson(container));
		System.out.println(res);
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
