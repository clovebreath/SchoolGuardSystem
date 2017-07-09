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

		gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		type = new TypeToken<Map<String, Object>>() {}.getType();  
		dbTool=new dbTools();
	    String allPicJson=dbTool.getAllpic();
	    Map<String, Object> allPicMessage = gson2.fromJson(allPicJson, type); 
	    
	    JSONObject jObject = new JSONObject(allPicMessage);
	    JSONArray ja = jObject.getJSONArray("picture");
	    
	    
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
		    System.out.println(requestMessage);

		    //1对多匹配
			String re=IdentifyModule.identify(requestMap.get("picture").replaceAll(" ","+"));
		
			if(re.equals(ProjectInfomation.NOT_MATCH)){
				container.put("result", re);
				out.print(gson2.toJson(container));
			}else if(re.equals(ProjectInfomation.PHOTO_ERROR)){
				container.put("result", re);
				out.print(gson2.toJson(container));
			}else if(re.equals(ProjectInfomation.MODULE_INIT_ERROR)){
				container.put("result", re);
				out.print(gson2.toJson(container));
			}else{
				
				//确定为校内人士之后，增加一条来访记录
				String schoolManMessage=dbTool.getSchoolMessage(re);
				Map<String, Object> schoolManRawMap = gson2.fromJson(schoolManMessage, type); 

				out.print(schoolManMessage);
if(schoolManRawMap.get("identity").equals("student")){
	String rawMessage=gson2.toJson(schoolManRawMap.get("message"));
	Map<String, String> newRecord= gson2.fromJson(rawMessage, new TypeToken<Map<String, String>>() {}.getType()); 
	newRecord.put("identity", (String)schoolManRawMap.get("identity"));
	newRecord.put("sid", null);
	newRecord.put("pid", null);
	
	System.out.println(gson2.toJson(newRecord));
	dbTool.addNewRecord(gson2.toJson(newRecord));
}else{
	
}

			}
		    
			
			
	    }else if(request.getParameter("identity").equals("society")){
	    	
	    	//避免传输过程中中文乱码
	    	String ss=request.getParameter("message");
	    	String requestMessage=new String((ss.getBytes("ISO-8859-1")),"UTF-8");
	    	
		    Map<String, String> requestMap = gson2.fromJson(requestMessage, new TypeToken<Map<String, String>>() {}.getType()); 
			
		    //首先判断是否人证合一
		    
			String res = CompareModule.Compare(requestMap.get("picture1").replaceAll(" ","+"), requestMap.get("picture2").replaceAll(" ","+"));
			System.out.println(res);
			if(res.equals(ProjectInfomation.PHOTO_ERROR)){
				//图片错误
				container.put("result", res);
			    out.print(gson2.toJson(container));
			}else if(res.equals(ProjectInfomation.ID_CARD_ERROR)){
				//将此人基本信息返回给主页面，同时将是否允许进出学校返回给主页面
			    container.put("result", res);
			    out.print(gson2.toJson(container));
			}else if(res.equals(ProjectInfomation.NOT_MATCH)){
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
