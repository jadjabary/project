package io.transferfile.sftp.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.transferfile.sftp.service.UserService;

@RestController
public class Register {
	
	@Autowired
	private UserService userService;
	
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Bean
	public WebMvcConfigurer corsconfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMapping(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("*");
			}
		};
	}
	
		
	 @CrossOrigin
	 @RequestMapping(value="/register", method=RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	 public ResponseEntity<Object> register(@RequestBody String jsondata) throws IOException{
		 
		 UserData user = objectMapper.readValue(jsondata,UserData.class);
		 System.out.println("entered in to Register method");
		 System.out.println(user.getUsername());
		 UserData userExists = userService.findUserByUsername(user.getUsername());
		 
		 
		 if(userExists != null) {
			 System.out.println("User exist register");
			 return new ResponseEntity<> ("User is already registered",HttpStatus.ALREADY_REPORTED);
		 }
		 else
		 {
			 
			System.out.println("User not exist register");
			userService.saveUser(user);
			return new ResponseEntity<> ("User is Registed",HttpStatus.OK);
		 }
		 
	 }
	
}

