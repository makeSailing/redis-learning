package com.makesailing.neo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseControllerTest {

	@Test
	public void contextLoads() {
	}

	@Autowired
	protected WebApplicationContext webApplicationContext;

	/**
	 * 模拟mvc测试对象
	 */
	protected MockMvc mockMvc;


	/**
	 * 所有测试方法执行之前执行该方法
	 */
	@Before
	public void before() {
		//获取mockmvc对象实例
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

}
