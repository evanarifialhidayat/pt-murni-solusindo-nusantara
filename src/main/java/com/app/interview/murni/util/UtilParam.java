package com.app.interview.murni.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.model.res.ModelJson;
public class UtilParam {
	
	
    public static String randomCaracterString(int p)
    {      
            Random r = new Random();
            String obj = "";
            String param = "123456789ABCDEVGHIJKLMNOPQRSTUFWXYZabcdefghijklmnopqrstuvwxyz";
               for (int i = 0; i < p; i++) {
                   obj += param.charAt(r.nextInt(param.length()));
               } 		
               return obj;
    }    
    public static ModelJson mappingdata(String param,UserLogin userLogin){
    	Map<String, String> map = new HashMap<>();
    	ModelJson result = new ModelJson();
    	if(param.equals(ParamPath.VALIDATION_SUKSES)) {
    		map.put("status", "SUCCESS");  
        	result.setResult(true);
	    	result.setErrors("SUCCESS");
	    	result.setList(userLogin);    	
    	}else if(param.equals(ParamPath.VALIDATION_SUKSES_DELETE)) {
    		map.put("status", "SUCCESS");  
        	result.setResult(true);
	    	result.setErrors("SUCCESS");
	    	result.setList(map);    	
    	}else if(param.equals(ParamPath.VALIDATION_GAGAL_REPO)) {
    		map.put("status", "ERORR");  
			result.setResult(false);
	    	result.setErrors("FAILED DATA");
	    	result.setList(map);    	
    	}else if(param.equals(ParamPath.VALIDATION_TRY)) {
    		map.put("status", "ERORR");  
			result.setResult(false);
	    	result.setErrors("EXCEPTION ON TRY CATCH");
	    	result.setList(map);    
    	}else if(param.equals(ParamPath.VALIDATION_DUPLICATE)) {
    		map.put("status", "ERORR");  
			result.setResult(false);
	    	result.setErrors("DUPLICATE DATA");
	    	result.setList(map);    
    	}else{
    		map.put("status", "ERORR");  
    		result.setResult(false);
    	    result.setErrors("ERORR VALIDATION");
    	    result.setList(map);
    	}
    	return result;    	
    }
    public static String getSecurePassword(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = salt+sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    public static String getRandomIdSalt(int p) {
		 Random r = new Random();
		 String roomId = "";
		 String url = "123456789ABCDEVGHIJKLMNOPQRSTUFWXYZabcdefghijklmnopqrstuvwxyz";
		    for (int i = 0; i < p; i++) {
		    	roomId += url.charAt(r.nextInt(url.length()));
		    } 		
		    return roomId;
	}
    
    public static String printStack(Exception e) {
    	StringWriter errors = new StringWriter();
    	e.printStackTrace(new PrintWriter(errors));
    	
    	return errors.toString();
    }
    public static Calendar convStringDate(String str){
    	Date date = null;
    	Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(str);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
    }
    
    
    
}
