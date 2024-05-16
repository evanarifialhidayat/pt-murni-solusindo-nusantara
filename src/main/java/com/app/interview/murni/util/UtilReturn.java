package com.app.interview.murni.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.model.res.ModelJson;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.services.token.JwtTokenUtil;
import com.app.interview.murni.services.token.JwtUserDetailsService;

@Service
@Transactional
public class UtilReturn {
	@Autowired  static UserLoginRepo userLoginRepo;
	@Autowired  static UserLoginService userLoginService;	
	@Autowired  static AuthenticationManager authenticationManager;
	@Autowired  static JwtUserDetailsService userDetailsService;
	@Autowired  static JwtTokenUtil jwtTokenUtil;

	static List datajson = null;
	
	public UtilReturn(UserLoginRepo userLoginRepo,
					  UserLoginService userLoginService, 
					  List datajson,
					  AuthenticationManager authenticationManager,
					  JwtUserDetailsService userDetailsService,
					  JwtTokenUtil jwtTokenUtil
					  ) {
		super();
		this.userLoginRepo = userLoginRepo;
		this.userLoginService = userLoginService;
		this.datajson = datajson;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}




	public static  ResponseEntity<?> returncontroller(String logik,UserLogin bodyparam,Integer id){
    	if(logik.equals(ParamPath.RETURN_LOGIN)) {
    		UserLogin get = userLoginRepo.findByEmail(bodyparam.getEmail());
    		if(get != null){
    			String idsalt = get.getPassword().substring(0, 9);
    			String encript1 = UtilParam.getSecurePassword(bodyparam.getPassword(), idsalt);
           	    bodyparam.setPassword(encript1);
    		}
    		Map<String, String> param = new HashMap<>();
    	    ModelJson result = new ModelJson();
    	 	try {    		
    	 		    UserLogin verifikasi = userLoginRepo.findByEmailAndPassword(bodyparam.getEmail(), bodyparam.getPassword());
    	 	 		if(verifikasi != null) {
    	 	 			UserDetails userDetails = userDetailsService.createUserDetails(bodyparam.getEmail(), bodyparam.getPassword());
    	 	            String token = jwtTokenUtil.generateToken(userDetails);
    	 	            verifikasi.setToken(token);
    	 	            UserLogin userLogin = userLoginService.saveData(verifikasi);
     	 				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES, verifikasi));
    	 		 	}else {
    	 		    	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
    	 		 	}            
    		} catch (Exception e) {
    			    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
    		}  
    	}else if(logik.equals(ParamPath.RETURN_REGISTER)) {
    		 String encript1 = UtilParam.getSecurePassword(bodyparam.getPassword(), UtilParam.getRandomIdSalt(9));
        	 bodyparam.setPassword(encript1);
        	 Map<String, String> param = new HashMap<>();
    	     ModelJson result = new ModelJson();
    	 	 try {    		
    	 		 		UserLogin userLogin = userLoginService.saveData(bodyparam);
    	 		 		if(userLogin != null) {    	 		 			    
    	 		 				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES, userLogin));
    	 		 		}else {
    	 		 				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
    	 		 		}            
    			} catch (Exception e) {
    				e.printStackTrace();
    						    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
    			}   
    		
    	}else if(logik.equals(ParamPath.RETURN_UPDATE)) {
    		String encript1 = UtilParam.getSecurePassword(bodyparam.getPassword(), UtilParam.getRandomIdSalt(9));
        	bodyparam.setPassword(encript1);
        	Map<String, String> param = new HashMap<>();
            ModelJson result = new ModelJson();
        	bodyparam.setId_seq(Long.parseLong(""+id));
        	try {    		
        		UserDetails userDetails = userDetailsService.createUserDetails(bodyparam.getEmail(), bodyparam.getPassword());
 	            String token = jwtTokenUtil.generateToken(userDetails);
 	            bodyparam.setToken(token);
        		UserLogin userLogin = userLoginService.updateDataApi(bodyparam);
                if(userLogin != null) {
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES, userLogin));
                }else {
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
                }            
    		} catch (Exception e) {
    			e.printStackTrace();
    			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
    		}      		
    	}else if(logik.equals(ParamPath.RETURN_DELETE)) {
    		Map<String, String> param = new HashMap<>();
            ModelJson result = new ModelJson();
        	UserLogin item = new UserLogin();
        	item.setId_seq(Long.parseLong(""+id));
        	try {    		
        		Boolean userLogin = userLoginService.deleteDataApi(item);
                if(userLogin) {
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES_DELETE, null));
                }else {
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
                }            
    		} catch (Exception e) {
    			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
    		}      		
    	}else if(logik.equals(ParamPath.RETURN_LIST)) {
    		datajson = new ArrayList(); 
        	ModelJson res = new ModelJson();
        	for(UserLogin i : userLoginRepo.findAll()) {
        		//UserDetails userDetails = userDetailsService.loadUserByUsername(bodyparam.getEmail());
        	    datajson.add(i);
        	}
        	
        	if(datajson == null) {
        		res.setResult(false);
    	    	res.setErrors("404");
    	    	res.setList(datajson);
        	}else {
        		res.setResult(true);
    	    	res.setErrors("SUCCESS");
    	    	res.setList(datajson);
        	}
        	
        	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(res);
    	}else{
    		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
    	}
    	
    }
}
