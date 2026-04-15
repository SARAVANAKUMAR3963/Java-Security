package com.pck.security.user;

import lombok.Data;

@Data
public class UserRequest {

	Integer id;

	String userName;

	String password;

	String Name;

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "UserRequest [id=" + id + ", userName=" + userName + ", password=" + password + ", Name=" + Name + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	
}
