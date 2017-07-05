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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import faceRecognition.CompareModule;
import faceRecognition.EncodeModule;

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
		    //ֻ������һ��ͼƬ����ְ����ѧ��
   	        map2.get("picture");
   	        File file1=new File("D:\\image\\wenhao.png");
			String s1 = EncodeModule.encodeImgageToBase64(file1);
			//System.out.println(s1);
   			CompareModule.init();
   			String re=CompareModule.Compare(s1, map2.get("picture"));
   			System.out.println(re);
		    container.put("result", "success");
		    out.print(gson2.toJson(container));
		    
	    }else if(request.getParameter("identity").equals("society")){
	    	
	    	String requestMessage=request.getParameter("message");
	    	System.out.print("+++"+request.getParameter("identity")+"+++");
	    	System.out.print(requestMessage);
		    Map<String, String> map2 = gson2.fromJson(requestMessage, type); 
		    
		    String imgPath1="D:\\image\\wenhao.png";
			//���֤��Ƭ
			String imgPath2="D:\\image\\Pro.jpg";
			
		/*
		File file1=new File(imgPath1);
		File file2=new File(imgPath2);
		    
		String s1 = EncodeModule.encodeImgageToBase64(file1);
		String s2 = EncodeModule.encodeImgageToBase64(file2);
			CompareModule.init();*/
			
			System.out.println(map2.get("picture1"));
			System.out.println(map2.get("picture2"));
			
			EncodeModule.decodeBase64ToImage(map2.get("picture1"));
			//String res = CompareModule.Compare(s1, s2);
			String res = CompareModule.Compare(map2.get("picture1"), map2.get("picture2"));
			
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
