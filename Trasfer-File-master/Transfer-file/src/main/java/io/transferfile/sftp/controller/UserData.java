package io.transferfile.sftp.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="userdata")
@Component
public class UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="username")
	private String username;
	@Column(name="publicIP")
	private String publicIP;
	@Column(name="password")
	private String password;
	@Column(name="active")
	private int active;
	
	public UserData() {
		
	}
	
	public UserData(int id, String username, String publicIP,String password,int active) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.publicIP = publicIP;
		this.active = active;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPublicIP() {
		return publicIP;
	}
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}

}
