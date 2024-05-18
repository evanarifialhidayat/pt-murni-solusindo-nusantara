package com.app.interview.murni.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.app.interview.murni.controller.IndexController;
import com.app.interview.murni.model.ListAvarageMeanModus;
import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.model.res.ModelJson;
import com.app.interview.murni.repo.ListAvarageMeanModusRepo;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.ListAvarageMeanModusService;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.services.token.JwtTokenUtil;
import com.app.interview.murni.services.token.JwtUserDetailsService;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class UtilReturn {
	@Autowired  static UserLoginRepo userLoginRepo;
	@Autowired  static UserLoginService userLoginService;	
	@Autowired  static AuthenticationManager authenticationManager;
	@Autowired  static JwtUserDetailsService userDetailsService;
	@Autowired  static JwtTokenUtil jwtTokenUtil;
	@Autowired  static ListAvarageMeanModusRepo avarageMeanModusRepo;
	@Autowired  static ListAvarageMeanModusService avarageMeanModusService;
	private static final Logger LOG = LogManager.getLogger(UtilReturn.class);

	static List datajson = null;
	
	public UtilReturn(UserLoginRepo userLoginRepo,
					  UserLoginService userLoginService, 
					  List datajson,
					  AuthenticationManager authenticationManager,
					  JwtUserDetailsService userDetailsService,
					  JwtTokenUtil jwtTokenUtil,
					  ListAvarageMeanModusRepo avarageMeanModusRepo,
					  ListAvarageMeanModusService avarageMeanModusService
					  ) {
		super();
		this.userLoginRepo = userLoginRepo;
		this.userLoginService = userLoginService;
		this.datajson = datajson;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.avarageMeanModusRepo = avarageMeanModusRepo;
		this.avarageMeanModusService = avarageMeanModusService;
	}




	public static  ResponseEntity<?> returncontroller(String logik,UserLogin bodyparam,Integer id){
		Long lo = userLoginRepo.countByEmail(bodyparam.getEmail());
    	if(logik.equals(ParamPath.RETURN_LOGIN)) {
    		if(lo == 1){
    			UserLogin get = userLoginRepo.findByEmail(bodyparam.getEmail());
    			String idsalt = get.getPassword().substring(0, 9);
    			String encript1 = UtilParam.getSecurePassword(bodyparam.getPassword(), idsalt);
           	    bodyparam.setPassword(encript1);
	           	Map<String, String> param = new HashMap<>();
	     	    ModelJson result = new ModelJson();
	     	 	try {    		
	     	 		    UserLogin verifikasi = userLoginRepo.findByEmailAndPassword(bodyparam.getEmail(), bodyparam.getPassword());
	     	 	 		if(verifikasi != null) {
	     	 	 			UserDetails userDetails = userDetailsService.createUserDetails(bodyparam.getEmail(), bodyparam.getPassword());
	     	 	            String token = jwtTokenUtil.generateToken(userDetails);
	     	 	            verifikasi.setToken(token);
	     	 	            UserLogin userLogin = userLoginService.saveData(verifikasi);
	     	 	            LOG.info("Login dengan api sukses selamat menikmati...",userLogin.toString());
	      	 				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES, verifikasi));
	     	 		 	}else {
	     	 		 		LOG.error("Login api ada masalah karena email dan password tidak terdaftar silahkan hubungi admin...");
	     	 		    	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
	     	 		 	}            
	     		} catch (Exception e) {
	     			    LOG.error("Load view login api gagal selamat menikmati...",UtilParam.printStack(e)); 
	     			    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
	     		}  
    		}else {
    			LOG.error("Login api ada masalah karena email dan password tidak terdaftar silahkan hubungi admin...");
    			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_DUPLICATE, null));
    		}
    	}else if(logik.equals(ParamPath.RETURN_REGISTER)) {
    		 String encript1 = UtilParam.getSecurePassword(bodyparam.getPassword(), UtilParam.getRandomIdSalt(9));
        	 bodyparam.setPassword(encript1);
        	 Map<String, String> param = new HashMap<>();
    	     ModelJson result = new ModelJson();
    	 	 try {    		
    	 		 		UserLogin userLogin = userLoginService.saveData(bodyparam);
    	 		 		if(userLogin != null) {    	 		 			
    	 		 			    LOG.info("Registrasi api data sukses..."+userLogin.toString());
    	 		 				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES, userLogin));
    	 		 		}else {
    	 		 				LOG.error("Registrasi api data gagal...");
    	 		 				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
    	 		 		}            
    			} catch (Exception e) {
    							LOG.error("Registrasi api data gagal...",UtilParam.printStack(e));
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
                	LOG.info("Update api data sukses..."+userLogin.toString());
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES, userLogin));
                }else {
                	LOG.error("Update api data gagal...");
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
                }            
    		} catch (Exception e) {
    			LOG.error("Update api data gagal...",UtilParam.printStack(e));
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
                	LOG.error("Deleted api data suksess..."+userLogin.toString());
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_SUKSES_DELETE, null));
                }else {
                	LOG.error("Deleted api data gagal...");
                	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_GAGAL_REPO, null));
                }            
    		} catch (Exception e) {
    			LOG.error("Deleted api data gagal...",UtilParam.printStack(e));
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
    	    	LOG.error("List api data gagal..."+datajson.toString());
        	}else {
        		res.setResult(true);
    	    	res.setErrors("SUCCESS");
    	    	res.setList(datajson);
    	    	LOG.info("List api data suksess..."+datajson.toString());
        	}
        	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(res);
    	}
