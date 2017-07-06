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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dbTools.dbTools;
import faceRecognition.CompareModule;
import faceRecognition.EncodeModule;
import faceRecognition.IdentifyModule;

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
//		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		BufferedReader br =request.getReader();
		
//        String inputLine;
// 	    String str = "";
// 	    try {
// 	      while ((inputLine = br.readLine()) != null) {
// 	        str += inputLine;
//       }
// 	      br.close();
// 	    } catch (IOException e) {
// 	      System.out.println("IOException: " + e);
// 	    }
// 	    
// 	   System.out.print(str);
 	    
		dbTools dbTool=new dbTools();
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

    	Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
	    Type type = new TypeToken<Map<String, Object>>() {}.getType();  
	    Map<String,String> container=new HashMap<String,String>();
	    if(request.getParameter("identity").equals("school")){
	    	
	    	String requestMessage=request.getParameter("message");
	    	System.out.println(requestMessage);
		    Map<String, String> requestMap = gson2.fromJson(requestMessage, type); 
		    
		    File file1=new File("D:\\image\\wenhao.png");
			String s1 = EncodeModule.encodeImgageToBase64(file1);
		    
		    //只传过来一张图片，教职工或学生
		    String allPicJson=dbTool.getAllpic();
		    Map<String, Object> allPicMessage = gson2.fromJson(allPicJson, type); 
		    
		    System.out.println(allPicMessage.get("picture"));
		    System.out.println(allPicMessage.get("picture").getClass());
		   
		    String js=gson2.toJson(allPicMessage.get("picture"));
		    
			IdentifyModule.init((JSONArray)allPicMessage.get("picture"));
			
			String re=IdentifyModule.identify(requestMap.get("picture").replaceAll(" ","+"));
			
		    System.out.println(js);
//		    CompareModule.init();
//   			String re=CompareModule.Compare(s1, requestMap.get("picture").replaceAll(" ","+"));
   			System.out.println(re);
		    container.put("result", "success");
		    out.print(gson2.toJson(container));
		    
	    }else if(request.getParameter("identity").equals("society")){
	    	
	    	String requestMessage=request.getParameter("message");
	    	System.out.print(requestMessage);
		    Map<String, String> requestMap = gson2.fromJson(requestMessage, type); 

//			System.out.println(requestMap.get("picture1"));
//			System.out.println(requestMap.get("picture2"));
			
		    //首先判断是否人证合一
		    CompareModule.init();
			String res = CompareModule.Compare(requestMap.get("picture1").replaceAll(" ","+"), requestMap.get("picture2").replaceAll(" ","+"));
			System.out.println(res);
			
			//如果认证和一，则从数据库查找此人
			String messages=dbTool.getMessage(requestMap.get("id"),requestMap.get("name"));
			Map<String, Object> queryResult = gson2.fromJson(messages, type); 
			
			//将此人基本信息返回给主页面，同时将是否允许进出学校返回给主页面
		    container.put("result", "success");
		    container.put("res", res);
		    out.print(gson2.toJson(container));
	    }
	    else{
	    	System.out.print("identity illegal");
	    	container.put("result", "failed");
	    	container.put("reason", "identity illegal");
	    	out.print(gson2.toJson(container));
	    }

	}
}
