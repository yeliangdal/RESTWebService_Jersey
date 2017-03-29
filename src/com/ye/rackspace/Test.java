package com.ye.rackspace;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DBConnector conn = DBConnector.getInstance();
		for(int i=0; i<conn.listPhotos().size();i++){
			System.out.println(conn.listPhotos().get(i));
		}
		
		ArrayList<String> pics = conn.picList(9);
		
		for(int i=0; i<conn.listPhotos().size();i++){
			System.out.println(pics.get(i));
		}
/*		conn.createStudent(12, "Jim");
		conn.insertPic(12, "p101");
		conn.insertPic(12, "p102");
		conn.insertPic(12, "p103");*/
	}

}
