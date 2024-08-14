package com.test.model1board;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.test.join.User;


public class BoardDAO {
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
	   
	public int addBoard(Board b) {
		String sql = "INSERT INTO boards(bnum, title, author, contents, rdate, hit) "
		           + "VALUES(boards_seq.NEXTVAL, ?, ?, ?, ?, 0) "
		           + "RETURNING bnum INTO ?";

		conn = getConn();

		try {
		    //
		    CallableStatement cstmt = conn.prepareCall("{call " + sql + "}");

		    // Set the input parameters
		    cstmt.setString(1, b.getTitle());
		    cstmt.setString(2, b.getAuthor());
		    cstmt.setString(3, b.getContents());
		    cstmt.setDate(4, b.getRdate());

		    // Register the OUT parameter for RETURNING
		    cstmt.registerOutParameter(5, Types.INTEGER); // Register the bnum as OUT parameter

		    // Execute the SQL statement
		    int rows = cstmt.executeUpdate();

		    // Retrieve the OUT parameter value
		    int bnum = cstmt.getInt(5);
		    
		    System.out.println("추출된 시퀀스 값:" + bnum);
		    return bnum;

		} catch(SQLException sqle) {
		    sqle.printStackTrace();
		} 

		return 0;

	}
	
	public Board getBoard(int bnum) {
		conn=getConn();
		String sql ="SELECT * FROM boards WHERE bnum=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bnum);
			rs = pstmt.executeQuery();
			if(rs.next()) {			
				int bnums = rs.getInt("BNUM");
				String title = rs.getString("TITLE");
				String author = rs.getString("AUTHOR");
				String contents = rs.getString("CONTENTS");
				java.sql.Date rdate = rs.getDate("RDATE");
				int hit = rs.getInt("HIT");
				
					
				Board b = new Board(bnums,title,author,contents,rdate,hit);
				return b;
			}	 
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally {
			closeAll();
		}
		return null;		
	}
	
	public List<Board> getList(){
		conn=getConn();
		String sql ="SELECT * FROM boards ORDER BY bnum";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Board> list = new ArrayList<>();
			while(rs.next()) {
				   int bnum = rs.getInt("BNUM");
				   String title = rs.getString("TITLE"); 
				   String author = rs.getString("AUTHOR");
				   java.sql.Date rdate = rs.getDate("RDATE");
				   int hit = rs.getInt("HIT");
				   
				   list.add(new Board(bnum,title,author,rdate,hit));
			   }
			   return list;
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			closeAll();
		}
		return null;
		
	}
	
	public boolean updateBoard(Board b) {
		conn=getConn();
		String sql= "UPDATE boards SET title=?,contents=? WHERE bnum=? ";
					
		try {
			 pstmt = conn.prepareStatement(sql);			    
			  pstmt.setString(1, b.getTitle());
			  pstmt.setString(2, b.getContents());
			  pstmt.setInt(3, b.getBnum());
			  			    			    
			  int rows = pstmt.executeUpdate();
		 			    		
			  return rows>0;

		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
		
	}
	
	public boolean delete(Board b) {
		   String sql = "DELETE FROM boards WHERE bnum=?";
			conn = getConn();
			try {				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, b.getBnum());
							
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
	
	 private void closeAll() {
	      try {
	         if(rs!=null) rs.close();
	         if(pstmt!=null) pstmt.close();
	         if(conn!=null) conn.close();
	      }catch(Exception e) {
	         e.printStackTrace();
	      }
	   }
}
