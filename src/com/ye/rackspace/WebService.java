package com.ye.rackspace;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ye
 * For CSCI4145 assignment 3
 * Create web services
 */

@Path("/")
public class WebService {


	
	private DBConnector dbConn = DBConnector.getInstance();	
	
	/* Provide web service to GET request
	 * Print all picIDs of a student
	 * @param String 
	 * @return List<Picture>
	 * */
	@GET
	@Path("/picid/{sid}")
	@Produces({MediaType.APPLICATION_JSON}) 
	//@Consumes(MediaType.APPLICATION_JSON)	 
	public List<Picture> getPictureIDs(@PathParam("sid") String msg) {
		boolean valid=true;
		int studentID=-1;;
		//JSONArray array=new JSONArray();
		List<Picture> pictures = new ArrayList<Picture>();
		
		String a[]=msg.split(",");
		//check the number of arguments 
		if (a.length!=1 || msg==null){
			valid=false;
		}
		//check the format of the arguments
		else if(isInteger(a[0])){
			studentID=Integer.parseInt(a[0]);
		}
		else{
			valid=false;
		}
		
		if(!valid){
			//return Response.serverError().build();	
		}
		else{
			
			ArrayList<String> pics = dbConn.picList(studentID);
			
			for(int i=0;i<pics.size();i++){
				Picture pic = new Picture(studentID, pics.get(i));
				pictures.add(pic);
				
			}
			//return Response.ok(array.toString()).build();
		}
		
		return pictures;
	}

	
	/* Provide web service to GET request
	 * Display all pictures of a student
	 * @param String 
	 * @return Response object
	 * */
	@GET
	@Path("/pic/{param}")
	public Response getPictures(@PathParam("param") String msg) {
	 
		boolean valid=true;
		int studentID=-1;;
		String ret="<html><body>";
		
		String a[]=msg.split(",");
		//check the number of arguments 
		if (a.length!=1 || msg==null){
			valid=false;
		}
		//check the format of the arguments
		else if(isInteger(a[0])){
			studentID=Integer.parseInt(a[0]);
		}
		else{
			valid=false;
		}
		
		if(!valid){
			ret= "Wrong input!  ";
			ret+= "Please enter a 3-digit student id ";			
		}
		else{
			ArrayList<String> pics = dbConn.picList(studentID);
			ret+="<p1>The pictures of user "+studentID+"</p1><p2>";
			for(int i=0;i<pics.size();i++){
				ret+="<img src=\"http://res.cloudinary.com/yliang/image/upload/"+pics.get(i)+"\""
						+ "width=\"400\" height=\"400\"/>";
			}
			ret+="</p2></body></html>";
		}
		
		return Response.status(200).entity(ret).build();
	}
	
	
	/* Provide web service to GET request
	 * Print all entries in table students
	 * @return JSONArray object
	 * */
	@GET
	@Path("/student")  
	@Produces({MediaType.APPLICATION_JSON}) 
	public List<Student> getStudents() {
	 
		ArrayList<String> students= dbConn.listStudents();
		
		String ret = "";
		List<Student> studentList = new ArrayList();
		for (int i=0;i<students.size();i++){
			String a[] = students.get(i).split(",");
			Student student = new Student();
			student.setStudentNum(Integer.parseInt(a[0]));
			student.setNickname(a[1]);
			studentList.add(student);
		}
		return studentList;
		//return Response.status(200).entity(ret).build();
	}
	
	/* Provide web service to GET request
	 * Print all entries in database table photos 
	 * @return JSONArray object
	 * */
	@GET
	@Path("/pic")  
	@Produces({MediaType.APPLICATION_JSON}) 
	public List<Picture> getPictures() {
	 
		ArrayList<String> pictures= dbConn.listPhotos();
		
		ArrayList<Picture> pictureList= new ArrayList<Picture>();
		
		for (int i=0;i<pictures.size();i++){
			String a[] = pictures.get(i).split(",");
			Picture pic = new Picture();
			pic.setPicID(a[1]);
			pic.setStudentID(Integer.parseInt(a[0]));
			pictureList.add(pic);
		}
		return pictureList;
		//return Response.status(200).entity(ret).build();
	}
	

	/* Provide web service to POST request
	 * Create a student record
	 * @param InputStream 
	 * @return JSON object
	 * */
	@POST
	@Path("/student/")
	@Produces({MediaType.APPLICATION_JSON}) 
	@Consumes({MediaType.APPLICATION_JSON})	
	public Student createStudent(InputStream incomingData){
		
		
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + result.toString());
		
		JSONObject jsonobj= new JSONObject();
		
