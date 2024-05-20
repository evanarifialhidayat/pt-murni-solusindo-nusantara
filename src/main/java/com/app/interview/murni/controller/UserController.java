package com.app.interview.murni.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.app.interview.murni.model.ListAvarageMeanModus;
import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.repo.ListAvarageMeanModusRepo;
import com.app.interview.murni.repo.UserLoginRepo;
import com.app.interview.murni.services.ListAvarageMeanModusService;
import com.app.interview.murni.services.UserLoginService;
import com.app.interview.murni.util.ParamPath;
import com.app.interview.murni.util.UtilParam;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping(value = com.app.interview.murni.util.ParamPath.PATH_WEB_INTERVIEW_MURNI_USER)
@Log4j2
public class UserController {
	@Autowired UserLoginService userLoginService;
	@Autowired UserLoginRepo userLoginRepo;
	@Autowired ListAvarageMeanModusRepo avarageMeanModusRepo;
	@Autowired ListAvarageMeanModusService avarageMeanModusService;
	private static final Logger LOG = LogManager.getLogger(UserController.class);
	
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_USER_FORM)
	    public String home_3(Model model){
	    	LOG.info("Load view registrasi sukses selamat menikmati...");
	    	return "ptaz_registrasi_form";
	    }

	   
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_CREATE)
		public String formsave(@ModelAttribute("name") UserLogin forminput, Model model) {
	    	String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), UtilParam.getRandomIdSalt(9));
		    forminput.setPassword(encript1);	
		    Long lo = userLoginRepo.countByEmail(forminput.getEmail());
		    if(lo == 0){
		    	try {
		    		userLoginService.saveData(forminput);
		    		Map<String, String> map = new HashMap<>();
    	    		model.addAttribute("message", "Successful registration!");
		    		userLoginService.saveData(forminput);
		    		LOG.info("Registrasi data sukses selamat menikmati..."+forminput.toString());
		    		return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
		    	}catch(Exception e) {
		    		Map<String, String> map = new HashMap<>();
    	    		model.addAttribute("message", "Non successful registration!");
    	    		LOG.error("Registrasi data gagal...",UtilParam.printStack(e));
		    		return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
		    	}
		    }else {
		    	Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Email is registered!");
	    		LOG.error("Ada masalah karena email dan password sudah terdaftar (DUPLICATE) silahkan hubungi admin...");
		    	return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;	    		
		    }
	    }
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_FORM)
		public String formsaveOnRegister(@ModelAttribute("name") UserLogin forminput, Model model) {
	    	String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), UtilParam.getRandomIdSalt(9));
		    forminput.setPassword(encript1);	
		    Long lo = userLoginRepo.countByEmail(forminput.getEmail());
		    if(lo == 0){
		    	try {
		    		Map<String, String> map = new HashMap<>();
    	    		model.addAttribute("message", "Successful registration!");
		    		userLoginService.saveData(forminput);
		    		LOG.info("Registrasi data sukses selamat menikmati..."+forminput.toString());
		    		return "login";
		    	}catch(Exception e) {
		    		Map<String, String> map = new HashMap<>();
    	    		model.addAttribute("message", "Non successful registration!");
    	    		LOG.error("Registrasi data gagal...",UtilParam.printStack(e));
		    		return "login";
		    	}
		    }else{
		    	Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Email is registered!");
	    		LOG.error("Ada masalah karena email dan password sudah terdaftar (DUPLICATE) silahkan hubungi admin...");
	    		return "login";
		    }
	    }
	    @PostMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_UPDATE)
  		public String formupdate(@ModelAttribute("name") UserLogin forminput, Model model) {
	    	try {
	    		String encript1 = UtilParam.getSecurePassword(forminput.getPassword(), UtilParam.getRandomIdSalt(9));
		    	forminput.setPassword(encript1);	
		    	userLoginService.updateData(forminput);
		    	Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Successful update!");
	    		LOG.info("Update registrasi data sukses selamat menikmati..."+forminput.toString());
	  	        return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
	    	}catch (Exception e) {
	    		Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Non successful update!");
	    		LOG.error("Update registrasi data gagal...",UtilParam.printStack(e));
	  	        return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
			}
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
            LOG.info("List registrasi data sukses selamat menikmati..."+listParam.toString());
	    	return "ptaz_registrasi_list";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST_EXAMPLE)
	    public String formlistExample(Model model){
	    	JSONArray arr = new JSONArray();
	        ListAvarageMeanModus obj = new ListAvarageMeanModus();
	        List listParam = (List<ListAvarageMeanModus>) avarageMeanModusRepo.findAll();
	        if(listParam.size() < 15) {
	        	
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",80,"Happy",UtilParam.convStringDate("2020-02-20")));      //  1
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",90,"Sad",   UtilParam.convStringDate("2020-02-20")));       // 2
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",85,"Happy",UtilParam.convStringDate("2020-02-20")));      //  3
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",75,"Sad",  UtilParam.convStringDate("2020-02-20")));      //  4
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",65,"Angry", UtilParam.convStringDate("2020-02-20")));       // 5
				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",85,"Happy",UtilParam.convStringDate("2020-02-21")));      //  6
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",90,"Sad",   UtilParam.convStringDate("2020-02-21")));       // 7
				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",75,"Sad",  UtilParam.convStringDate("2020-02-21")));      //  8
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",85,"Sad",   UtilParam.convStringDate("2020-02-21")));      //  9
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Josh",70,"Happy", UtilParam.convStringDate("2020-02-21")));      //  0
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",80,"Sad",  UtilParam.convStringDate("2020-02-21")));      //  11
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",73,"Sad",  UtilParam.convStringDate("2020-02-22")));      //  12
				avarageMeanModusService.insertData(new ListAvarageMeanModus("Kevin",75,"Angry",UtilParam.convStringDate("2020-02-22")));      //  13
				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",82,"Sad",  UtilParam.convStringDate("2020-02-22")));      //  14
				avarageMeanModusService.insertData(new ListAvarageMeanModus("David",65,"Sad",  UtilParam.convStringDate("2020-02-22")));      // 15
				listParam = (List<ListAvarageMeanModus>) avarageMeanModusRepo.findAllData();
				LOG.info("Insert 10 data avarage score data sukses selamat menikmati..."+listParam.toString());
        	}
   			for(int i=0; i<listParam.size(); i++) {
   				obj = (ListAvarageMeanModus) listParam.get(i);
   				String pattern = "yyyy-MM-dd";
   				DateFormat df = new SimpleDateFormat(pattern);
   				Date dates = obj.getCreated().getTime();   
   				String todayAsString = df.format(dates);
   				obj.setCreated_string(todayAsString);
   			}
            model.addAttribute("arr",listParam);
            LOG.info("List avarage score data sukses selamat menikmati..."+listParam.toString());
            
            
            List listParam2 = (List<ListAvarageMeanModus>) avarageMeanModusRepo.findAllAvarageScore();
            Map<String, String> mapavarage = new HashMap<>();
    		model.addAttribute("total_avarage", ""+listParam2.size());
    		ListAvarageMeanModus avarage;
    		for(int i=0; i<listParam2.size(); i++){
    			avarage = (ListAvarageMeanModus) listParam2.get(i);
    			model.addAttribute("avgname"+i, ""+avarage.getName()+" - "+avarage.getAvg());
    		}
    		
    		
    		List<Object[]> listParam3 = avarageMeanModusRepo.findAllModusEmotion();
    		model.addAttribute("total_avarage", ""+listParam3.size());
    		ListAvarageMeanModus modus;
    		for(int i=0; i<listParam3.size(); i++){
    			modus = (ListAvarageMeanModus) listParam2.get(i);
    			model.addAttribute("modusemution"+i, ""+listParam3.get(i)[0].toString()+" - "+listParam3.get(i)[1].toString());
    		}
    		
            
    		List<Object[]> listParam4 = avarageMeanModusRepo.findAllAvarageScoreNameTanggal();
    		List listavaragescorenametanggal = new ArrayList<ListAvarageMeanModus>();
    		for(int i=0; i<listParam4.size(); i++){
    			listavaragescorenametanggal.add(new ListAvarageMeanModus(
    					i+1,
    					listParam4.get(i)[0].toString(),
    					listParam4.get(i)[1].toString(),
    					Double.parseDouble(""+listParam4.get(i)[2]
    							)));
    		}
			model.addAttribute("listavaragescorenametanggal",listavaragescorenametanggal);
			
			List<Object[]> listParam5 = avarageMeanModusRepo.findAllModusEmotionNameTanggal();
    		List listmodusemotionnametanggal = new ArrayList<ListAvarageMeanModus>();
    		for(int i=0; i<listParam5.size(); i++){
    			listmodusemotionnametanggal.add(new ListAvarageMeanModus(
    					i+1,
    					listParam5.get(i)[0].toString(),
    					listParam5.get(i)[1].toString(),
    					listParam5.get(i)[2].toString()
    					));
    		}
			model.addAttribute("listmodusemotionnametanggal",listmodusemotionnametanggal);
    		
            
            
	    	return "ptaz_avarage_score";
	    }
	    @GetMapping(value = ParamPath.PATH_WEB_INTERVIEW_MURNI_DELETE)
	    public String delete(Model model, @RequestParam String id){
	    	try {
	    		UserLogin obj = new UserLogin();
		        obj.setId_seq(Long.parseLong(id));
		        userLoginService.deleteData(obj);
		        Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Successful deleted!");
	    		LOG.info("Deleted registrasi data sukses selamat menikmati...",obj.toString());
		        return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
	    	}catch (Exception e) {
	    		Map<String, String> map = new HashMap<>();
	    		model.addAttribute("message", "Non successful deleted!");
	    		LOG.error("Deleted registrasi data gagal selamat menikmati...",UtilParam.printStack(e));
	    		return "redirect:/user"+ParamPath.PATH_WEB_INTERVIEW_MURNI_LIST;
			}
	    }

}
