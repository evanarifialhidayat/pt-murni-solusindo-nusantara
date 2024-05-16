package com.app.interview.murni.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.interview.murni.model.UserLogin;
import com.app.interview.murni.model.res.ModelJson;
import com.app.interview.murni.util.ParamPath;
import com.app.interview.murni.util.UtilReturn;

@RestController
public class UserApiController {
	
    @GetMapping(value = ParamPath.PATH_API_INTERVIEW_MURNI_LIST)
    public ResponseEntity<ModelJson> listitem(){	    
    	return (ResponseEntity<ModelJson>) UtilReturn.returncontroller(ParamPath.RETURN_LIST, null, null);
    }
    
    @PostMapping(value = ParamPath.PATH_API_INTERVIEW_MURNI_LOGIN)
    public ResponseEntity<?> login(@Validated @RequestBody UserLogin bodyparam){	   
    	return UtilReturn.returncontroller(ParamPath.RETURN_LOGIN, bodyparam, null);
    }
    
    @PostMapping(value = ParamPath.PATH_API_INTERVIEW_MURNI_CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createitem(@Validated @RequestBody UserLogin bodyparam){	
    	return UtilReturn.returncontroller(ParamPath.RETURN_REGISTER, bodyparam, null);
    }
    
    @PostMapping(value = ParamPath.PATH_API_INTERVIEW_MURNI_UPDATE)
    public ResponseEntity<?> updateitem(@PathVariable Integer id,@Validated @RequestBody UserLogin bodyparam){	 
    	return UtilReturn.returncontroller(ParamPath.RETURN_UPDATE, bodyparam, id);
    }
    @DeleteMapping(value = ParamPath.PATH_API_INTERVIEW_MURNI_DELETE)
    public ResponseEntity<?> deleteitem(@PathVariable Integer id){	 
    	return UtilReturn.returncontroller(ParamPath.RETURN_DELETE, null, id);
    }

    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ModelJson>handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> param = new HashMap<>();
        ModelJson result = new ModelJson();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            param.put(fieldName, errorMessage);            
        });
        result.setResult(true);
    	result.setErrors("ERORR VALIDATION");
    	result.setList(param);
    	
    	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
    }
}


//curl -H "Content-Type: application/json" -X POST -d '{"nama":"evanbb","email":"e@gmail.com","katasandi":"123456"}' http://localhost:8080/api/registrasi  
//curl -H "Content-Type: application/json" -X POST -d '{"nama":"evancc,"email":"a@gmail.com","katasandi":"1234567"}' http://localhost:8080/api/update/1
//curl -H "Content-Type: application/json" -X POST -d '{"email":"a@gmil.com","katasandi":"1234567"}' http://localhost:8080/api/login   
//curl -v http://localhost:8080/api/list
//curl -X "DELETE" 'http://localhost:8080/api/delete/1'





