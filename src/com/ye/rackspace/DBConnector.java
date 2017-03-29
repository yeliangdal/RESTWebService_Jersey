package com.ye.rackspace;

import java.sql.*;
import java.util.ArrayList;
/*
 * 
 * The Database connector class creates a singleton connector object
 * It connects to the mySQL DB, execute sql queries, and catch the returned information
 * */
public class DBConnector {
	
	//create a singleton of the connector object 
	private static DBConnector instance;
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//returns the singleton object of DBconnector
	public static DBConnector getInstance(){
		if(instance==null){
			instance= new DBConnector();
		}
		
		return instance;
	}
	
	private DBConnector(){
		init();		
	}
	
	//initialize the connector object
	private void init(){
		//DB driver
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306";

		//  Database credentials
		final String USER = "test";
		final String PASS = "csci4145";
		
		try{
			Class.forName(JDBC_DRIVER);
			conn=DriverManager.getConnection(DB_URL, USER, PASS);
			stmt=conn.createStatement();
			stmt.executeQuery("use webService");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		
	}
		
	
	/*
	 * Insert a student record into table Students
	 * @param id:int the student id
	 * @return 0 if no tuple was inserted 
	 * 		   1 if a row was successfully inserted
	 * */
	public int createStudent(int id){
		String sql="INSERT INTO Students (studentID) VALUES (?)";
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return result;
	}
	
	/*
	 * Insert a student record into table Students with nickname
	 * @param id:int the student id
	 * @param name:String the nickname
	 * @return 0 if no tuple was inserted 
	 * 		   1 if a row was successfully inserted
	 * */
	public int createStudent(int id, String name){
		String sql="INSERT INTO Students VALUES (?, ?);";
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			result = pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return result;
	}
	/*
	 * Insert a photo record into table Photos
	 * @param id:int the student id
	 * @param photoID:String the publicID of the photo
	 * @return 0 if no tuple was inserted 
	 * 		   1 if a row was successfully inserted
	 * */
	
	public int insertPic(int id, String photoID){
		int result=0;
		String sql="INSERT INTO Photos VALUES (?,?)";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, photoID);
			result = pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return result;
	}
	
	/*
	 * Get all pictures of a student in the form of publicID
	 * @param id:int the student id
	 * @return picList:ArrayList<String> all photoIDs of the student
	 * */
	public ArrayList<String> picList(int id){
		ArrayList<String> picList= new ArrayList<String>();
		String sql="SELECT * FROM Photos WHERE studentID=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			//read from the resultset 
			while (rs.next()){
				String publicID=rs.getString("photoID");
				picList.add(publicID);
			}
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return picList;
	}
	/*
	 * List the content of the table Student
	 * @return students:ArrayList<String> 
	 * */
	public ArrayList<String> listStudents(){
		ArrayList<String> students= new ArrayList<String>();
		String sql="SELECT * FROM Students";
		try{
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//read from the resultset 
			while (rs.next()){
				String student=rs.getInt(1)+","+rs.getString(2);
				students.add(student);
			}
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return students;
	} 
	
	/*
	 * List the content of the table Photos
	 * @return photos:ArrayList<String> 
	 * */
	public ArrayList<String> listPhotos(){
		ArrayList<String> photos= new ArrayList<String>();
		String sql="SELECT * FROM Photos";
		try{
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//read from the resultset 
			while (rs.next()){
				String record=rs.getString(1)+","+rs.getString(2);
				photos.add(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return photos;
	} 
	
	/*
	 * Delete a photo record from table Photos
	 * @param id:int the student id
	 * @param photoID:String the publicID of the photo
	 * @return 0 if no tuple was deleted 
	 * 		   1 if a row was successfully deleted
	 * */
	
	public int deletePic(int id, String photoID){
		int result=0;
		String sql="DELETE FROM Photos WHERE studentID = ? AND photoID =?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, photoID);
			result = pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return result;
	}
	
	/*
	 * Delete all photos of a student from table Photos
	 * @param id:int the student id
	 * @return 0 if no tuple was deleted 
	 * 		   >0 number of rows were successfully deleted
	 * */
	
	public int deleteAllPic(int id){
		int result=0;
		String sql="DELETE FROM Photos WHERE studentID = ?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		return result;
	}
	
	
	/*
	 * Delete a student from table Student
	 * Also delete all photos associated with this student
	 * @param id:int the student id
	 * @return 0 if no tuple was deleted 
	 * 		   1 if the student is deleted
	 * */
	
	public int deleteStudent(int id){
		int result=0;
		String sql="DELETE FROM Students WHERE studentID = ?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();;
		}
		//delete all photos as well
		deleteAllPic(id);
		
		return result;
	}
}
