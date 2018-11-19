package com.makesailing.neo.service;

import com.makesailing.neo.domain.UserInfo;

/**
 * #
 *
 * @author jamie
 * @date 2018/11/16 11:00
 */
public interface UserService {

	String SERVICE_ID = "userService";

	Long addUser(UserInfo userInfo);

	UserInfo getUserById(Long userId);

	int deleteUserById(Long userId);

	int updateUser(UserInfo userInfo);

}
