package com.bacancy.ecommerce.dto;

public class UserDto {

	private Long id;
	private String email;
	private int roleId;
	private UserProfileDto userProfileDto;
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public UserProfileDto getUserProfileDto() {
		return userProfileDto;
	}
	public void setUserProfileDto(UserProfileDto userProfileDto) {
		this.userProfileDto = userProfileDto;
	}
	
	
	
	
}
