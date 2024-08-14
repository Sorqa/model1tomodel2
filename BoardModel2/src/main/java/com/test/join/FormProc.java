package com.test.join;

import java.sql.Date;

import jakarta.servlet.http.HttpServletRequest;

public class FormProc {
	
	public boolean saveForm(HttpServletRequest request) {
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
		
		//MemberVO,UserDAO,member 테이블에 저장
		//hobby 테이블 별도 생성
		return saved;
	}
}
