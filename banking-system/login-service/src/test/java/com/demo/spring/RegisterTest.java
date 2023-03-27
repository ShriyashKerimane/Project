package com.demo.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.entity.RegisterInput;
import com.demo.spring.entity.Users;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,properties = {
		"employeeServer=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
class RegisterTest {
	
	String employeeServer="http://localhost:${wiremock.server.port}";

	@LocalServerPort
	int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void testRegisterUser() throws Exception{
		Users user = new Users("abc", "root", "customer");
		user.setUserId(12345);
		Message message = new Message("Customer save successfully");
		
		RegisterInput input = new RegisterInput("abc", "root", "999", "shriyashkerimane284@gmail.com", "customer");
		
		ObjectMapper mapper = new ObjectMapper();
		String messageJson = mapper.writeValueAsString(message);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<RegisterInput> req = new HttpEntity<>(input, headers);
		
		stubFor(post(urlEqualTo("/employee/savecustomer"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/register",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals(message.getStatus(), response.getBody().getStatus());
	}
	
	@Test
	void testRegisterEmployee() throws Exception{
		Users user = new Users("abc", "root", "employee");
		user.setUserId(12345);
		Message message = new Message("User credential saved");
		
		RegisterInput input = new RegisterInput("abc", "root", "999", "shriyashkerimane284@gmail.com", "employee");
		
		ObjectMapper mapper = new ObjectMapper();
		String messageJson = mapper.writeValueAsString(message);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<RegisterInput> req = new HttpEntity<>(input, headers);
		
		stubFor(post(urlEqualTo("/employee/savecustomer"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));
		ResponseEntity<Message> response = testRestTemplate.exchange("http://localhost:" + port + "/gateway/register",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals(message.getStatus(), response.getBody().getStatus());
	}
}
