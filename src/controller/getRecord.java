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
 * Servlet implementation class getRecord
 */
@WebServlet("/getRecord")
public class getRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getRecord() {
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
		//��ǰҳ
		int currpage = Integer.parseInt(request.getParameter("currpage")==null?"1":request.getParameter("currpage"));
		//�ܵļ�¼��
		int total = this.getResultCount();

		//��ҳ��λ
		int pagesize = 5;
		//Page�����
		Page page = new Page(total,currpage,pagesize);
		if(currpage<=1){
			currpage=1;
			page.setCurrpage(currpage);
		}else if(currpage>=page.getPagecount()){
			currpage=page.getPagecount();
			page.setCurrpage(currpage);
		}
		//���ò�ѯ����
		ResultSet rs = this.getResultSet(page.getStart(),page.getPagesize());
	
		//���ڷ��ظ�ǰ̨ҳ���JSON�ĵ�
		Map<String,Object> container=new HashMap<String,Object>();
		try {
			int i=0;
		    int rows =0;
			//������ݿ��ѯ����������
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
		//��ӷ�ҳ��Ϣ
		container.put("currpage", ""+page.getCurrpage());
		container.put("pagecount",""+page.getPagecount());
		
		Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		//���ô�ӡ����
		this.print(request, response, gson2.toJson(container));
	}
	
	/**
	 * @category ��ӡ��XMLDOM�ĵ�,����ǰ̨ҳ��Ľ���
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
	 * @category ���ص�ǰҳ�Ĳ�ѯ���
	 * @param �к�
	 * @param ����
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
	    String sql = "SELECT id,name,identity,status,sname,wname,time FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid ORDER BY id desc limit ?,?";

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
	 * @return ���ݿ����ܵļ�¼��
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
		String sql = "select count(*) from schoolsys.record";
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
	 * @return ȡ�����ݿ���������
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
