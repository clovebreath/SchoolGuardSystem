package dbTools;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.PDLOverrideSupported;

import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;
import java.sql.Connection;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.EscapeTokenizer;

import faceRecognition.EncodeModule;

public class dbTools {
	public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://121.250.222.38:3306/schoolsys";
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

	public static Connection getConn() throws SQLException {// 链接数据库；
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);

	}

	public static void closeConn(Connection conn) {// 关闭数据库；
		if (null != conn) {

			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("�ر�����ʧ�ܣ�");
				e.printStackTrace();
			}
		}
	}

	public String getMessage(String message) {// 根据身份证号查询外来人员信息
		dbTools dbUtil = new dbTools();
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> temping = new HashMap<String, Object>();
		Map<String, String> temp = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
		}.getType());
		String json = "";
		String p = dbUtil.getParent(temp.get("id"));
		String b = dbUtil.getBlacklist(temp.get("id"));
		if (!p.equals("{}")) {
			return p;
		}
		if (!b.equals("{}")) {
			return b;
		} else {
			temping.put("identity", "noresult");
			map.put("id", temp.get("id"));
			map.put("name", temp.get("name"));
			temping.put("message", map);
		}
		json = gson.toJson(temping);
		return json;
	}

	public String getParent(String IDnumber) { // 获取parent表的信息;
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
				resultMap.put("isordered", rs.getString("isordered"));
				temping.put("identity", "parent");
				temping.put("message", resultMap);
			}
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getSchoolMessage(String id) {// 根据工号或学号返回详细信息;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql1 = "select * from student left join worker on student.tid=worker.wid where student.sid=?";
			String sql2 = "select * from worker where wid=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql1);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("id", id);
				resultMap.put("pic", rs.getString("spic"));
				resultMap.put("name", rs.getString("sname"));
				resultMap.put("class", rs.getString("class"));
				resultMap.put("teachername", rs.getString("wname"));
				resultMap.put("canleave", rs.getString("canleave"));
				temping.put("identity", "student");
			}
			ps = conn.prepareStatement(sql2);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("id", id);
				resultMap.put("pic", rs.getString("wpic"));
				resultMap.put("name", rs.getString("wname"));
				resultMap.put("phone", rs.getString("phone"));
				temping.put("identity", rs.getString("position"));
			}
			temping.put("message", resultMap);
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getParentPic(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "select pic from parent where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("picture", rs.getString("pic"));
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getBlacklistPic(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "select pic from blacklist where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("picture", rs.getString("pic"));
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getStudentPic(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "select spic from student where sid=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("picture", rs.getString("spic"));
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getWorkerPic(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "select wpic from worker where wid=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("picture", rs.getString("wpic"));
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getPicById(String id) {//根据具体身份证、学号或工号返回个人注册照片
		dbTools dbUtil = new dbTools();
		String a = dbUtil.getParentPic(id);
		String b = dbUtil.getBlacklistPic(id);
		String c = dbUtil.getStudentPic(id);
		String d = dbUtil.getWorkerPic(id);
		if (!a.equals("{}")) {
			return a;
		}
		if (!b.equals("{}")) {
			return b;
		}
		if (!c.equals("{}")) {
			return c;
		} else {
			return d;
		}

	}

	public String getAllpic() {// 获取所有图片
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		int total = 0;
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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

	public String getBlacklist(String IDnumber) {// 获取黑名单信息
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
				temping.put("identity", "blacklist");
				temping.put("message", resultMap);
			}
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String addNewRecord(String message) {// 增加一条来访记录
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
			}.getType());
			String sql = "insert into schoolsys.record (name,identity,status,pic,sid,tid,time,pid) values(?,?,?,?,?,?,now(),?)";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			if (!map.get("name").equals(null) && !map.get("identity").equals(null)) {
				ps.setString(1, map.get("name"));
				ps.setString(2, map.get("identity"));
				ps.setString(3, map.get("status"));
				ps.setString(4, map.get("pic"));
				ps.setString(5, map.get("sid"));
				ps.setString(6, map.get("tid"));
				ps.setString(7, map.get("id"));
				ps.executeUpdate();
				resultMap.put("message", "ok");
				json = gson.toJson(resultMap);
			} else {
				resultMap.put("message", "fail");
				json = gson.toJson(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getAllRecord() {// 获取所有记录
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "SELECT id,name,identity,status,sname,wname,time FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid;";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			Map<String, String>[] container = new Map[rowCount];
			if (rs.first()) {
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

	public String getRecordDetailById(int id) {// 获取一条详细记录
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sq = "select * from schoolsys.record where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sq);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				String pid=rs.getString("pid");
				if (rs.getString("identity").equals("parent")) {
					String sql = "SELECT record.id,record.name,record.identity,status,sname,wname,time,record.pic as newpic,parent.pic as logpic,record.pid,student.class,parent.isordered,worker.phone FROM schoolsys.record left join worker on record.tid=worker.wid left join student on record.sid=student.sid left join parent on record.pid=parent.id where pid=? and record.id=?;";
					ps = conn.prepareStatement(sql);
					ps.setString(1, pid);
					ps.setInt(2, id);
					rs = ps.executeQuery();
					while (rs.next()) {
						resultMap.put("id", rs.getString("id"));
						resultMap.put("name", rs.getString("name"));
						resultMap.put("identity", rs.getString("identity"));
						resultMap.put("status", rs.getString("status"));
						resultMap.put("sname", rs.getString("sname"));
						resultMap.put("tname", rs.getString("wname"));
						resultMap.put("date", rs.getString("time"));
						resultMap.put("newpic", rs.getString("newpic"));
						resultMap.put("logpic", rs.getString("logpic"));
						resultMap.put("pid", rs.getString("pid"));
						resultMap.put("class", rs.getString("class"));
						resultMap.put("order", rs.getString("isordered"));
						resultMap.put("phone", rs.getString("phone"));
					}
				} else if (rs.getString("identity").equals("student")) {
					String sql = "SELECT record.id,record.name,record.identity,status,time,record.pic as newpic,student.spic as logpic,student.class,student.sid,worker.phone,worker.wname FROM schoolsys.record left join student on record.pid=student.sid left join worker on student.tid=worker.wid where pid=? and record.id=?;";
					ps = conn.prepareStatement(sql);
					ps.setString(1, pid);
					ps.setInt(2, id);
					rs = ps.executeQuery();
					while (rs.next()) {
						resultMap.put("id", rs.getString("id"));
						resultMap.put("name", rs.getString("name"));
						resultMap.put("identity", rs.getString("identity"));
						resultMap.put("status", rs.getString("status"));
						resultMap.put("sname", null);
						resultMap.put("tname", rs.getString("wname"));
						resultMap.put("date", rs.getString("time"));
						resultMap.put("newpic", rs.getString("newpic"));
						resultMap.put("logpic", rs.getString("logpic"));
						resultMap.put("pid", rs.getString("sid"));
						resultMap.put("class", rs.getString("class"));
						resultMap.put("phone", rs.getString("phone"));
					}
				} else if (rs.getString("identity").equals("blacklist")) {
					String sql = "SELECT record.id,record.name,record.identity,status,time,record.pic as newpic,blacklist.pic as logpic,wname,record.pid,worker.phone,blacklist.note FROM schoolsys.record left join blacklist on record.pid=blacklist.id left join worker on blacklist.contactid=worker.wid where pid=? and record.id=?;";
					ps = conn.prepareStatement(sql);
					ps.setString(1, pid);
					ps.setInt(2, id);
					rs = ps.executeQuery();
					while (rs.next()) {
						resultMap.put("id", rs.getString("id"));
						resultMap.put("name", rs.getString("name"));
						resultMap.put("identity", rs.getString("identity"));
						resultMap.put("status", rs.getString("status"));
						resultMap.put("sname", null);
						resultMap.put("tname", null);
						resultMap.put("note", rs.getString("note"));
						resultMap.put("contactname", rs.getString("wname"));
						resultMap.put("date", rs.getString("time"));
						resultMap.put("newpic", rs.getString("newpic"));
						resultMap.put("logpic", rs.getString("logpic"));
						resultMap.put("pid", rs.getString("pid"));
						resultMap.put("phone", rs.getString("phone"));
					}
				}else {
					String sql = "SELECT record.id,record.name,record.identity,status,time,record.pic as newpic,worker.wpic as logpic,record.pid,worker.phone,worker.position FROM schoolsys.record left join worker on record.pid=worker.wid where pid=? and record.id=?;";
					ps = conn.prepareStatement(sql);
					ps.setString(1,pid);
					ps.setInt(2, id);
					rs = ps.executeQuery();
					while (rs.next()) {
						resultMap.put("id", rs.getString("id"));
						resultMap.put("name", rs.getString("name"));
						resultMap.put("identity", rs.getString("identity"));
						resultMap.put("status", rs.getString("status"));
						resultMap.put("sname", null);
						resultMap.put("tname", null);
						resultMap.put("date", rs.getString("time"));
						resultMap.put("newpic", rs.getString("newpic"));
						resultMap.put("logpic", rs.getString("logpic"));
						resultMap.put("pid", rs.getString("pid"));
						resultMap.put("phone", rs.getString("phone"));
						resultMap.put("position", rs.getString("position"));
					}
				}
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		dbTools.closeConn(conn);
		return json;
	}

	public String setStudentCanleave(String message) {// 修改学生请假状态
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		ResultSet rs = null;
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
			}.getType());
			String sq = "select * from schoolsys.student where sid=?";
			String sql = "update schoolsys.student set canleave=? where sid=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sq);
			ps.setString(1, map.get("id"));
			rs = ps.executeQuery();
			if (rs.next()) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, map.get("status"));
				ps.setString(2, map.get("id"));
				ps.executeUpdate();
				resultMap.put("message", "ok");
			} else {
				resultMap.put("message", "fail");
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String setScPeopleStay(String message) {// 修改在校状态
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		ResultSet rs = null;
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
			}.getType());
			String sql = "";
			String sq = "";
			if (map.get("identity").equals("student")) {
				sq = "select * from schoolsys.student where sid=?";
				sql = "update schoolsys.student set isschool=? where sid=?";
			} else if (map.get("identity").equals("worker")) {
				sq = "select * from schoolsys.worker where wid=?";
				sql = "update schoolsys.worker set isschool=? where wid=?";
			} else if (map.get("identity").equals("parent")) {
				sq = "select * from schoolsys.parent where id=?";
				sql = "update schoolsys.parent set isschool=? where id=?";
			}
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sq);
			ps.setString(1, map.get("id"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("isschool").equals("Y")) {
					ps = conn.prepareStatement(sql);
					ps.setString(1, "N");
					ps.setString(2, map.get("id"));
					ps.executeUpdate();
					resultMap.put("message", "ok");
				} else {
					ps = conn.prepareStatement(sql);
					ps.setString(1, "Y");
					ps.setString(2, map.get("id"));
					ps.executeUpdate();
					resultMap.put("message", "ok");
				}
			} else {
				resultMap.put("message", "fail");
			}
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String setOrder(String message) {//修改预约状态
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
			}.getType());
			String sql = "update schoolsys.parent set isordered=? where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, map.get("order"));
			ps.setString(2, map.get("id"));
			ps.executeUpdate();
			resultMap.put("message", "ok");
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String parentCome(String identity, String id, String imgnow) {// 判断家长是否能进入学校
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		String isorder = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from parent left join student on parent.sid=student.sid left join worker on student.tid=worker.wid where parent.id=?";
			String sql2="select max(id) from record ";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", identity);
				resultMap.put("imgnow", imgnow);
				resultMap.put("teacherName", rs.getString("wname"));
				resultMap.put("id", rs.getString("id"));
				resultMap.put("class", rs.getString("class"));
				resultMap.put("name", rs.getString("name"));
				resultMap.put("teacherPhone", rs.getString("phone"));
				resultMap.put("imglog", rs.getString("pic"));
				resultMap.put("imgstu", rs.getString("spic"));
				resultMap.put("stuName", rs.getString("sname"));
				isorder = rs.getString("isordered");
			}
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultMap.put("recordid", rs.getInt(1)+1);
			}
			temping.put("details", resultMap);
			if (isorder.equals("Y")) {
				temping.put("result", "allowed");
			} else if (isorder.equals("N")) {
				temping.put("result", "notallowed");
			}
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String refuseBlackList(String identity, String id, String imgnow) {// 拒绝黑名单进入
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from schoolsys.blacklist left join schoolsys.worker on blacklist.contactid=worker.wid where id=?";
			String sql2="select max(id) from record ";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("identity", identity);
				resultMap.put("id", rs.getString("id"));
				resultMap.put("name", rs.getString("name"));
				resultMap.put("note", rs.getString("note"));
				resultMap.put("imgnow", imgnow);
				resultMap.put("imglog", rs.getString("pic"));
				resultMap.put("contactPhone", rs.getString("phone"));
				resultMap.put("contactPosition", rs.getString("position"));
				resultMap.put("contactName", rs.getString("wname"));
			}
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultMap.put("recordid", rs.getInt(1)+1);
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

	public String studentCome(String identity, String id, String imgnow) {// 判断学生能否出入
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		String canleave = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from schoolsys.student left join schoolsys.worker on student.tid=worker.wid where sid=?";
			String sql2="select max(id) from record ";
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
				canleave = rs.getString("canleave");
			}
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultMap.put("recordid", rs.getInt(1)+1);
			}
			temping.put("details", resultMap);
			if (canleave.equals("Y")) {
				temping.put("result", "allowed");
			} else if (canleave.equals("N")) {
				temping.put("result", "notallowed");
			}
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String workerCome(String identity, String id, String imgnow) {// 教职工出入
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> temping = new HashMap<String, Object>();
		try {
			String sql = "select * from schoolsys.worker where wid=?";
			String sql2="select max(id) from record ";
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
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();
			if (rs.next()) {
				resultMap.put("recordid", rs.getInt(1)+1);
			}
			temping.put("result", "allowed");
			temping.put("details", resultMap);
			json = gson.toJson(temping);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String getSid(String name) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sid = null;
		try {
			String sql = "select sid from schoolsys.student where sname=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				sid = rs.getString("sid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return sid;
	}

	public String getTid(String name) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String wid = null;
		try {
			String sql = "select wid from schoolsys.worker where wname=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				wid = rs.getString("wid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return wid;
	}

	public String getBlacklistName(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String name = null;
		try {
			String sql = "select name from schoolsys.blacklist where id=?";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		return name;
	}
	
	public String changeRecordResult(int id) {//修改record的status状态
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "UPDATE `schoolsys`.`record` SET `status`='allowed' WHERE `id`=?;";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
			resultMap.put("message", "ok");
			json = gson.toJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}

	public String allowedPeople(String message) {// 处理模块总方法直接调用即可
		Gson gson = new Gson();
		dbTools db = new dbTools();
		Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
		}.getType());
		if (map.get("identity").equals("student")) {
			String str = db.studentCome(map.get("identity"), map.get("id"), map.get("imgnow"));
			Map<String, Object> temp = gson.fromJson(str, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (temp.get("result").equals("allowed")) {
				Map<String, Object> temp1 = new HashMap<String, Object>();
				Map<String, Object> temp2 = new HashMap<String, Object>();
				temp1.put("id", ((Map<String, String>) temp.get("details")).get("id"));
				temp1.put("identity", "student");
				temp2.put("name", ((Map<String, String>) temp.get("details")).get("name"));
				temp2.put("id", ((Map<String, String>) temp.get("details")).get("id"));
				temp2.put("identity", "student");
				temp2.put("status", "allowed");
				temp2.put("sid", null);
				temp2.put("tid", null);
				temp2.put("date", "1");
				temp2.put("pic", ((Map<String, String>) temp.get("details")).get("imgnow"));
				String jsons = gson.toJson(temp1);
				String jsons1 = gson.toJson(temp2);
				db.setScPeopleStay(jsons);
				db.addNewRecord(jsons1);
			} else if (temp.get("result").equals("notallowed")) {
				Map<String, Object> temp2 = new HashMap<String, Object>();
				temp2.put("name", ((Map<String, String>) temp.get("details")).get("name"));
				temp2.put("id", ((Map<String, String>) temp.get("details")).get("id"));
				temp2.put("identity", "student");
				temp2.put("status", "notallowed");
				temp2.put("sid", null);
				temp2.put("tid", null);
				temp2.put("date", "1");
				temp2.put("pic", ((Map<String, String>) temp.get("details")).get("imgnow"));
				String jsons = gson.toJson(temp2);
				db.addNewRecord(jsons);
			}
			return str;
		} else if (map.get("identity").equals("parent")) {
			String str = db.parentCome(map.get("identity"), map.get("id"), map.get("imgnow"));
			Map<String, Object> temp = gson.fromJson(str, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (temp.get("result").equals("allowed")) {
				Map<String, Object> temp1 = new HashMap<String, Object>();
				Map<String, Object> temp2 = new HashMap<String, Object>();
				temp1.put("id", ((Map<String, String>) temp.get("details")).get("id"));
				temp1.put("identity", "parent");
				temp2.put("name", ((Map<String, String>) temp.get("details")).get("name"));
				temp2.put("id", ((Map<String, String>) temp.get("details")).get("id"));
				temp2.put("identity", "parent");
				temp2.put("status", "allowed");
				temp2.put("sid", db.getSid(((Map<String, String>) temp.get("details")).get("stuName")));
				temp2.put("tid", db.getTid(((Map<String, String>) temp.get("details")).get("teacherName")));
				temp2.put("date", "1");
				temp2.put("pic", ((Map<String, String>) temp.get("details")).get("imgnow"));
				String jsons = gson.toJson(temp1);
				String jsons1 = gson.toJson(temp2);
				db.setScPeopleStay(jsons);
				db.addNewRecord(jsons1);
			} else if (temp.get("result").equals("notallowed")) {
				Map<String, Object> temp2 = new HashMap<String, Object>();
				temp2.put("name", ((Map<String, String>) temp.get("details")).get("name"));
				temp2.put("id", ((Map<String, String>) temp.get("details")).get("id"));
				temp2.put("identity", "parent");
				temp2.put("status", "notallowed");
				temp2.put("sid", db.getSid(((Map<String, String>) temp.get("details")).get("stuName")));
				temp2.put("tid", db.getTid(((Map<String, String>) temp.get("details")).get("teacherName")));
				temp2.put("date", "1");
				temp2.put("pic", ((Map<String, String>) temp.get("details")).get("imgnow"));
				String jsons1 = gson.toJson(temp2);
				db.addNewRecord(jsons1);
			}
			return str;
		} else if (map.get("identity").equals("blacklist")) {
			String str = db.refuseBlackList(map.get("identity"), map.get("id"), map.get("imgnow"));
			Map<String, Object> temp = gson.fromJson(str, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			Map<String, Object> temp2 = new HashMap<String, Object>();
			temp2.put("name", db.getBlacklistName(((Map<String, String>) temp.get("details")).get("id")));
			temp2.put("id", ((Map<String, String>) temp.get("details")).get("id"));
			temp2.put("identity", "blacklist");
			temp2.put("status", "notallowed");
			temp2.put("sid", null);
			temp2.put("tid", null);
			temp2.put("date", "1");
			temp2.put("pic", ((Map<String, String>) temp.get("details")).get("imgnow"));
			String jsons1 = gson.toJson(temp2);
			db.addNewRecord(jsons1);
			return str;
		} else {
			String str = db.workerCome(map.get("identity"), map.get("id"), map.get("imgnow"));
			Map<String, Object> temp = gson.fromJson(str, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			Map<String, Object> temp1 = new HashMap<String, Object>();
			Map<String, Object> temp2 = new HashMap<String, Object>();
			temp1.put("id", ((Map<String, String>) temp.get("details")).get("id"));
			temp1.put("identity", "worker");
			temp2.put("name", ((Map<String, String>) temp.get("details")).get("name"));
			temp2.put("id", ((Map<String, String>) temp.get("details")).get("id"));
			temp2.put("identity", "worker");
			temp2.put("status", "allowed");
			temp2.put("sid", null);
			temp2.put("tid", null);
			temp2.put("date", "1");
			temp2.put("pic", ((Map<String, String>) temp.get("details")).get("imgnow"));
			String jsons = gson.toJson(temp1);
			String jsons1 = gson.toJson(temp2);
			db.setScPeopleStay(jsons);
			db.addNewRecord(jsons1);
			return str;
		}
	}

	public static void readImage2DB() {// 往数据库里插入图片
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dbTools.getConn();
			String sql = "update schoolsys.student set spic=? where sid=201400300002";
			ps = conn.prepareStatement(sql);
			File file1 = new File("D:\\pic6.jpg");
			String s1 = EncodeModule.encodeImgageToBase64(file1);
			ps.setString(1, s1);
			int count = ps.executeUpdate();
			if (count > 0) {
				System.out.println("插入成功！");
			} else {
				System.out.println("插入失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbTools.closeConn(conn);
			if (null != ps) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean makeStudentSure(String  sid) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> list =new ArrayList<String>();
		try {
			String sql = "select sid from student";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("sid"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		if (list.contains(sid)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean makeParentSure(String  id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> list =new ArrayList<String>();
		try {
			String sql = "select id from parent";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("id"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbTools.closeConn(conn);
		if (list.contains(id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String addNewParent(String message) {//家长注册方法
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
			}.getType());
			String sql = "insert into schoolsys.parent (id,name,pic,sid,isordered,isschool) values(?,?,?,?,'N','N')";
			conn = dbTools.getConn();
			ps = conn.prepareStatement(sql);
			if (!map.get("name").equals(null)&&makeStudentSure( map.get("sid"))) {
				ps.setString(1, map.get("id"));
				ps.setString(2, map.get("name"));
				ps.setString(3, map.get("pic"));
				ps.setString(4, map.get("sid"));
				ps.executeUpdate();
				resultMap.put("message", "ok");
				json = gson.toJson(resultMap);
			} else {
				resultMap.put("message", "fail");
				json = gson.toJson(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}
	
	public String dParent(String message) {//拉黑操作
		Connection conn = null;
		PreparedStatement ps = null;
		Gson gson = new Gson();
		String json = "";
		ResultSet rs = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = gson.fromJson(message, new TypeToken<HashMap<String, String>>() {
			}.getType());
			Map<String, String> map2=new HashMap<>();
			String sql = "select * FROM `schoolsys`.`parent` WHERE `id`=?;";
			String sql2 = "DELETE FROM `schoolsys`.`parent` WHERE `id`=?;";
			String sql3 = "insert into schoolsys.blacklist (id,name,pic,contactid,note) values(?,?,?,?,?)";
			conn = dbTools.getConn();
			if (makeParentSure(map.get("id"))) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, map.get("id"));
				rs=ps.executeQuery();
				while (rs.next()) {
					map2.put("id", rs.getString("id"));
					map2.put("name", rs.getString("name"));
					map2.put("pic", rs.getString("pic"));
				}
				ps = conn.prepareStatement(sql3);
				ps.setString(1, map2.get("id"));
				ps.setString(2, map2.get("name"));
				ps.setString(3, map2.get("pic"));
				ps.setString(4, map.get("contactid"));
				ps.setString(5, map.get("note"));
				ps.executeUpdate();
				ps = conn.prepareStatement(sql2);
				ps.setString(1, map.get("id"));
				ps.executeUpdate();
				resultMap.put("message", "ok");
				json = gson.toJson(resultMap);
			}else {
				resultMap.put("message", "fail");
				json = gson.toJson(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			json = gson.toJson(resultMap);
		}
		dbTools.closeConn(conn);
		return json;
	}

//	 public static void main(String[] args) {
//	 Map<String, Object> map = new HashMap<String, Object>();
//	 Map<String, Object> map2 = new HashMap<String, Object>();
//	 map.put("id", "789");
//	 map.put("contactid", "001");
//	 map.put("note", "丑拒");
//	 map2.put("id", "789");
//	 map2.put("name", "王五爸爸");
//	 map2.put("pic", "saoudgauifgia");
//	 map2.put("sid", "201400300003");
//	 Gson gson = new Gson();
//	 String json = gson.toJson(map); 
//	 String json2 = gson.toJson(map2);
//	 dbTools db = new dbTools();
//	 System.out.println(db.getRecordDetailById(46));
//	 }
}
