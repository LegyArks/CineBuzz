package com.capgemini.CineBuzz.entities;

import jakarta.persistence.*;

@Entity
public class Register {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String name;

	private String email;

	private String password;

	private String phone;

    private String userType;

	public Register(Long userId, String name, String email, String password, String phone, String userType) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
	}
	
	public Register(String name, String email, String password, String phone, String userType) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userType = userType;
	}
	
	public Register() {
	
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Register [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", userType=" + userType + "]";
	}	
}

