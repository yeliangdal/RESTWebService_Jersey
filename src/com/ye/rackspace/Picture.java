package com.ye.rackspace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Picture {
	//private int id;
	private int studentID;
	private String picID;
	
	public Picture(){}
	
	public Picture(int studentID, String picID){
		//this.id = id;
		this.studentID=studentID;
		this.picID=picID;
	}

	@Override
	public String toString() {
		return "Picture [studentID=" + studentID + ", picID=" + picID + "]";
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getPicID() {
		return picID;
	}

	public void setPicID(String picID) {
		this.picID = picID;
	}
	


	
}
