package dbTools;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import com.google.gson.reflect.TypeToken;


public class dbTools {
	public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/schoolsys";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "123456";

	static {
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			System.out.println("fail");
			e.printStackTrace();
		}
	}

	public static Connection getConn() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);

	}

	public static void closeConn(Connection conn) {
		if (null != conn) {

			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("关闭连接失败！");
				e.printStackTrace();
			}
		}
	}

	public String getMessage(String id, String name) {
		dbTools dbUtil = new dbTools();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> temping = new HashMap<String, Object>();
		String json = "";
		String p = dbUtil.getParent(id);
		String b = dbUtil.getBlacklist(id);
		Gson gson = new Gson();
		if (!p.equals("{}")) {
			return p;
		}
		if (!b.equals("{}")) {
			return b;
		} else {
			temping.put("identity", "noresult");
			map.put("id", id);
			map.put("name", name);
			temping.put("message", map);
		}
		json = gson.toJson(temping);
		return json;

	}

	public String getParent(String IDnumber) { // 对比家长信息；
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from parent left join student on parent.sid=student.sid left join worker on student.tid=worker.wid where parent.id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, IDnumber);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("student", rs.getString("sname"));
				resultMap.put("teacher", rs.getString("wname"));
				resultMap.put("id", rs.getString("id"));
				resultMap.put("class", rs.getString("class"));
				resultMap.put("name", rs.getString("name"));
				resultMap.put("teacherphone", rs.getString("phone"));
				resultMap.put("studentgender", rs.getString("sgender"));
			}
			temping.put("identity", "parent");
			temping.put("message", resultMap);
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;

	}

	public String getAllpic() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		int total = 0;
		ArrayList<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql1 = "select sid,spic from student";
			String sql2 = "select wid,wpic from worker";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("sid"));
				map.put("pic", rs.getString("spic"));
				list.add(map);
				total++;
			}
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("wid"));
				map.put("pic", rs.getString("wpic"));
				list.add(map);
				total++;
			}
			temping.put("picture", list);
			temping.put("total", total);
			json = gson.toJson(temping);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;

	}

	public String getBlacklist(String IDnumber) {// 黑名单读取操作；
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from schoolsys.blacklist left join schoolsys.worker on blacklist.contactid=worker.wid where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, IDnumber);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", "blacklist");
				resultMap.put("id", rs.getString("id"));
				resultMap.put("note", rs.getString("note"));
				resultMap.put("name", rs.getString("name"));
				resultMap.put("personincharge", rs.getString("wname"));
				resultMap.put("personinchargephone", rs.getString("phone"));
				resultMap.put("personinchargeposition", rs.getString("position"));
			}
			temping.put("identity", "blacklist");
			temping.put("message",resultMap);
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String addNewRecord(String message) {
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
		}.getType());
		try {
			String sql = "insert into schoolsys.record (name,identity,status,pic,sid,tid,time) values(?,?,?,?,?,?,?)";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, map.get("name"));
			ps.setString(2, map.get("identity"));
			ps.setString(3, map.get("status"));
			ps.setString(4, map.get("pic"));
			ps.setString(5, map.get("sid"));
			ps.setString(6, map.get("tid"));
			ps.setString(7, map.get("date"));
			ps.executeQuery();
			resultMap.put("message", "ok");
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();

			if (e.getClass().equals(SQLException.class)) {
				resultMap.put("message", "fail");
				json = gson.toJson(resultMap);
			}
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getAllRecord() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "select a.id,a.name,a.identity,a.status,c.sname,d.wname,a.time from schoolsys.record a,schoolsys.student c,schoolsys.worker d where a.sid=c.sid and a.tid=d.wid ;";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			Map<String, String>[] container = new Map[rowCount];
			if (rs.first()) {
				// 有的话 先获取第一条记录
				int index = 0;
				do {
					Map<String, String> temping = new HashMap<String, String>();
					temping.put("id", rs.getString("id"));
					temping.put("name", rs.getString("name"));
					temping.put("identity", rs.getString("identity"));
					temping.put("status", rs.getString("status"));
					temping.put("sname", rs.getString("sname"));
					temping.put("tname", rs.getString("wname"));
					temping.put("time", rs.getString("time"));
					container[index++] = temping;
				} while (rs.next());

				resultMap.put("total", rowCount);
				resultMap.put("message", container);
				json = gson.toJson(resultMap);
			} else {
				resultMap.put("total", rowCount);
				resultMap.put("message", container);
				json = gson.toJson(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getRecordDetailById(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "select a.id,a.name,a.identity,a.status,c.sname,d.wname,a.time,a.pic as newpic,b.pic as logpic from schoolsys.record a,schoolsys.parent b,schoolsys.student c,schoolsys.worker d where a.id=b.id and a.sid=c.sid and a.tid=d.wid and a.id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultMap.put("id", rs.getString("id"));
				resultMap.put("name", rs.getString("name"));
				resultMap.put("identity", rs.getString("identity"));
				resultMap.put("status", rs.getString("status"));
				resultMap.put("sname", rs.getString("sname"));
				resultMap.put("tname", rs.getString("wname"));
				resultMap.put("date", rs.getString("time"));
				resultMap.put("newpic", rs.getString("newpic"));
				resultMap.put("logpic", rs.getString("logpic"));
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String setStudentCanleave(String message) {
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = gson.fromJson(message, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		try {
			String sql = "update schoolsys.student set canleave='" + map.get("status") + "' where sid='" + map.get("id")
					+ "';";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeQuery();
			resultMap.put("message", "ok");
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();

			if (e.getClass().equals(SQLException.class)) {
				resultMap.put("message", "fail");
				json = gson.toJson(resultMap);
			}
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String setScPeopleStay(String message) {
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = gson.fromJson(message, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		try {
			String sql;
			if (map.get("identity").equals("student")) {
				sql = "update schoolsys.student set isschool='" + map.get("status") + "' where sid='" + map.get("id")
						+ "';";
			} else if (map.get("identity").equals("worker")) {
				sql = "update schoolsys.student set isschool='" + map.get("status") + "' where wid='" + map.get("id")
						+ "';";

			} else {
				sql = "update schoolsys.student set isschool='" + map.get("status") + "' where id='" + map.get("id")
						+ "';";
			}
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeQuery();
			resultMap.put("message", "ok");
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();

			if (e.getClass().equals(SQLException.class)) {
				resultMap.put("message", "fail");
				json = gson.toJson(resultMap);
			}
		}
		dbTools.closeConn(conn);
		return json;
	}
	
	public String parentCome(String identity,String id,String imgnow){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		String isorder="";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from parent left join student on parent.sid=student.sid left join worker on student.tid=worker.wid where parent.id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", identity);
				resultMap.put("imgnow",imgnow);
				resultMap.put("teacherName", rs.getString("wname"));
				resultMap.put("id", rs.getString("id"));
				resultMap.put("class", rs.getString("class"));
				resultMap.put("name", rs.getString("name"));
				resultMap.put("teacherphone", rs.getString("phone"));
				resultMap.put("imglog", rs.getString("pic"));
				resultMap.put("imgstu", rs.getString("spic"));
				resultMap.put("stuName", rs.getString("sname"));
				isorder=rs.getString("isordered");
			}
			temping.put("details", resultMap);
			if (isorder.equals("Y")) {
				temping.put("result", "allowed");
			} else if(isorder.equals("N")) {
				temping.put("result", "notallowed");
			}
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}
	
	public String refuseBlackList(String identity,String id,String imgnow){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from schoolsys.blacklist left join schoolsys.worker on blacklist.contactid=worker.wid where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", identity);
				resultMap.put("id", rs.getString("id"));
				resultMap.put("note", rs.getString("note"));
				resultMap.put("imgnow", imgnow);
				resultMap.put("imglog", rs.getString("pic"));
				resultMap.put("contactPhone", rs.getString("phone"));
				resultMap.put("contactPosition", rs.getString("position"));
				resultMap.put("contactName", rs.getString("wname"));
			}
			temping.put("result", "notallowed");
			temping.put("details", resultMap);
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}
	
	public String studentCome(String identity,String id,String imgnow) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		String canleave="";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql="select * from schoolsys.student left join schoolsys.worker on student.tid=worker.wid where sid=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", identity);
				resultMap.put("id", rs.getString("sid"));
				resultMap.put("imgnow", imgnow);
				resultMap.put("imglog", rs.getString("spic"));
				resultMap.put("name", rs.getString("sname"));
				resultMap.put("teacherPhone", rs.getString("phone"));
				resultMap.put("class", rs.getString("class"));
				resultMap.put("teacherName", rs.getString("wname"));
				canleave=rs.getString("canleave");				
			}
			temping.put("details", resultMap);
			if (canleave.equals("Y")) {
				temping.put("result", "allowed");
			} else if(canleave.equals("N")) {
				temping.put("result", "notallowed");
			}
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}
	
	public String workerCome(String identity,String id,String imgnow){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql="select * from schoolsys.worker where wid=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", identity);
				resultMap.put("id", rs.getString("wid"));
				resultMap.put("imgnow", imgnow);
				resultMap.put("imglog", rs.getString("wpic"));
				resultMap.put("name", rs.getString("wname"));
				resultMap.put("phone", rs.getString("phone"));
				resultMap.put("position", rs.getString("position"));	
			}
			temping.put("result", "allowed");
			temping.put("details", resultMap);
			json=gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}
	
	public String allowedPeople(String message){
		Gson gson=new Gson();
		dbTools db=new dbTools();
		Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String >>() {
		}.getType());
		if (map.get("identity").equals("student")) {
			return db.studentCome(map.get("identity"), map.get("id"), map.get("imgnow"));
		}else if (map.get("identity").equals("parent")) {
			return db.parentCome(map.get("identity"), map.get("id"), map.get("imgnow"));
		}else if (map.get("identity").equals("blacklist")) {
			return db.refuseBlackList(map.get("identity"), map.get("id"), map.get("imgnow"));
		}else {
			return db.workerCome(map.get("identity"), map.get("id"), map.get("imgnow"));
		}
	}
	

}
