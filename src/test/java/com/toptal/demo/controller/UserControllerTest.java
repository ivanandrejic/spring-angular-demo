package com.toptal.demo.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.toptal.demo.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
    private MockMvc mockMvc;

	
//**********************************************************
//	Test GET all
//**********************************************************
    @Test
    public void testGetAllAdmin() throws Exception {
    	mockMvc.perform(get("/rest/users").secure(true).with(httpBasic("admin", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetAllManager() throws Exception {
    	mockMvc.perform(get("/rest/users").secure(true).with(httpBasic("manager", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetAllUser() throws Exception {
    	mockMvc.perform(get("/rest/users").secure(true).with(httpBasic("user", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
//**********************************************************
//	Test GET one admin
//**********************************************************
    @Test
    public void testGetCurrentAdmin() throws Exception {
    	mockMvc.perform(get("/rest/users/1").secure(true).with(httpBasic("admin", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetOtherAdmin() throws Exception {
    	mockMvc.perform(get("/rest/users/4").secure(true).with(httpBasic("admin", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetNoneAdmin() throws Exception {
    	mockMvc.perform(get("/rest/users/42").secure(true).with(httpBasic("admin", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
//**********************************************************
//	Test GET one manager
//**********************************************************
    @Test
    public void testGetCurrentManager() throws Exception {
    	mockMvc.perform(get("/rest/users/3").secure(true).with(httpBasic("manager", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetAdminManager() throws Exception {
    	mockMvc.perform(get("/rest/users/1").secure(true).with(httpBasic("manager", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
    @Test
    public void testGetOtherManager() throws Exception {
    	mockMvc.perform(get("/rest/users/4").secure(true).with(httpBasic("manager", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetNoneManager() throws Exception {
    	mockMvc.perform(get("/rest/users/42").secure(true).with(httpBasic("manager", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
//**********************************************************
//	Test GET one user
//**********************************************************
    @Test
    public void testGetCurrentUser() throws Exception {
    	mockMvc.perform(get("/rest/users/4").secure(true).with(httpBasic("user", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetAdminUser() throws Exception {
    	mockMvc.perform(get("/rest/users/1").secure(true).with(httpBasic("user", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
    @Test
    public void testGetOtherUser() throws Exception {
    	mockMvc.perform(get("/rest/users/3").secure(true).with(httpBasic("user", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
    @Test
    public void testGetNoneUser() throws Exception {
    	mockMvc.perform(get("/rest/users/42").secure(true).with(httpBasic("user", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
	
}
