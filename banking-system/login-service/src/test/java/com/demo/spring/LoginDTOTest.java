package com.demo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Users;

class LoginDTOTest {
	
	@Test
	void testCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(123);
		customer.setCustomerName("abc");
		customer.setEmailId("abc");
		customer.setPhoneNumber("123");
		
		assertEquals(123, customer.getCustomerId());
		assertEquals("abc",customer.getCustomerName());
		assertEquals("abc",customer.getEmailId());
		assertEquals("123",customer.getPhoneNumber());
		
	}
	
	@Test
	void testUsers() {
		Users user = new Users();
		user.setPassword("123");
		user.setUser("customer");
		user.setUserId(123);
		user.setUserName("abc");
		
		assertEquals(123, user.getUserId());
		assertEquals("123", user.getPassword());
		assertEquals("customer", user.getUser());
		assertEquals("abc", user.getUserName());

	}

}
