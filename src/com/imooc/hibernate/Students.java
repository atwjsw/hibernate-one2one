package com.imooc.hibernate;

import java.sql.Blob;
import java.util.Date;

/*
 * 学生类
 */
public class Students {
	
	//1. 公有类
	//2. 提供公有的不带参数的默认的构造方法
	//3. 属性私有
	//4. 属性setter/getter封装
	
	private int sid; //学号
	private String sname; //姓名
	private String gender; //性别
	private Date birthday; //出生日期
	//private String address; //地址	
	private Blob picture;
	private Address address;
	
	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public Students () {}
	
	public Students(int sid, String sname, String gender, Date birthday, String address) {
		//super();
		this.sid = sid;
		this.sname = sname;
		this.gender = gender;
		this.birthday = birthday;		
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
