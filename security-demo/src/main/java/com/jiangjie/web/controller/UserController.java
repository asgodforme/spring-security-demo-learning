package com.jiangjie.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.jiangjie.dto.User;
import com.jiangjie.dto.User.UserDetailView;
import com.jiangjie.dto.User.UserSimpleView;
import com.jiangjie.dto.UserQueryCondition;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/me")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("/me1")
	public Object getCurrentUser(Authentication authentication) {
		return authentication;
	}
	
	@GetMapping("/me2")
	public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
		return userDetails;
	}

	@PostMapping
	public User create(@Valid @RequestBody User user) {
//		, BindingResult errors
//		if (errors.hasErrors()) {
//			errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
//		}

		System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
		user.setId("1");
		return user;
	}

	@PutMapping("/{id:\\d+}")
	public User update(@Valid @RequestBody User user, BindingResult errors) {

		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(error -> {
//				FieldError fieldError = (FieldError) error;
//				String message = fieldError.getField() + " " + error.getDefaultMessage();
				System.out.println(error.getDefaultMessage());
			});
		}

		System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
		user.setId("1");
		return user;
	}

	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}

//	@RequestMapping(value="/user", method=RequestMethod.GET)
//	public List<User> query(@RequestParam(value = "username") String nickname) {
//		List<User> user = new ArrayList<User>();
//		System.out.println(nickname);
//		user.add(new User());
//		user.add(new User());
//		user.add(new User());
//		return user;
//	}

	@GetMapping
	@JsonView(UserSimpleView.class)
	@ApiOperation(value = "用户查询服务")
	public List<User> query(UserQueryCondition condition, @PageableDefault Pageable pageable) {
		List<User> user = new ArrayList<User>();
		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
		user.add(new User());
		user.add(new User());
		user.add(new User());
		return user;
	}

	@GetMapping(value = "/{id:\\d+}")
	@JsonView(UserDetailView.class)
	public User getInfo(@ApiParam(value = "用户id") @PathVariable String id) {

//		throw new UserNotExistException(id, "user not exist");
//		throw new RuntimeException("user not exist");
		System.out.println("进入getInfo服务");
		User user = new User();
		user.setUsername("tom");
		return user;
	}

}
