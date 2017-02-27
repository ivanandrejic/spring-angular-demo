package com.toptal.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.toptal.demo.Application;
import com.toptal.demo.config.SecurityConfig;
import com.toptal.demo.domain.SecureUser;
import com.toptal.demo.repo.SecureUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Import(SecurityConfig.class)
public class UserControllerTest {
	
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	 private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(),
	            Charset.forName("utf8"));

	    private MockMvc mockMvc;

	    private String userName = "bdussault";

	    private HttpMessageConverter mappingJackson2HttpMessageConverter;
	    
	    @Autowired
	    private SecureUserRepository userRepo;


	    @Autowired
	    private WebApplicationContext webApplicationContext;

	    @Autowired
	    void setConverters(HttpMessageConverter<?>[] converters) {

	        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
	            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
	            .findAny()
	            .orElse(null);

	        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	    }

	    @Before
	    public void setup() throws Exception {
//	    	MockMvcBuilders
//			.webAppContextSetup(webApplicationContext)
//			.apply(SecurityMockMvcConfigurers.springSecurity())
//			.build();
	        this.mockMvc = webAppContextSetup(webApplicationContext).build();
	        
	        SecureUser user = new SecureUser();
	        user.setName("test");
	        user.setRole("ROLE_ADMIN");
			userRepo.save(user);

//	        this.bookmarkRepository.deleteAllInBatch();
//	        this.accountRepository.deleteAllInBatch();
//
//	        this.account = accountRepository.save(new Account(userName, "password"));
//	        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + userName, "A description")));
//	        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + userName, "A description")));
	    }

//	    @Test
//	    public void userNotFound() throws Exception {
//	        mockMvc.perform(post("/rest/users/")
//	                .content(this.json(new Bookmark()))
//	                .contentType(contentType))
//	                .andExpect(status().isNotFound());
//	    }

//	    @Test
//	    public void readSingleBookmark() throws Exception {
//	        mockMvc.perform(get("/" + userName + "/bookmarks/"
//	                + this.bookmarkList.get(0).getId()))
//	                .andExpect(status().isOk())
//	                .andExpect(content().contentType(contentType))
//	                .andExpect(jsonPath("$.id", is(this.bookmarkList.get(0).getId().intValue())))
//	                .andExpect(jsonPath("$.uri", is("http://bookmark.com/1/" + userName)))
//	                .andExpect(jsonPath("$.description", is("A description")));
//	}

    @Test
    public void readSingleBookmark() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
	}
    
    HttpHeaders createHeaders(String username, String password){
	   return new HttpHeaders() {{
	         String auth = username + ":" + password;
	         byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
	         String authHeader = "Basic " + new String( encodedAuth );
	         set( "Authorization", authHeader );
	      }};
	}
	    
	    
}
