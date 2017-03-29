package com.ye.rackspace;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryDelete {

	public static void ImageDelete(String publicID){
		//create configuration file for cloudinary connection
		Map config = new HashMap();
		config.put("cloud_name", "yliang");
		config.put("api_key", "827321347673686");
		config.put("api_secret", "pg_ZDtufW6WAQpamNsdO2znSh-8");
		
		Cloudinary cloudinary = new Cloudinary(config);
		
		
		try{
			Map deleteResult= cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
			System.out.println(deleteResult);
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
