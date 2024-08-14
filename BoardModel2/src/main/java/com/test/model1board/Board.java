package com.test.model1board;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Board {
	private int bnum;
	private String title;
	private String author;
	private String contents;
	private java.sql.Date rdate;
	private int hit;
	
	public Board() {}
	
	public Board(int bnum, String title, String author, String contents, Date rdate, int hit) {
		super();
		this.bnum = bnum;
		this.title = title;
		this.author = author;
		this.contents = contents;
		this.rdate = rdate;
		this.hit = hit;
	}

	public Board(int bnum, String title, String author, Date rdate, int hit) {
		super();
		this.bnum = bnum;
		this.title = title;
		this.author = author;
		this.rdate = rdate;
		this.hit = hit;
	}

	

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 원하는 형식으로 날짜를 포맷
        String formattedDate = sdf.format(rdate);  // rdate를 포맷하여 문자열로 변환

        String s = String.format("%s\t%s\t%s\t%d",title, author, formattedDate, hit);
        return s;
		
	}

	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public java.sql.Date getRdate() {
		return rdate;
	}
	public void setRdate(java.sql.Date rdate) {
		this.rdate = rdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
}
