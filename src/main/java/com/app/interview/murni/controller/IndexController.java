package com.app.interview.murni.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.util.ParamPath;
import com.app.interview.murni.util.UtilParam;
import com.app.interview.murni.util.UtilReturn;

@Controller
public class IndexController {
	@Autowired UserLoginService userLoginService;
	@Autowired UserLoginRepo userLoginRepo;
	
	    @GetMapping(value = "")
	    public String home(Model model){
	    	return "login";
	    }	    
	    @GetMapping(value = ParamPath.PATH_DEFAULTH)
	    public String home_1(Model model){
	    	return "login";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI)
	    public String home_2(Model model){
    		return "login";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_DASHBOARD)
	    public String home_3(Model model){
//	    	System.out.println("=========2==22222222========");
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	return "ptaz";
	    }
	    
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_HOME_DASHBOARD)
	    public String home_4(@ModelAttribute("name") UserLogin forminput,Model model){	  
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	
	    	UserLogin get = userLoginRepo.findByEmail(forminput.getEmail());
    		if(get != null){
    			String idsalt = get.getPassword().substring(0, 9);
    			String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), idsalt);
    			forminput.setPassword(encript1);
    			UserLogin verifikasi = userLoginRepo.findByEmailAndPassword(forminput.getEmail(), forminput.getPassword());
    			if(verifikasi == null) {
    				Map<String, String> map = new HashMap<>();
    	    		model.addAttribute("message", "Email and password are not registered, please register!");
    	    		return "login";
    			}else {
    				return "ptaz";
    			}
    		}else {
    			Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Email and password are not registered, please register!");	    		 
	    		return "login";
    		}
	    	
	    }
}
