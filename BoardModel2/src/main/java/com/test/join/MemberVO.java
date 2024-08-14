package com.test.join;

import java.sql.Date;

public class MemberVO {
	
	private String uid;
	private String pwd;
	private String gender;
	private String[] hobbies;
	
	private int history;
	private int age;
	private java.sql.Date birth;
	private String intro;
	
	
	public MemberVO(String uid, String pwd, String gender, String[] hobbies, int history, int age, Date birth,
			String intro) {
		super();
		this.uid = uid;
		this.pwd = pwd;
		this.gender = gender;
		this.hobbies = hobbies;
		this.history = history;
		this.age = age;
		this.birth = birth;
		this.intro = intro;
	}
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String[] getHobbies() {
		return hobbies;
	}
	public void setHobbies(String[] hobbies) {
		this.hobbies = hobbies;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public java.sql.Date getBirth() {
		return birth;
	}
	public void setBirth(java.sql.Date birth) {
		this.birth = birth;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}

}
