package io.transferfile.sftp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Controller;
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
@CrossOrigin
public class Upload {

	@Autowired
	private UserService userService;

	private DefaultSftpSessionFactory gimmeFactory(String ip,String username, String password) {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
		factory.setHost(ip);
		factory.setPort(22);
		factory.setUser(username);
		factory.setPassword(password);
		factory.setAllowUnknownKeys(true);
		return factory;
	}
	
	UserData user1 = new UserData();
	UserData userExists = new UserData();

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMapping(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("*");
			}

		};
	}
	
	@CrossOrigin 
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Object> upload(@RequestBody String jsondata) throws IOException {

		 user1 = objectMapper.readValue(jsondata,UserData.class);
		 System.out.println("entered in upload post method.");
		 System.out.println(user1.getUsername());
		 userExists = userService.findUserByUsername(user1.getUsername());

		if (userExists == null) {
			System.out.println("User not exist");
			return new ResponseEntity<>("User not found", HttpStatus.NO_CONTENT);
		} 
		else {
				
			System.out.println("User exist");
			
			return new ResponseEntity<>("User is Registed", HttpStatus.OK);
		}

	}
	
	@CrossOrigin 
	@RequestMapping(value = "/upload/file", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Object> fileUpload(@RequestParam (required = true, value="file") MultipartFile  file) throws IOException {
		
		File convertFile = new File("C:\\Users\\dipsh\\git\\Transfer-file\\Transfer-file\\src\\main\\resources\\" + file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		
		
		System.out.println(userExists.getPublicIP());
		System.out.println(user1.getUsername());
		System.out.println(user1.getPassword());
		
		SftpSession session = gimmeFactory(userExists.getPublicIP(),userExists.getUsername(),userExists.getPassword()).getSession();
		InputStream resourceAsStream =
				Upload.class.getClassLoader().getResourceAsStream(file.getOriginalFilename());
		try { session.write(resourceAsStream, "upload/mynewfile" +
		  LocalDateTime.now() +".txt"); } 
		catch (IOException e) 
		{ throw new RuntimeException(e); } 
		session.close();

		
		return new ResponseEntity<>("File Uploaded", HttpStatus.OK);
	}
}
