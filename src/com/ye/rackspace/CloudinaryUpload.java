package com.ye.rackspace;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


public class CloudinaryUpload {

	/*
	 * @author Ye
	 * 
	 * */
	public static void LocalImageUpload(String fileName, String publicID) {
		// TODO Auto-generated method stub
		File file = new File(fileName);
		
		//create configuration file for cloudinary connection
		Map config = new HashMap();
		config.put("cloud_name", "yliang");
		config.put("api_key", "827321347673686");
		config.put("api_secret", "pg_ZDtufW6WAQpamNsdO2znSh-8");
		
		Cloudinary cloudinary = new Cloudinary(config);
		
		
		try{
			//give the image a custom name
			Map params = ObjectUtils.asMap("public_id", publicID);
			//upload the image to cloudinary
			Map uploadResult = cloudinary.uploader().upload(file, params);
			System.out.println("Upload Successful! The public ID of the image is "+ uploadResult.get("public_id"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	public static void UrlImageUpload(String url, String publicID) {
		
		//create configuration file for cloudinary connection
		
		Map config = ObjectUtils.asMap(
				  "cloud_name", "yliang",
				  "api_key", "827321347673686",
				  "api_secret", "pg_ZDtufW6WAQpamNsdO2znSh-8");
		Cloudinary cloudinary = new Cloudinary(config);
		
		try{
			//give the image a custom name
			Map params = ObjectUtils.asMap("public_id", publicID);
			//upload the image to cloudinary
			Map uploadResult = cloudinary.uploader().upload(url, params);
			System.out.println("Upload Successful! The public ID of the image is "+ uploadResult.get("public_id"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] agrs){
		LocalImageUpload("balloon.png", "image_010");
		UrlImageUpload("http://pngimg.com/upload/balloon_PNG4968.png","image_011");
	}
}