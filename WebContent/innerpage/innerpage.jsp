<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection,java.sql.Statement,java.util.Scanner,java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>innerpage</title>
    <link rel="stylesheet" type="text/css" href="main.css">
    <script type="text/javascript" src="jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="innerOp.js"></script>
</head>
<body>
    <div id="content_bg">
    </div>
    <div id="wrapper">
        <div id="menu">
            <nav>
                <ul>
                    <li id="realtime_info" class="current" onclick="show_realtime_info()">
                        <a href="#realtimeinfo">实时信息</a>
                    </li>
                    <li id="all_record" onclick="show_all_data()">
                        <a href="#allrecord">所有记录</a>
                    </li>
                    <li id="black_record" onclick="show_black_record()">
                        <a href="#blackrecord">黑名单记录</a>
                    </li>
                    <li id="unreserved_record" onclick="show_unreserved_record()">
                        <a href="#unreservedrecord">未预约记录</a>
                    </li>
                    <li id="settings" onclick="show_settings()">
                        <a href="#settings">设置</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div id="content">
            <div id="info_div" class="each_ctt">
                <div id="photos">
                    <img src="" width="400" height="300" id="cap_photo">
                    <div id="s_photo">
                        <img src="" width="100" height="120" id="db_photo">
                        <img src="" width="100" height="120" id="id_photo">
                        <div id="expr">身份：</div>
                    </div>
                </div>
                <div id="text_info">
                    <div id="name">姓名：</div>
                    <div id="id_num">身份证号：</div>
                    <div id="name_stu">学生姓名：</div>
                    <div id="cls">学生班级：</div>
                    <div id="teacher">班主任：</div>
                    <div id="t_contact">班主任联系方式：</div>
                </div>
                <div id="status">
                	通过
                </div>
            </div>
            <form id="data_table" class="each_ctt" style="display: none;">
                <div id="data_table_wrapper">
                    <table id="data_table_id">
                        <thead>
                            <tr>
                                <th class="cols" rowspan="1" colspan="1">序号</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">身份证号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">学生姓名&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">班级&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">班主任&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">班主任联系方式&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="3">拍摄照片&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                <th class="colsnotindex" rowspan="1" colspan="1">状态&nbsp;&nbsp;&nbsp;&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody id="table_body">
       <%
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
                    <div id="page_end">
                        <div id="page_info">本页显示 条记录，一共 条记录。</div>
                        <div id="page_up_down">
                            <ul>
                                <li id="previous">
                                    <a href="innerpage.jsp?pgno=<%=PageNow-1 %>&pgcnt=5#allrecord">上一页</a>
                                </li>
                                <li id="next">
                                    <a href="innerpage.jsp?pgno=<%=PageNow+1 %>&pgcnt=5#allrecord">下一页</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <button type="button" onclick="add_data('table_body', 'NEVRE', '111111111111111111', 'flute', '18班', 'VAN', '12345678901', '', '拒绝')">add</button>
            </form>
        </div>
    </div>
</body>
</html>