		try {
			jsonobj = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String studentNum="";
		String nickName="";
		try {
			studentNum= jsonobj.getString("studentNum");
			nickName =jsonobj.getString("nickName");
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbConn.createStudent(Integer.parseInt(studentNum), nickName);
		Student student = new Student(Integer.parseInt(studentNum), nickName);
		
		return student;
	}
	
	
	/* Provide web service to POST request
	 * Create a picture record for a given student, upload to the cloudinary cloud via a url
	 * @param InputStream 
	 * @return JSON object
	 * */
	@POST
	@Path("/pic/url/")
	@Produces({MediaType.APPLICATION_JSON}) 
	@Consumes({MediaType.APPLICATION_JSON})	
	public Picture createPicUrl(InputStream incomingData){
		
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + result.toString());
		
		JSONObject jsonobj= new JSONObject();
		
		try {
			jsonobj = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String studentNum="";
		String picurl="";
		String pid="";
		try {
			studentNum= jsonobj.getString("studentNum");
			picurl =jsonobj.getString("picurl");
			pid=jsonobj.getString("pid");
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//insert pic into table
		dbConn.insertPic(Integer.parseInt(studentNum), pid);
		
		//upload pic to cloudinary
		CloudinaryUpload.UrlImageUpload(picurl, pid);
		Picture picture = new Picture(Integer.parseInt(studentNum), pid);
		
		return picture;
	}
	
	
	/* Provide web service to POST request
	 * Create a picture record for a given student, 
	 * upload a local file to the cloudinary cloud, handled by local client 
	 * @param InputStream 
	 * @return JSON object
	 * */
	@POST
	@Path("/pic/local/")
	@Produces({MediaType.APPLICATION_JSON}) 
	@Consumes({MediaType.APPLICATION_JSON})	
	public Picture createPicLocal(InputStream incomingData){
		
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + result.toString());
		
		JSONObject jsonobj= new JSONObject();
		
		try {
			jsonobj = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String studentNum="";
		String path="";
		String pid="";
		try {
			studentNum= jsonobj.getString("studentNum");
			path =jsonobj.getString("path");
			pid=jsonobj.getString("pid");
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//insert pic into table
		dbConn.insertPic(Integer.parseInt(studentNum), pid);
		
		Picture picture = new Picture(Integer.parseInt(studentNum), pid);
		
		return picture;
	}
	
	/*	
	 * Provide web service to POST
	 * Delete a picture record from a student
	 * @param InputStream 
	 * @return JSON object
	*/
	@POST
	@Path("/pic/delete")
	@Produces({MediaType.APPLICATION_JSON}) 
	@Consumes({MediaType.APPLICATION_JSON})	
	public Picture delPic(InputStream incomingData){
		
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + result.toString());
		
		JSONObject jsonobj= new JSONObject();
		
		try {
			jsonobj = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String studentNum="";
		String picID="";
		try {
			studentNum= jsonobj.getString("studentNum");
			picID=jsonobj.getString("picID");
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbConn.deletePic(Integer.parseInt(studentNum), picID);
		
		CloudinaryDelete.ImageDelete(picID);
		
		
		
		return (new Picture(Integer.parseInt(studentNum), picID));
	}
	
	/*	
	 * Provide web service to POST
	 * Delete all picture records from a student
	 * @param InputStream 
	 * @return JSON object
	*/
	
	@POST
	@Path("/pic/deleteall")
	@Produces({MediaType.APPLICATION_JSON}) 
	@Consumes({MediaType.APPLICATION_JSON})	
	public List<Picture> delAllPic(InputStream incomingData){
		
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + result.toString());
		
		JSONObject jsonobj= new JSONObject();
		
		try {
			jsonobj = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String studentNum="";
		try {
			studentNum= jsonobj.getString("studentNum");
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//delete pic from cloud and catch the deleted pic as a JSON list
		ArrayList<String> picIDs = new ArrayList<String>();
		
		ArrayList<Picture> pics = new ArrayList<Picture>();
		
		picIDs = dbConn.picList(Integer.parseInt(studentNum));
		
		for(int i=0;i<picIDs.size();i++){
			Picture pic = new Picture(Integer.parseInt(studentNum),picIDs.get(i));
			pics.add(pic);
			CloudinaryDelete.ImageDelete(picIDs.get(i));
		}
		
		//delete pic from DB
		dbConn.deleteAllPic(Integer.parseInt(studentNum));
		return pics;
	}
	

	/*	
	 * Provide web service to POST
	 * Delete a student
	 * @param InputStream 
	 * @return JSON object
	*/
	
	@POST
	@Path("/student/delete")
	@Produces({MediaType.APPLICATION_JSON}) 
	@Consumes({MediaType.APPLICATION_JSON})	
	public Student delStudent(InputStream incomingData){
		
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + result.toString());
		
		JSONObject jsonobj= new JSONObject();
		
		try {
			jsonobj = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String studentNum="";
		try {
			studentNum= jsonobj.getString("studentNum");
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbConn.deleteStudent(Integer.parseInt(studentNum));
		
		return (new Student(Integer.parseInt(studentNum)));
		
		
	}
	
	
	
/*	 * A helper method returns true if a given string is an integer
	 * 
	 * */
	
	private static boolean isInteger(String num){
	     try{
	        int x = Integer.parseInt(num);
	        return true;
	     }
	     catch(NumberFormatException e){
	        return false;
	     }
	  }
}