//    	else if(logik.equals(ParamPath.RETURN_LIST_EXAMPLE)) {
//    		datajson = new ArrayList(); 
//        	ModelJson res = new ModelJson();
//        	Iterable<ListAvarageMeanModus> iterable =  avarageMeanModusRepo.findAll();
//        	if(iterable.hashCode() != 10) {
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",80,"Happy",UtilParam.convStringDate("2020-02-20")));      //  1
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",90,"Sad",   UtilParam.convStringDate("2020-02-20")));       // 2
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",85,"Happy",UtilParam.convStringDate("2020-02-20")));      //  3
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",75,"Sad",  UtilParam.convStringDate("2020-02-20")));      //  4
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",65,"Angry", UtilParam.convStringDate("2020-02-20")));       // 5
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",85,"Happy",UtilParam.convStringDate("2020-02-21")));      //  6
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",90,"Sad",   UtilParam.convStringDate("2020-02-21")));       // 7
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",75,"Sad",  UtilParam.convStringDate("2020-02-21")));      //  8
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",85,"Sad",   UtilParam.convStringDate("2020-02-21")));      //  9
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",70,"Happy", UtilParam.convStringDate("2020-02-21")));      //  0
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",80,"Sad",  UtilParam.convStringDate("2020-02-21")));      //  11
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",73,"Sad",  UtilParam.convStringDate("2020-02-22")));      //  12
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",75,"Angry",UtilParam.convStringDate("2020-02-22")));      //  13
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",82,"Sad",  UtilParam.convStringDate("2020-02-22")));      //  14
//				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",65,"Sad",  UtilParam.convStringDate("2020-02-22")));      // 15
//        	}
//        	for(ListAvarageMeanModus i : avarageMeanModusRepo.findAll()) {
//        		//UserDetails userDetails = userDetailsService.loadUserByUsername(bodyparam.getEmail());
//        	    datajson.add(i);
//        	}
//        	
//        	if(datajson == null) {
//        		res.setResult(false);
//    	    	res.setErrors("404");
//    	    	res.setList(datajson);
//    	    	LOG.error("List avarage score api data gagal..."+datajson.toString());
//        	}else {
//        		res.setResult(true);
//    	    	res.setErrors("SUCCESS");
//    	    	res.setList(datajson);
//    	    	LOG.info("List avarage score data suksess..."+datajson.toString());
//        	}
//        	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(res);
//    	}
    	else{
    		LOG.error("Path belum tersedia tunggu ya...");
    		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(UtilParam.mappingdata(ParamPath.VALIDATION_TRY, null));
    	}
    	
    }
}
