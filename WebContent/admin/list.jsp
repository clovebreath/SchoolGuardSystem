<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection,java.sql.Statement,java.util.Scanner,java.sql.*"%>
<!DOCTYPE html>
<html lang=“zh-CN”>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
      <table class="table table-hover">
      <thead>
	      <tr><th class='c1'>编号</th><th class='c2'>姓名</th><th class='c3'>身份</th><th class='c4'>状态</th><th class='c5'>学生姓名</th><th class='c6'>预约老师</th><th class='c7'>时间</th></tr>
      </thead>
      <tbody>
        
       <%
			String pgno = "";  //网址中传递的页面数据
			String pgcnt = ""; //网址传递的每页最大显示数目
			int RowAmount = 0; //数据库中总的行数
			int PageAmount = 0; //数据库所有页面可以组成多少个页面
			int PageSize; //每页最大显示数目
			int PageNow;  //当前页面
			ResultSet rs;
			if(request.getParameter("pgno")!=null){  //获取网址从传递的数据
			    pgno = request.getParameter("pgno");
			}else{
			    pgno = "1";  //赋给初始值防止没有传入数据时崩溃
			}
			PageNow = java.lang.Integer.parseInt(pgno);
			if(PageNow <= 0){
			    PageNow = 1;
			}
			
			if(request.getParameter("pgcnt")!=null){
			    pgcnt = request.getParameter("pgcnt");
			}else{
			    pgcnt = "5";
			}
			PageSize = java.lang.Integer.parseInt(pgcnt); //**转换为int类型**

			//**连接数据库**
			try{
			    String connectString = "jdbc:mysql://localhost:3306/schoolsys"
			            + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
			    String user = "root";
			    String pwd = "123456";
			    String sql = "SELECT id,name,identity,status,sname,wname,time FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid ORDER BY id desc";
			    Class.forName("com.mysql.jdbc.Driver");
			    Connection conn = DriverManager.getConnection(connectString, user, pwd);
			
			    Statement state = conn.createStatement();
			    rs = state.executeQuery(sql);
			    //获取数据库行数
			    rs.last();
			    RowAmount = rs.getRow();
			   // 计算数据库中数据最大页面数
			    PageAmount = (RowAmount + PageSize - 1 )/ PageSize;
			    if(PageNow > PageAmount){
			        PageNow = PageAmount;
			    }
			   // 将当前的rs指针指向要显示的页面首条数据
			    if(PageAmount > 0){
			        rs.absolute((PageNow - 1)*PageSize + 1); 
			    }
			    //循环获取数据
			    for(int i = 0 ; i < PageSize && !rs.isAfterLast(); i++){
        %>        
        <a href="details.jsp?id=<%= rs.getString("id") %>"><tr>
	        <th scope="row"><%=rs.getString("id") %></th>
	        <td><%=rs.getString("name") %></td>
	        <td><%=rs.getString("identity") %></td>
	        <td><%=rs.getString("status") %></td>
	        <td><%=rs.getString("sname") %></td>
	        <td><%=rs.getString("wname") %></td>
	        <td><%=rs.getString("time") %></td>
        </tr></a>
        <%
		        rs.next();
		    }
		    rs.close();
		    state.close();
		    conn.close();
		    }catch (Exception e){
		    e.printStackTrace();
		}
		%>    
		</tbody>
		</table>

       <br/><br/>  
       <div style="float:right">
	        <a type="button" class="btn btn-default " href="list.jsp?pgno=<%=PageNow-1 %>&pgcnt=5">
	                        上一页</a>    &nbsp;               
	        <a type="button" class="btn btn-default " href="list.jsp?pgno=<%=PageNow+1 %>&pgcnt=5">
	                       下一页</a>      &nbsp;   
	       <button type="button" class="btn btn-default " onclick="location.href='index.jsp'">返回</button>
       </div>
</body>
</html>