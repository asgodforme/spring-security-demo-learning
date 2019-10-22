package com.jiangjie.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.jiangjie.validator.MyConstraint;

public class User {

	public interface UserSimpleView {
	};

	public interface UserDetailView extends UserSimpleView {
	};

	private String id;

	@MyConstraint(message = "这是一个测试")
	private String username;

	@NotBlank(message = "密码不能为空！")
	private String password;

	@Past(message = "生日不能是过去的时间！")
	private Date birthDay;

	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonView(UserSimpleView.class)
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

}
