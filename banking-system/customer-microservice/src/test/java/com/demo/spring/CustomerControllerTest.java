package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.demo.spring.entity.Statement;
import com.demo.spring.entity.input.StatementInput;
import com.demo.spring.entity.input.TransactionInput;
import com.demo.spring.entity.input.TransferInput;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.repository.StatementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	AccountRepository accountRepository;

	@MockBean
	CustomerRepository customerRepository;
	
	@MockBean
	StatementRepository statementRepository;

	@Test
	void testCreateAccountPass() throws Exception {
		Account account = new Account(10000, "savings", 10000.0, 12345, "active", "Bangalore");
		Customer customer = new Customer(12345, "Shriyash", "123456", "shriyash@everywhere.com");

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		when(customerRepository.findById(12345)).thenReturn(Optional.of(customer));
		mvc.perform(post("/customer/createaccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
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
		mvc.perform(post("/customer/createaccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));
	}

	@Test
	void testDepositAmountSuccess() throws Exception {
		Account account = new Account("Savings", 20000.0, 1000, "bangalore");
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");

		TransactionInput input = new TransactionInput(1000,100000, 10000.0, "20221017");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(input);
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.updateBalance(20000.0, 100000)).thenReturn(1);
		mvc.perform(patch("/customer/deposit").contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance in account is "+30000.0));
	}

	@Test
	void testDepositAmountFail() throws Exception {
		Account account = new Account(100000,"Savings", 20000.0, 10000, "active", "bangalore");
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		TransactionInput input = new TransactionInput(1000,100000, 10000.0, "20221017");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(input);
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(110000)).thenReturn(Optional.of(account));
		when(accountRepository.updateBalance(20000.0, 110000)).thenReturn(1);
		mvc.perform(patch("/customer/deposit").contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account not found"));
	}
	
	@Test
	void testDepositCustomerFail() throws Exception {
		Account account = new Account(100000,"Savings", 20000.0, 10000, "active", "bangalore");
		TransactionInput input = new TransactionInput(1000,100000, 10000.0, "20221017");
		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(input);
		when(customerRepository.findById(1000)).thenReturn(Optional.empty());
		when(accountRepository.findById(110000)).thenReturn(Optional.of(account));
		when(accountRepository.updateBalance(20000.0, 110000)).thenReturn(1);
		mvc.perform(patch("/customer/deposit").contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));
	}

	@Test
	void testDepositSuccess() throws Exception {
		TransactionInput input = new TransactionInput(1000,100000, 1000.0, "20221016");
		Account account = new Account(100000,"Savings", 20000.0, 1000, "active", "bangalore");
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");


		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(input);

		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(accountRepository.updateBalance(20000.0, 100000)).thenReturn(1);
		mvc.perform(patch("/customer/deposit").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance in account is "+21000.0));
	}

	@Test
	void testWithdrawsuccess() throws Exception {

		TransactionInput input = new TransactionInput(1000,100000, 1000.0, "20221016");
		Account account = new Account(100000,"Savings", 20000.0, 1000, "active", "bangalore");
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");

		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(input);

		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(accountRepository.updateBalance(20000.0, 100000)).thenReturn(1);
		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance in Account is "+19000.0));
	}
	
	@Test
	void testWithdrawFail() throws Exception {

		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		TransactionInput input = new TransactionInput(1000,100000, 1000.0, "20221016");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(input);

		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(100000)).thenReturn(Optional.empty());
		when(accountRepository.updateBalance(20000.0, 100000)).thenReturn(1);
		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account not found"));	
		}
	
	@Test
	void testWithdrawCustomerFail() throws Exception {

		Account account = new Account(100000,"Savings", 20000.0, 1000, "active", "bangalore");
		TransactionInput input = new TransactionInput(1000,100000, 1000.0, "20221016");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(input);

		when(customerRepository.findById(1000)).thenReturn(Optional.empty());
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(accountRepository.updateBalance(20000.0, 100000)).thenReturn(1);
		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));	
		}
	
	@Test
	void testWithdrawLowBalance() throws Exception {

		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		Account account = new Account(100000,"Savings", 10.0, 1000, "active", "bangalore");
		TransactionInput input = new TransactionInput(1000,100000, 1000.0, "20221016");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(input);
		
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		when(accountRepository.updateBalance(20000.0, 100000)).thenReturn(1);
		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Low Balance"));
	}
	
	@Test
    void testTransferPass() throws Exception{
        
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		TransferInput input = new TransferInput(1000,100000, 100001, 1000.0, "20221016");        
		Account sender = new Account(100000,"Savings", 20000.0, 1000, "active", "bangalore");
		Account receiver = new Account(100001,"Savings", 5000.0, 12345, "active", "hubli");
        
        ObjectMapper mapper = new ObjectMapper();
        
        String transactionJson = mapper.writeValueAsString(input);
        
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(100000)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(100001)).thenReturn(Optional.of(receiver));
        when(accountRepository.updateBalance(19000.0,100000)).thenReturn(1);
        when(accountRepository.updateBalance(6000.0,100001)).thenReturn(1);
        mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.status").value("Balance in account is "+19000.0));
    }
	
	@Test
    void testTransferFailForCustomer() throws Exception{
        
		TransferInput input = new TransferInput(1000,100000, 100001, 1000.0, "20221016");        
		Account sender = new Account(100000,"Savings", 20000.0, 12345, "active", "bangalore");
		Account receiver = new Account(100001,"Savings", 5000.0, 12345, "active", "hubli");
        
        ObjectMapper mapper = new ObjectMapper();
        
        String transactionJson = mapper.writeValueAsString(input);
        
		when(customerRepository.findById(1000)).thenReturn(Optional.empty());
        when(accountRepository.findById(100000)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(100001)).thenReturn(Optional.of(receiver));
        when(accountRepository.updateBalance(19000.0,100000)).thenReturn(1);
        when(accountRepository.updateBalance(6000.0,100001)).thenReturn(1);
        mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.status").value("Customer Not Found"));
    }
    
    @Test
    void testTransferFailForSender() throws Exception{
        
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
    	TransferInput input = new TransferInput(1000,10000, 100001, 1000.0, "20221016");		
		Account receiver = new Account(100001,"Savings", 5000.0, 12345, "active", "hubli");
        
        ObjectMapper mapper = new ObjectMapper();
        
        String transactionJson = mapper.writeValueAsString(input);
        
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(100000)).thenReturn(Optional.empty());
        when(accountRepository.findById(100001)).thenReturn(Optional.of(receiver));
        when(accountRepository.updateBalance(19000.0,100000)).thenReturn(1);
        when(accountRepository.updateBalance(6000.0,100001)).thenReturn(1);
        mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.status").value("Account not found"));
    }
	
    @Test
    void testTransferFailForSenderLowBalance() throws Exception{
    	
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
    	TransferInput input = new TransferInput(1000,100000, 100001, 1000.0, "20221016");		
    	Account sender = new Account(100000,"Savings", 10.0, 1000, "active", "bangalore");
    	Account receiver = new Account(100001,"Savings", 5000.0, 12345, "active", "hubli");
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	String transactionJson = mapper.writeValueAsString(input);
    	
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
    	when(accountRepository.findById(100000)).thenReturn(Optional.of(sender));
    	when(accountRepository.findById(100001)).thenReturn(Optional.of(receiver));
    	when(accountRepository.updateBalance(19000.0,100000)).thenReturn(1);
    	when(accountRepository.updateBalance(6000.0,100001)).thenReturn(1);
    	mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
    	.andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    	.andExpect(jsonPath("$.status").value("Low Balance"));
    }

	@Test
	void testGetBalanceSuccess() throws Exception {
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		Account account = new Account(100000,"Savings", 10000.0, 1000, "active", "bangalore");

		ObjectMapper mapper = new ObjectMapper();
		String accountJson = mapper.writeValueAsString(account);
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		mvc.perform(post("/customer/checkbalance").content(accountJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance in account is"+10000.0));

	}
	
	@Test
	void testGetBalanceFailForCustomer() throws Exception {
		Account account = new Account(100000,"Savings", 10000.0, 1000, "active", "bangalore");

		ObjectMapper mapper = new ObjectMapper();
		String accountJson = mapper.writeValueAsString(account);
		when(customerRepository.findById(1000)).thenReturn(Optional.empty());
		when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
		mvc.perform(post("/customer/checkbalance").content(accountJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));

	}

	@Test
	void testGetBalanceFail() throws Exception {
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		Account account = new Account(100000,"Savings", 10000.0, 1000, "active", "bangalore");

		ObjectMapper mapper = new ObjectMapper();
		String empJson = mapper.writeValueAsString(account);
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		when(accountRepository.findById(100000)).thenReturn(Optional.empty());
		mvc.perform(post("/customer/checkbalance").content(empJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account not found"));
	}
	
	
	
	
	
	@Test
    void testFetchStatementPass() throws Exception {
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		Account account = new Account(100000,"Savings", 10000.0, 1000, "active", "bangalore");
		StatementInput input = new StatementInput(1000,100000,"20221019","20221021");
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(input);
		
        List<Statement> list = new ArrayList<>();
        list.add(new Statement(101 ,100000, "withdraw", 1000.0, 10000.0, "20221020"));
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
        when(statementRepository.getStatement(100000,"20221019","20221021")).thenReturn(list);
        mvc.perform(post("/customer/statement").content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
                        "[ {'accountNumber': 100000,'transactionType': 'withdraw','amount': 1000.0,'balance': 10000.0,'date':'20221020'}]"));
  }
	
	@Test
    void testFetchStatementFailForCustomer() throws Exception {
		Account account = new Account(100000,"Savings", 10000.0, 10000, "active", "bangalore");
		StatementInput input = new StatementInput(1000,100000,"20221019","20221021");
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(input);
		
        List<Statement> list = new ArrayList<>();
        list.add(new Statement(101 ,100000, "withdraw", 1000.0, 10000.0, "20221020"));
		when(customerRepository.findById(1000)).thenReturn(Optional.empty());
        when(accountRepository.findById(100000)).thenReturn(Optional.of(account));
        when(statementRepository.getStatement(100000,"20221019","20221021")).thenReturn(list);
        mvc.perform(post("/customer/statement").content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("Customer Not Found"));
  }
	
	@Test
    void testFetchStatementFail() throws Exception {
		Customer customer = new Customer(1000, "Shriyash", "98765", "sk@everywhere.com");
		StatementInput input = new StatementInput(1000,100000,"20221019","20221021");
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(input);
		
        List<Statement> list = new ArrayList<>();
        list.add(new Statement(101 ,100000, "withdraw", 1000.0, 10000.0, "20221020"));
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(100000)).thenReturn(Optional.empty());
        when(statementRepository.getStatement(100000,"20221019","20221021")).thenReturn(list);
        mvc.perform(post("/customer/statement").content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("Account not found"));
  }
}
