package com.test.join;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO 
{
   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
   private Connection getConn() 
   {
      try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection(
                   "jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "TIGER");
         return conn;
      }catch(Exception e) {
         e.printStackTrace();
      }
      return null;
      
   }
   
   public boolean checkDuplicate(String uid) {
	   conn=getConn();
	   String sql= "SELECT * FROM users WHERE userid=?";
	   try {
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setString(1, uid);
		   rs = pstmt.executeQuery();
		   if(rs.next()) {return false;}
		   else {return true;}
				   
	   }catch(SQLException sqle) {
		   sqle.printStackTrace();
	   }
	   return false;
   }
   
   public boolean login(User user) {
		String sql = "SELECT * FROM users WHERE userid=? AND userpwd=?";
		conn = getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUid());
			pstmt.setString(2, user.getPwd());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			closeAll();
		}
		return false;
	}
   
   public List<User> getList()
   {
	   String sql = "SELECT * FROM users";
	   conn = getConn();
	   try {
		   pstmt = conn.prepareStatement(sql);
		   rs = pstmt.executeQuery();
		   List<User> list = new ArrayList<>();
		   while(rs.next()) {
			   String useruid = rs.getString("USERID");
			   String userpwd = rs.getString("USERPWD"); 
			   
			   list.add(new User(useruid,userpwd));
		   }
		   return list;
	   }catch(SQLException sqle) {
		   sqle.printStackTrace();
	   }
	   return null;
   }
   
   public User getUser(String uid) {	//public List<Map<String,String>> getUser(String uid) {
		String sql = "SELECT * FROM users WHERE userid = ?";
		conn = getConn();
		User user = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			
			
	         while(rs.next()) {
	            String useruid = rs.getString("USERID");
	            String userpwd = rs.getString("USERPWD"); 
	            
	           user = new User();
	           user.setUid(useruid);
	           user.setPwd(userpwd);
	            
	         }
	         
	         return user;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return null;
	}
   
   public boolean update(User user) {
	   
		String sql = "UPDATE users SET userpwd=? WHERE userid=?";
		conn = getConn();
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPwd());
			pstmt.setString(2, user.getUid());
			System.out.printf("업데이트1");
			int rows = pstmt.executeUpdate();	//안되면 oracle문제
			System.out.printf("업데이트2");
			return rows>0;	//0보다 같거나 작으면 바뀐게 없다
		
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			closeAll();
		}
		return false;
   }
   
   public boolean delete(String uid) {
	   String sql = "DELETE FROM users WHERE userid=?";
		conn = getConn();
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
						
			int rows = pstmt.executeUpdate();	//안되면 oracle문제			
			return rows>0;	//0보다 같거나 작으면 바뀐게 없다
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			closeAll();
		}
		return false;
		
	}
   
   
   public boolean add(User user) {
		String sql = "INSERT INTO users(userid,userpwd) VALUES (?,?)";
		conn = getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,user.getUid());
			pstmt.setString(2,user.getPwd());
			
			int rows = pstmt.executeUpdate();
			return rows>0;
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			closeAll();
		}
		return false;
	}
   
   
   
   private void closeAll() {
      try {
         if(rs!=null) rs.close();
         if(pstmt!=null) pstmt.close();
         if(conn!=null) conn.close();
      }catch(Exception e) {
         e.printStackTrace();
      }
   }

   public boolean saveMember(MemberVO m) {
	   
	   String sql = "INSERT INTO member(memid,pwd,gender,intro,birth,age) VALUES (?,?,?,?,?,?,?)";
	   conn = getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getUid());
			pstmt.setString(2,m.getPwd());
			pstmt.setString(3,m.getGender());
			
			pstmt.setString(4,m.getIntro());
			pstmt.setDate(5,m.getBirth());
			pstmt.setInt(6, m.getAge());
			
			int mem_rows = pstmt.executeUpdate();
			
			String[] hobbies = m.getHobbies();
			sql = "INSERT INTO hobby(memid, hcode) VALUES"
				+ "("
				+ "		?, "
				+ "		(SELECT code FROM hobbycode WHERE hobby=?)"
				+ ")";
			pstmt = conn.prepareStatement(sql);
			int hobby_rows = 0;
			for(int i=0;i<hobbies.length;i++) {
				pstmt.setString(1,m.getUid());
				pstmt.setString(2,hobbies[i]);
				hobby_rows += pstmt.executeUpdate();
			}
			return hobbies.length + 1 == mem_rows + hobby_rows;	//+1은 member 입력
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			closeAll();
		}
	   
	return false;
	
	
   }
   
   public boolean saveMember2(MemberVO m) {
	   String sql = "INSERT INTO member(memid,pwd,gender,history,intro,birth,age) VALUES (?,?,?,?,?,?,?)";
	   conn = getConn();
	   try {
		   conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getUid());
			pstmt.setString(2,m.getPwd());
			pstmt.setString(3,m.getGender());
			pstmt.setInt(4,m.getHistory());
			pstmt.setString(5,m.getIntro());
			pstmt.setDate(6,m.getBirth());
			pstmt.setInt(7, m.getAge());
			
			int mem_rows = pstmt.executeUpdate();
			
			String[] hobbies = m.getHobbies();
			sql = "INSERT INTO hobby(memid, hcode) VALUES"
				+ "("
				+ "		?, "
				+ "		(SELECT code FROM hobbycode WHERE hobby=?)"
				+ ")";
			pstmt = conn.prepareStatement(sql);
			int hobby_rows = 0;
			for(int i=0;i<hobbies.length;i++) {
				pstmt.setString(1,m.getUid());
				pstmt.setString(2,hobbies[i]);
				hobby_rows += pstmt.executeUpdate();
			}
			boolean saved = hobbies.length + 1 == mem_rows + hobby_rows;	//+1은 member 입력
			if(saved) {
				conn.commit();
			}
			return saved;
		}catch(SQLException sqle) {
			try {
				conn.rollback();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			sqle.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
			}catch(SQLException e) {
				e.printStackTrace();
			}
			closeAll();
		}
	   
	return false;
	
   }


}
