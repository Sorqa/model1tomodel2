package com.test.model1board;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.test.join.MemberVO;
import com.test.join.User;
import com.test.join.UserDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BoardService {

	private HttpServletRequest request;
	private HttpServletResponse response;
	String[] path;	//url경로
	public BoardService() {}
	
	public BoardService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		String uri = request.getRequestURI();
		String[] token = uri.split("/");
		int len = token.length;
		path = Arrays.copyOfRange(token,3,len);

	}
	

	public String process() 
	{
		
		String cmd = path[0];
		
		String viewPath = null;
		if(cmd==null) {
	         return "/board/index.jsp";
	      }
		if(cmd.equals("home")) {
			viewPath ="/board/index.jsp";
		}
		if(cmd.equals("loginForm")) {
	    	  viewPath="/join/loginForm.jsp";
	     }else if(cmd.equals("login")) {
	    	  User user = new User();
	    	  user.setUid(request.getParameter("uid"));
	          user.setPwd(request.getParameter("pwd"));
	    	  UserDAO dao = new UserDAO();
	    	  
	    	  boolean ok = dao.login(user);
	    	  if(ok) {
	    	  request.getSession().setAttribute("uid",user.getUid());
	    	  }
	    	  sendJSON("ok",ok+"");
	      }else if(cmd.equals("logout")) {	    //에러남	 
	    	  request.getSession().invalidate();
	    	  sendJSON("logout",true+"");
	    	  return null;
	      }else if(cmd.equals("boardAddForm")) {
	    	  viewPath="/board/boardAddForm.jsp";
	      }else if(cmd.equals("add")) {
	    	  Board board = new Board();
	    	  board.setTitle(request.getParameter("title"));
	    	  board.setContents(request.getParameter("contents"));	    	  
	    	  
	    	  board.setAuthor((String)request.getSession().getAttribute("uid"));
	    	  java.util.Date uDate = new java.util.Date();
	    	  java.sql.Date sDate = new java.sql.Date(uDate.getTime());
	    	  board.setRdate(sDate);
	    	  
	    	  BoardDAO dao = new BoardDAO();
	    	  
	    	  int bnum = dao.addBoard(board);
	    	  boolean saved = bnum>0;
	    	  Map<String,Object> map = new HashMap<>();
	    	  map.put("saved",saved);
	    	  map.put("bnum",bnum);
	    	  sendJSON(map);
	          return null;	    	  
	      }else if(cmd.equals("boardList")) {
	    	  BoardDAO dao = new BoardDAO();
	    	  
	    	
	    	List<Board> list = dao.getList();
	    	
	    	request.setAttribute("boards", list);
	    	  viewPath="/board/boardList.jsp";
	      }else if(cmd.equals("boardDetail")) {	//cont/detail/3
	    	  
	    	  int no = Integer.parseInt(path[1]);
	    	  BoardDAO dao = new BoardDAO();
	    	  
	    	  Board board = dao.getBoard(no);
	    	 
	    	  request.setAttribute("board", board); //영역에 들어갔다
	    	  
	    	  viewPath="/board/boardDetail.jsp";
	      }else if(cmd.equals("updateForm")) {
	    	  int no = Integer.parseInt(path[1]);
	    	  BoardDAO dao = new BoardDAO();
	    	  
	    	  Board board = dao.getBoard(no);
	    	 
	    	  request.setAttribute("board", board);
	    	  viewPath="/board/updateForm.jsp";
	      }else if(cmd.equals("delete")) {
	    	  Board board = new Board();
	    	  board.setBnum(Integer.parseInt(path[1]));
	    	  
	    	  BoardDAO dao = new BoardDAO();
	    	 	    	  	
	    	  boolean deleted = dao.delete(board);
	    	  sendJSON("deleted",deleted+"");	
	    	  return null;		    	  
	      }else if(cmd.equals("boardUpdate")) {
	    	  Board update = new Board();
	    	  update.setBnum(Integer.parseInt(request.getParameter("bnum")));
	    	  update.setTitle(request.getParameter("title"));
	    	  update.setContents(request.getParameter("contents"));
	    	  
	    	  BoardDAO dao = new BoardDAO();
	    	  	 
	    	  boolean updated = dao.updateBoard(update);
	    	  
	    	  sendJSON("updated",updated+"");
	    	  return null;	    	  	
	    	  
	      }else if(cmd.equals("joinForm")) {
	    	  viewPath="/join/joinForm.jsp";
	      }else if(cmd.equals("join")) {
	    	  String[] hobbies = request.getParameterValues("hobby");
	  		String uid = request.getParameter("uid");
	  		String pwd = request.getParameter("pwd");
	  		String gender = request.getParameter("gender");
	  		int history = Integer.parseInt(request.getParameter("history"));
	  		int age = Integer.parseInt(request.getParameter("age"));		
	  	     java.sql.Date birth = Date.valueOf(request.getParameter("birth"));
	  	     
	  		String intro =  request.getParameter("intro");
	  		//String uid, String pwd, String gender, String[] hobbies, int history, int age, Date birth,String intro
	  		MemberVO m = new MemberVO(uid,pwd,gender,hobbies,history,age,birth,intro);
	  		UserDAO dao = new UserDAO();
	  		boolean saved = dao.saveMember2(m);
	  		sendJSON("saved",saved+"");
	    	  
	  		return null;
	      }
		
		return viewPath;
	}
	
	private void sendJSON(String key, String value) {
		String json = String.format("{\"%s\":%s}", key, value);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {			
			e.printStackTrace();
		}
 		out.print(json);
 		out.flush(); 
	}
	 private void sendJSON(Map<String,Object> map) {
	      JSONObject jsObj = new JSONObject(map);
	      String js = jsObj.toJSONString();
	      try {
	         PrintWriter out = response.getWriter();
	         out.print(js);
	         out.flush();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	
}
