<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection,java.sql.Statement,java.util.Scanner,java.sql.*,dbTools.*"%>
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
	<script type="text/javascript" src="../js/ajax.js"></script>
</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<nav class="navbar navbar-default" role="navigation">
				<div class="navbar-header">
					 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="#">后台</a>
				</div>
				
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li >
							 <a href="student.jsp">学生管理</a>
						</li>
						<li >
							 <a href="worker.jsp">职工管理</a>
						</li>
						<li class="active">
							 <a href="parent.jsp">家长管理</a>
						</li>
						<li>
							 <a href="blacklist.jsp">黑名单管理</a>
						</li>
					</ul>
					<form class="navbar-form navbar-left" role="search" method="GET" action="parent.jsp">
						<div class="form-group">
							<input type="text" class="form-control" name="id"/>
						</div> <button type="submit" class="btn btn-default ">Submit</button>
					</form>
				</div>
			</nav>
		</div>

		<div class="row clearfix">
		<div class="col-md-12 column">
<table class="table table-hover">
      <thead>
	      <tr><th class='c1'>身份证号</th><th class='c2'>姓名</th><th class='c3'>照片</th><th class='c4'>孩子姓名</th><th class='c5'>班级</th>
	      <th class='c6'>是否预约</th><th class='c7'>老师姓名</th><th class='c8'>老师联系方式</th><th class='c9'>操作一</th><th class='c10'>操作二</th></tr>
      </thead>
      <tbody>
        
       <%
       if(request.getParameter("id")!=null){
    	   ResultSet rs;
    	 //**连接数据库**
			try{
			    String connectString = "jdbc:mysql://localhost:3306/schoolsys"
			            + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
			    String user = "root";
			    String pwd = "123456";
			    String sql="SELECT id,name,pic,sname,class,isordered,wname,phone FROM schoolsys.parent left join schoolsys.student on parent.sid=student.sid left join schoolsys.worker on tid=wid where id=\"" + request.getParameter("id")+ "\"";
			 //   String sql = "SELECT id,name,identity,status,sname,wname,time FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid ORDER BY id desc";
			    Class.forName("com.mysql.jdbc.Driver");
			    Connection conn = DriverManager.getConnection(connectString, user, pwd);
			
			    Statement state = conn.createStatement();
			    rs = state.executeQuery(sql);
			    
			    if(rs.next()){
			        %>        
       <tr>
	        <td><%=rs.getString("id") %></td>
	        <td><%=rs.getString("name") %></td>
	        <td><img style="height:100px;width: auto;" src=<%="data:image/jpeg;base64,"+rs.getString("pic") %>/></td>
	        <td><%=rs.getString("sname") %></td>
	        <td><%=rs.getString("class") %></td>
	        <td><%=rs.getString("isordered") %></td>
	        <td><%=rs.getString("wname") %></td>
	        <td><%=rs.getString("phone") %></td>
	        <% if(rs.getString("isordered").equals("Y")) {%>    
	        <td><button class="btn btn-block btn-default" value="N"  id='<%=rs.getString("id") %>' >
	        取消预约
	        </button></td>
	        <%}else{ %>
	        <td><button class="btn btn-block btn-default"  value="Y"  id='<%=rs.getString("id") %>' > 
	        预约
	        </button></td>	        
 			<%} %>	  
 			<td><button class="btn btn-block btn-default"  value=<%=rs.getString("id") %>  id='<%="b"+rs.getString("id") %>' >
	        拉入黑名单
	        </button></td>	  
        </tr>
<script type="text/javascript">
var btnId='<%=rs.getString("id")%>';
document.getElementById(btnId).addEventListener('click',function(e){
	changeOrderStatus(e.target.id,e.target.value);
} );
var btnId2='<%="b"+rs.getString("id")%>';
document.getElementById(btnId2).addEventListener('click',function(e){
	if(confirm("确定要拉黑他么？此操作不可撤销。")){
		makeBlacklist(e.target.value);
	}else{
	}
} );

</script>    
			         <%
			    }else{
			    	%>
			 <script type="text/javascript">
				alert("输入的身份证号有误，请重试！");
				window.location="parent.jsp";
			 </script>
			    	<%
			    }

			    rs.close();
			    state.close();
			    conn.close();
			    }catch (Exception e){
			    e.printStackTrace();
			}
       }else{
			String pgno = "";  //网址中传递的页面数据
			String pgcnt = ""; //网址传递的每页最大显示数目
			int RowAmount = 0; //数据库中总的行数
			int PageAmount = 0; //数据库所有页面可以组成多少个页面
			int PageSize=5; //每页最大显示数目
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
			    String sql="SELECT id,name,pic,sname,class,isordered,wname,phone FROM schoolsys.parent left join schoolsys.student on parent.sid=student.sid left join schoolsys.worker on tid=wid";
			 //   String sql = "SELECT id,name,identity,status,sname,wname,time FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid ORDER BY id desc";
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
       <tr>
	        <td><%=rs.getString("id") %></td>
	        <td><%=rs.getString("name") %></td>
	        <td><img style="height:100px;width: auto;" src=<%="data:image/jpeg;base64,"+rs.getString("pic") %>/></td>
	        <td><%=rs.getString("sname") %></td>
	        <td><%=rs.getString("class") %></td>
	        <td><%=rs.getString("isordered") %></td>
	        <td><%=rs.getString("wname") %></td>
	        <td><%=rs.getString("phone") %></td>
	        <% if(rs.getString("isordered").equals("Y")) {%>    
	        <td><button class="btn btn-block btn-default" value="N"  id='<%=rs.getString("id") %>' >
	        取消预约
	        </button></td>
	        <%}else{ %>
	        <td><button class="btn btn-block btn-default"  value="Y"  id='<%=rs.getString("id") %>' > 
	        预约
	        </button></td>	        
 			<%} %>
 			 <td><button class="btn btn-block btn-default"  value=<%=rs.getString("id") %>  id='<%="b"+rs.getString("id") %>' > 
	        拉入黑名单
	        </button></td>	  	        
        </tr>
<script type="text/javascript">
var btnId='<%=rs.getString("id")%>';
document.getElementById(btnId).addEventListener('click',function(e){
	changeOrderStatus(e.target.id,e.target.value);
} );
var btnId2='<%="b"+rs.getString("id")%>';
document.getElementById(btnId2).addEventListener('click',function(e){
	if(confirm("确定要拉黑他么？此操作不可撤销。")){
		makeBlacklist(e.target.value);
	}else{
	}
} );
</script>        
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
		
<script type="text/javascript">
var changeCanLeave=function(e){
	console.log(e.target.id);
	<% dbTools dbTool=new dbTools();%>
}
</script>

       <br/><br/>  
       <div style="float:right">
	        <a type="button" class="btn btn-default " href="parent.jsp?pgno=<%=PageNow-1 %>&pgcnt=5">
	                        上一页</a>    &nbsp;               
	        <a type="button" class="btn btn-default " href="parent.jsp?pgno=<%=PageNow+1 %>&pgcnt=5">
	                       下一页</a>      &nbsp;   
	       <button type="button" class="btn btn-default " onclick="window.history.back(-1);">返回</button>
       </div>
 <%} %>


</div>
</div>
<script type="text/javascript">
var divDisplay=function(){
	document.getElementById("beblack").style.display="none";
}
</script>
</body>
</html>