package com.bacancy.ecommerce.dto;

public class UserDto {

	private Long id;
	private String email;
	private String role;
	private String password;
	private UserProfileDto userProfile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserProfileDto getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfileDto userProfile) {
		this.userProfile = userProfile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
