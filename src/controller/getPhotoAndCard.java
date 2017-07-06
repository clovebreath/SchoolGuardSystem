package controller;

import java.io.File;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dbTools.dbTools;
import faceRecognition.CompareModule;
import faceRecognition.EncodeModule;
import faceRecognition.IdentifyModule;
import faceRecognition.ProjectInfomation;

/**
 * Servlet implementation class getPhotoAndCard
 */
@WebServlet("/getPhotoAndCard")
public class getPhotoAndCard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	dbTools dbTool;
	Gson gson2 ;
	Type type  ;
	Map<String,String> container;
	
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
	    //只传过来一张图片，需从数据库中取出所有教职工和学生图片进行1对N比对
		gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		type = new TypeToken<Map<String, Object>>() {}.getType();  
		dbTool=new dbTools();
	    String allPicJson=dbTool.getAllpic();
	    Map<String, Object> allPicMessage = gson2.fromJson(allPicJson, type); 
	    
	    JSONObject jObject = new JSONObject(allPicMessage);
	    JSONArray ja = jObject.getJSONArray("picture");
	    
	    //在人群中找出此人
		IdentifyModule.init(ja);
		CompareModule.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		container=new HashMap<String,String>();
		
	    if(request.getParameter("identity").equals("school")){
	    	
	    	String requestMessage=request.getParameter("message");
		    Map<String, String> requestMap = gson2.fromJson(requestMessage, type); 
//		    System.out.println(requestMap.get("picture").replaceAll(" ","+"));

		    //1对多匹配
			String re=IdentifyModule.identify(requestMap.get("picture").replaceAll(" ","+"));
			System.out.println(re);
			
			if(re.equals(ProjectInfomation.NOT_MATCH)){
				container.put("result", re);
				out.print(gson2.toJson(container));
			}else{
				out.print(dbTool.getSchoolMessage(re));
			}
		    	    
	    }else if(request.getParameter("identity").equals("society")){
	    	
	    	String requestMessage=request.getParameter("message");
		    Map<String, String> requestMap = gson2.fromJson(requestMessage, new TypeToken<Map<String, String>>() {}.getType()); 
			
		    //首先判断是否人证合一
		    
			String res = CompareModule.Compare(requestMap.get("picture1").replaceAll(" ","+"), requestMap.get("picture2").replaceAll(" ","+"));
			System.out.println(res);
			
			if(res.equals(ProjectInfomation.NOT_MATCH)){
				//将此人基本信息返回给主页面，同时将是否允许进出学校返回给主页面
			    container.put("result", res);
			    out.print(gson2.toJson(container));
			}else{
				//如果认证和一，则从数据库查找此人
				container.put("id",requestMap.get("id"));
				container.put("name",requestMap.get("name"));
				String messages=dbTool.getMessage(gson2.toJson(container));
				 out.print(messages);
			}
	    }
	    else{
	    	System.out.print("identity illegal");
	    	container.put("result", "failed");
	    	container.put("reason", "identity illegal");
	    	out.print(gson2.toJson(container));
	    }

	}
}
