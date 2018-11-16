package com.makesailing.neo.service.impl;

import com.makesailing.neo.domain.UserInfo;
import com.makesailing.neo.mappers.UserInfoMapper;
import com.makesailing.neo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * #
 *
 * @author jamie
 * @date 2018/11/16 11:04
 */
@Service(value = UserService.SERVICE_ID)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public Long addUser(UserInfo userInfo) {
		return userInfoMapper.insertSelective(userInfo);
	}

	@Override
	public UserInfo getUserById(Long userId) {
		return userInfoMapper.selectByPrimaryKey(userId);
	}
}


