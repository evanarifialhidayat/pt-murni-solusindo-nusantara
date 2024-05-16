package com.app.interview.murni.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.util.ParamPath;
import com.app.interview.murni.util.UtilParam;

@Controller
@RequestMapping(value = com.app.interview.murni.util.ParamPath.PATH_WEB_INTERVIEW_MURNI_USER)
public class UserController {
	@Autowired UserLoginService userLoginService;
	@Autowired UserLoginRepo userLoginRepo;

	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_FORM)
	    public String registrasi(Model model){	   
	    	return "ptaz_registrasi_form";
	    }
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_CREATE)
		public String formsave(@ModelAttribute("name") UserLogin forminput, Model model) {
	    	System.out.println("=====sss======="+forminput.getAge());
	    		String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), UtilParam.getRandomIdSalt(9));
		    	forminput.setPassword(encript1);	
	    	userLoginService.saveData(forminput);
	    	return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
	    }
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_UPDATE)
  		public String formupdate(@ModelAttribute("name") UserLogin forminput, Model model) {
	    	String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), UtilParam.getRandomIdSalt(9));
	    	forminput.setPassword(encript1);	
	    	userLoginService.updateData(forminput);
  	        return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
  	    }
	    
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST)
	    public String formlist(Model model){
	    	
            JSONArray arr = new JSONArray();
            UserLogin obj = new UserLogin();
	        List listParam = (List<UserLogin>) userLoginRepo.findAll();
	   		if(listParam.size() > 0) {
	   			for(int i=0; i<listParam.size(); i++) {
	   				obj = (UserLogin) listParam.get(i);
	   			 }
	   		}
            model.addAttribute("arr",listParam);
	    	return "ptaz_registrasi_list";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_DELETE)
	    public String delete(Model model, @RequestParam String id){
	    	UserLogin obj = new UserLogin();
	        obj.setId_seq(Long.parseLong(id));
	        userLoginService.deleteData(obj);
	        return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
	    }

}
