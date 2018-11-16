package com.makesailing.neo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson.JSON;
import com.makesailing.neo.BaseControllerTest;
import com.makesailing.neo.domain.UserInfo;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

/** 
* UserController Tester. 
* 
* @author jamie
* @since <pre>11/16/2018</pre> 
*/ 
public class UserControllerTest extends BaseControllerTest {

    @Before
    public void setUp() throws Exception { 
    }

    
    @After
    public void tearDown() throws Exception { 
    } 
    
    /** 
    * 
    * Method: addUser(@ReqauestBody UserInfo userInfo)
    * 
    */ 
    @Test
    public void testAddUser() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("jamie");
        userInfo.setPassword("abc123");
        userInfo.setAge(18);
        userInfo.setGender(1);
        userInfo.setState(1);
        userInfo.setLastLoginIp("127.0.0.1");
        userInfo.setLastLoginTime(new Date());
        userInfo.setGmtCreate(new Date());
        userInfo.setGmtModified(new Date());
        String userJson = JSON.toJSONString(userInfo);

        String response = mockMvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
            .andExpect(status().isOk())
            .andDo(print()).andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(response);
        System.out.println(response);
    } 
    
    /** 
    * 
    * Method: getUserById(@PathVariable("id") Long userId) 
    * 
    */ 
    @Test
    public void testGetUserById() throws Exception { 
    //TODO: Test goes here... 
    } 
    
        
    } 
