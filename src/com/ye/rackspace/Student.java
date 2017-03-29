package com.ye.rackspace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	private int studentNum;
	private String nickname;
	
	public Student(){}
	
	public Student(int studentNum, String nickName){
		this.studentNum=studentNum;
		this.nickname=nickName;
	}
	
	public Student(int studentNum){
		this.studentNum=studentNum;
		this.nickname="";
	}

	@Override
	public String toString() {
		return "Student [studentNum=" + studentNum + ", nickname=" + nickname + "]";
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
