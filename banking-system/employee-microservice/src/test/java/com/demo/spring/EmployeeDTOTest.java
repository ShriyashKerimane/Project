package com.demo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.dto.CustomerDetailDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.input.AccountNumberInput;
import com.demo.spring.util.Message;

class EmployeeDTOTest {
	
	@Test
	void testAccount() {
		Account account = new Account();
		account.setAccountNumber(123);
		account.setCustomerId(111);
		account.setAccountType("FD");
		account.setBalance(1000.0);
		account.setBranch("Sirsi");
		account.setStatus("active");
		
		assertEquals(123,account.getAccountNumber());
		assertEquals("FD",account.getAccountType());
		assertEquals(1000.0,account.getBalance());
		assertEquals("Sirsi",account.getBranch());
		assertEquals("active",account.getStatus());
		assertEquals(111,account.getCustomerId());
		
	}
	
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
	void testCustomerDetailDTO() {
		CustomerDetailDTO detail = new CustomerDetailDTO();
		
		List<Account> list = new ArrayList<>();
		Account account = new Account(123, "FD", 1000.0, 321, "active", "sirsi");
		list.add(account);
		detail.setAccounts(list);
		detail.setCustomerId(321);
		detail.setCustomerName("abc");
		detail.setEmailId("abc@def.com");
		detail.setPhoneNumber("911");
		
		assertEquals(list, detail.getAccounts());
		assertEquals(321, detail.getCustomerId());
		assertEquals("abc", detail.getCustomerName());
		assertEquals("abc@def.com", detail.getEmailId());
		assertEquals("911", detail.getPhoneNumber());
		
	}
	
	@Test
	void testCustomerDTO() {
		
		CustomerDTO customer = new CustomerDTO(111, "Shriyash", "911", "shriyash@everywhere.com");
		
		assertEquals(111, customer.getCustomerId());
		assertEquals("Shriyash", customer.getCustomerName());
		assertEquals("shriyash@everywhere.com", customer.getEmailId());
		assertEquals("911", customer.getPhoneNumber());
		
	}
	
	@Test
	void testMessage() {
		Message message = new Message();
		message.setStatus("Hello");
		
		assertEquals("Hello", message.getStatus());
	}
	
	@Test
	void testAccountNumberInput() {
		
		AccountNumberInput input = new AccountNumberInput(123);
		assertEquals(123, input.getAccountNumber());

	}

}
