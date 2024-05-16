package com.app.interview.ptaz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import com.app.interview.murni.MainApplication;
import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.model.res.ModelJson;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.services.token.JwtTokenUtil;
import com.app.interview.murni.services.token.JwtUserDetailsService;
import com.app.interview.murni.util.UtilParam;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@ContextConfiguration(classes = MainApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class MainApplicationTests implements ITesApiWeb {

	
	@Autowired UserLoginRepo userLoginRepo;
	@Autowired UserLoginService userLoginService;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired JwtUserDetailsService userDetailsService;
	@Autowired JwtTokenUtil jwtTokenUtil;
	
	List datajson = null;
	UserLogin userLogin;
	String email = "evan123hidayat@gmail.com";
	Integer age  = 19;
	String katasandi = "123456";
	
	@Test
	@Order(1)
	public void registrasiApi() {
		UserLogin bodyparam = new UserLogin();
		bodyparam.setEmail(email);
		bodyparam.setAge(age);
		String encript1 = UtilParam.getSecurePassword(katasandi, UtilParam.getRandomIdSalt(9));
		bodyparam.setPassword(encript1);
		userLogin = userLoginService.saveData(bodyparam);
		Assert.notNull(userLogin, "Non successful registration");	
		
	}

	@Test
	@Order(2)
	public void listAllApi() {
		datajson = new ArrayList(); 
    	ModelJson res = new ModelJson();
    	for(UserLogin i : userLoginRepo.findAll()) {
    	    datajson.add(i);
    	}
		Assert.isTrue(datajson.size() > 0, "Non successful get all data");	
	}

	@Test
	@Order(3)
	public void loginApi() {
		UserLogin get = userLoginRepo.findByEmail(userLogin.getEmail());
		String idsalt = get.getPassword().substring(0, 9);
		String encript1 = UtilParam.getSecurePassword(katasandi, idsalt);
       	userLogin.setPassword(encript1);
		UserLogin verifikasi = userLoginRepo.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
		UserDetails userDetails = userDetailsService.createUserDetails(userLogin.getEmail(), katasandi);
        String token = jwtTokenUtil.generateToken(userDetails);
        userLogin.setToken(token);
		Assert.notNull(verifikasi, "Non successful login api");	
	}

	@Test
	@Order(4)
	public void updateApi() {
		email = "evan12345hidayat@gmail.com";
		katasandi = "1234567";
		userLogin.setEmail(email);
		userLogin.setPassword(katasandi);
		String encript1 = UtilParam.getSecurePassword(userLogin.getPassword(), UtilParam.getRandomIdSalt(9));
    	userLogin.setPassword(encript1);
    	UserDetails userDetails = userDetailsService.createUserDetails(userLogin.getEmail(), userLogin.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        userLogin.setToken(token);
		userLogin = userLoginService.updateDataApi(userLogin);
		Assert.notNull(userLogin, "Non successful update api");	
	}

	@Test
	@Order(5)
	public void deleteApi() {
		Boolean obj = userLoginService.deleteDataApi(userLogin);
		Assert.isTrue(obj, "Non successful deleted api");	
	}

	@Test
	@Order(6)
	public void registrasiWeb() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(7)
	public void listAllWeb() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(8)
	public void loginWeb() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(9)
	public void updateWeb() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(10)
	public void deleteWeb() {
		// TODO Auto-generated method stub
		
	}
	
}
