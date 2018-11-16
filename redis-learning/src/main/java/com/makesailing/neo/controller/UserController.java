package com.makesailing.neo.controller;

import com.makesailing.neo.common.response.Response;
import com.makesailing.neo.domain.UserInfo;
import com.makesailing.neo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * #
 *
 * @author jamie
 * @date 2018/11/16 11:00
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public Response<Long> addUser(@RequestBody UserInfo userInfo) {
		Long userId = userService.addUser(userInfo);
		Response<Long> response = new Response<>();
		response.setResult(userId);
		return response;
	}

	@GetMapping("/get/{id}")
	public Response<UserInfo> getUserById(@PathVariable("id") Long userId) {
		UserInfo userInfo = userService.getUserById(userId);
		Response<UserInfo> response = new Response<>();
		response.setResult(userInfo);
		return response;
	}
}


