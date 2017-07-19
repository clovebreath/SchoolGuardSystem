package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Page;

/**
 * Servlet implementation class getParentRecord
 */
@WebServlet("/getParentRecord")
public class getParentRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getParentRecord() {
        super();
        // TODO Auto-generated constructor stub
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
		//当前页
		int currpage = Integer.parseInt(request.getParameter("currpage")==null?"1":request.getParameter("currpage"));
		//总的记录数
		int total = this.getResultCount();

		//分页单位
		int pagesize = 8;
		//Page类对象
		Page page = new Page(total,currpage,pagesize);
		if(currpage<=1){
			currpage=1;
			page.setCurrpage(currpage);
		}else if(currpage>=page.getPagecount()){
			currpage=page.getPagecount();
			page.setCurrpage(currpage);
		}
		//调用查询方法
		ResultSet rs = this.getResultSet(page.getStart(),page.getPagesize());
	
		//用于返回给前台页面的JSON文档
		Map<String,Object> container=new HashMap<String,Object>();
		try {
			int i=0;
		    int rows =0;
			//添加数据库查询出来的数据
	      	if (rs.last()) {  
	      			rows=rs.getRow();
	      			Map<String, String>[] canvas=new Map[rows];
	      			rs.first();
	      	      do{  
	  				Map<String,String> temping=new HashMap<String,String>();
					temping.put("id", rs.getString("id"));
					temping.put("name", rs.getString("name"));
					temping.put("identity", rs.getString("identity"));
					temping.put("status", rs.getString("status"));
					temping.put("sname", rs.getString("sname"));
					temping.put("wname", rs.getString("wname"));
					temping.put("time", rs.getString("time"));
					canvas[i++]=temping;
	      	      } while(rs.next());      
	  			container.put("records", canvas);
	      	 }  	
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//添加分页信息
		container.put("currpage", ""+page.getCurrpage());
		container.put("pagecount",""+page.getPagecount());
		
		Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		//调用打印方法
		this.print(request, response, gson2.toJson(container));
	}
	
	/**
	 * @category 打印出XMLDOM文档,用于前台页面的接收
	 * @param request
	 * @param response
	 * @param JSON
	 * @throws IOException
	 */
	private void print(HttpServletRequest request, HttpServletResponse response,String JSON) throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(JSON);
		out.close();
	}
	
	/**
	 * @category 返回当前页的查询结果
	 * @param 行号
	 * @param 长度
	 * @return ResultSet
	 */
	private ResultSet getResultSet(int start,int len){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	    String sql = "SELECT id,name,identity,status,sname,wname,time FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid where identity='parent' ORDER BY id desc limit ?,?";

		//String sql = "select * from person order by pid limit ?,?"; 
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, len);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * @return 数据库中总的记录数
	 */
	private int getResultCount(){
		int count=0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		String sql = "select count(*) from schoolsys.record where identity='parent'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			while(rs.next()){
				count = rs.getInt(1);
			}
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return count;
	}
	/**
	 * @return 取得数据库连接驱动
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection getConn() throws ClassNotFoundException, SQLException{
		
		String driver = "com.mysql.jdbc.Driver";
		String url =  "jdbc:mysql://localhost:3306/schoolsys"
	            + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
		String user = "root";
		String password = "123456";
		
		Class.forName(driver);
		return DriverManager.getConnection(url,user,password);
	}
}
