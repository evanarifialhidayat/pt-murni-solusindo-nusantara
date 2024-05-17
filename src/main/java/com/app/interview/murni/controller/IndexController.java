package com.app.interview.murni.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.util.ParamPath;
import com.app.interview.murni.util.UtilParam;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class IndexController {
	@Autowired UserLoginService userLoginService;
	@Autowired UserLoginRepo userLoginRepo;
	private static final Logger LOG = LogManager.getLogger(IndexController.class);
	
	    @GetMapping(value = "")
	    public String home(Model model){
	    	LOG.info("Load view login sukses selamat menikmati...");
	    	return "login";
	    }	    
	    @GetMapping(value = ParamPath.PATH_DEFAULTH)
	    public String home_1(Model model){
	    	LOG.info("Load view login sukses selamat menikmati...");
	    	return "login";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI)
	    public String home_2(Model model){
	    	LOG.info("Load view login sukses selamat menikmati...");
    		return "login";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_DASHBOARD)
	    public String home_3(Model model){
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	LOG.info("Load view dashboard sukses selamat menikmati...");
	    	return "ptaz";
	    }
	    
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST_VIEW_DATA)
	    public String view_registrasi(Model model){
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	Map<String, String> map = new HashMap<>();
    		model.addAttribute("message", "Login first with your email and password!");
    		LOG.info("Load view login sukses selamat menikmati...");
    		return "login";
	    }
	
	    
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_HOME_DASHBOARD)
	    public String home_4(@ModelAttribute("name") UserLogin forminput,Model model){	  
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	Long lo = userLoginRepo.countByEmail(forminput.getEmail());
    		if(lo == 1){
    	    	UserLogin get = userLoginRepo.findByEmail(forminput.getEmail());
    			String idsalt = get.getPassword().substring(0, 9);
    			String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), idsalt);
    			forminput.setPassword(encript1);
    			UserLogin verifikasi = userLoginRepo.findByEmailAndPassword(forminput.getEmail(), forminput.getPassword());
    			if(verifikasi == null) {
    				Map<String, String> map = new HashMap<>();
    	    		model.addAttribute("message", "Email and password are not registered, please register!");
    	    		LOG.error("Ada masalah karena email dan password belum terdaftar silahkan registrasi terlebih dahulu...");
    	    		return "login";
    			}else {
    				LOG.info("Load view dashboard sukses selamat menikmati...");
    				return "ptaz";
    			}
    		}else if(lo == 0){
    			Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Email and password are not registered, please register!");	  
	    		LOG.error("Ada masalah karena email dan password sudah terdaftar (DUPLICATE) silahkan hubungi admin...");
	    		return "login";
    		}else {
    			Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Email and password duplicated, please contact your admin!");	  
	    		LOG.error("Ada masalah karena email dan password sudah terdaftar (DUPLICATE) silahkan hubungi admin...");
	    		return "login";
    		}
	    	
	    }
}
