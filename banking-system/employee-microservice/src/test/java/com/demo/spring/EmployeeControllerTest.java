package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.input.CustomerInput;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	AccountRepository accountRepository;

	@MockBean
	CustomerRepository customerRepository;

	@Test
	void testCustomerDetailPass() throws Exception {
		CustomerInput input = new CustomerInput(10001);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(input);

		Customer customer = new Customer(10001, "Pratamesh", "888888", "pratamesh@everywhere.com");
		List<Account> list = new ArrayList<>();
		list.add(new Account(100002, "salary", 30000.0, 10001, "deactive", "hyderabad"));

		when(customerRepository.findById(10001)).thenReturn(Optional.of(customer));
		when(customerRepository.findAccounts(10001)).thenReturn(list);

		mvc.perform(post("/employee/customerdetails").contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	void testCustomerDetailFail() throws Exception {
		CustomerInput input = new CustomerInput(10001);
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(input);

		List<Account> list = new ArrayList<>();
		AccountDTO accountDTO = new AccountDTO("salary", 30000.0, 10001, "hyderabad");
		list.add(new Account(10001, "salary", accountDTO.getBalance(), 10001, "deactive", "hyderabad"));

		when(customerRepository.findById(10001)).thenReturn(Optional.empty());
		when(customerRepository.findAccounts(10001)).thenReturn(list);

		mvc.perform(post("/employee/customerdetails").contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("Customer not found Exception"));

	}

	@Test
	void testCheckActiveTestSuccessActive() throws Exception {
		Account account = new Account(100000, "Savings", 10000.0, 10000, "active", "bangalore");
		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(accountRepository.updateStatus("deactive", 100000)).thenReturn(1);

		mvc.perform(post("/employee/accountstatus").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account status updated"));

	}

	@Test
	void testCheckActiveTestSuccessDeactive() throws Exception {
		Account account = new Account(100000, "Savings", 10000.0, 10000, "deactive", "bangalore");
		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(accountRepository.updateStatus("active", 100000)).thenReturn(1);

		mvc.perform(post("/employee/accountstatus").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account status updated"));

	}

	@Test
	void testCheckActiveTestFail() throws Exception {
		Account account = new Account(100000, "Savings", 10000.0, 10000, "active", "bangalore");
		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepository.findById(100000)).thenReturn(Optional.empty());
		when(accountRepository.updateStatus("deactive", 100000)).thenReturn(1);

		mvc.perform(post("/employee/accountstatus").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account not found Exception"));

	}

	@Test
	void testCreateAccountSuccess() throws Exception {
		Account account = new Account(10000, "saving", 10000.0, 12345, "active", "Bangalore");
		Customer customer = new Customer(12345, "Shriyash", "123456", "shriyash@everywhere.com");

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		when(customerRepository.findById(12345)).thenReturn(Optional.of(customer));
		mvc.perform(post("/employee/createaccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account saved successfully"));

	}

	@Test
	void testCreateAccountFail() throws Exception {
		Account account = new Account(10000, "saving", 10000.0, 12345, "active", "Bangalore");

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		when(customerRepository.findById(12345)).thenReturn(Optional.empty());
		mvc.perform(post("/employee/createaccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer not found Exception"));

	}

	@Test
	void testGetAllCustomer() throws Exception {
		List<Customer> list = new ArrayList<>();
		list.add(new Customer(12345, "Shriyash", "123456", "shriyash@everywhere.com"));
		when(customerRepository.findAll()).thenReturn(list);
		mvc.perform(get("/employee/listall")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[ {'customerId': 12345,'customerName': 'Shriyash','phoneNumber': '123456','emailId': 'shriyash@everywhere.com'}]"));
	}

	@Test
	void testSaveCutomer() throws Exception {
		Customer customer = new Customer(12345, "Shriyash", "123456", "shriyash@everywhere.com");

		ObjectMapper objectMapper = new ObjectMapper();
		String customerJson = objectMapper.writeValueAsString(customer);

		when(customerRepository.save(customer)).thenReturn(customer);
		mvc.perform(post("/employee/savecustomer").content(customerJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer save successfully"));
	}

}
