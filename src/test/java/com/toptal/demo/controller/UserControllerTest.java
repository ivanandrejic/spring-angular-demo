package com.toptal.demo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    public void testGetAllAdmin() throws Exception {
    	mockMvc.perform(get("/rest/users").with(httpBasic("admin", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetAllManager() throws Exception {
    	mockMvc.perform(get("/rest/users").with(httpBasic("manager", "toptal")))
			.andDo(print())
			.andExpect(status().isOk())
		;
    }
    
    @Test
    public void testGetAllUser() throws Exception {
    	mockMvc.perform(get("/rest/users").with(httpBasic("user", "toptal")))
			.andDo(print())
			.andExpect(status().is4xxClientError())
		;
    }
    
    
	
}
