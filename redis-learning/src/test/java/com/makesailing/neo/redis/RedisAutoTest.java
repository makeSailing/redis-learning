package com.makesailing.neo.redis;

import com.makesailing.neo.BaseControllerTest;
import com.makesailing.neo.domain.UserInfo;
import com.makesailing.neo.redis.constants.RedisConstants;
import java.util.Date;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * #
 *
 * @author jamie
 * @date 2018/11/19 11:24
 */
public class RedisAutoTest extends BaseControllerTest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private RedisTemplate<String, UserInfo> redisTemplate;

	@Test
	public void save(){
		stringRedisTemplate.opsForValue().set("books:", "java fast");
		Assert.assertEquals("java fast", stringRedisTemplate.opsForValue().get("books:"));
	}

	@Test
	public void testObjSerializer() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(2L);
		userInfo.setUserName("张三");
		userInfo.setAge(19);
		userInfo.setGmtCreate(new Date());

		redisTemplate.opsForValue().set(RedisConstants.USER_INFO + userInfo.getUserId(), userInfo);
		Assert.assertEquals(true, redisTemplate.hasKey(RedisConstants.USER_INFO + userInfo.getUserId()));

		//stringRedisTemplate.opsForValue().set(USER_INFO + userInfo.getUserId(), JSON.toJSONString(userInfo));
		//
		//Assert.assertNotNull(stringRedisTemplate.opsForValue().get(USER_INFO + userInfo.getUserId()));
	}
}


