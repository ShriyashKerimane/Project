package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Statement;
import com.demo.spring.entity.input.AccountNumberInput;
import com.demo.spring.entity.input.StatementInput;
import com.demo.spring.entity.input.TransactionInput;
import com.demo.spring.entity.input.TransferInput;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.exception.LowBalanceException;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.repository.StatementRepository;
import com.demo.spring.util.Message;

@Service
public class CustomerService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	StatementRepository statementRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	Notification notification;

	private static final String ACCOUNTNOTFOUND = "Account {} not found";
	private static final String CUSTOMERNOTFOUND = "Customer {} not found";
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Message create(Account account) {
		Optional<Customer> customer = customerRepository.findById(account.getCustomerId());
		if (customer.isPresent()) { 
			accountRepository.save(account);
			logger.info("Account created with accountNumber  {}", account.getAccountNumber());
			Message mail = new Message(
					"Your Account opened successfully...your new account number is " + account.getAccountNumber());
			notification.sendEmail(customer.get().getEmailId(), mail);
			return new Message("Account saved successfully");
		} else {
			logger.error("CustomerId not found {}", account.getCustomerId());
			throw new CustomerNotFoundException(); 
		}
	}

	public Message deposit(TransactionInput input) {
		Optional<Account> account = accountRepository.findById(input.getAccountNumber());
		Optional<Customer> customer = customerRepository.findById(input.getCustomerId());
		if(customer.isPresent()) {
			if (account.isPresent() && account.get().getCustomerId().equals(input.getCustomerId())) {
				Double updatedBalance = account.get().getBalance() + input.getAmount();
				accountRepository.updateBalance(updatedBalance, input.getAccountNumber());
				Statement statement = new Statement(input.getAccountNumber(), "deposit", input.getAmount(), updatedBalance,
						input.getDate());
				statementRepository.save(statement);
				logger.info("Amount deposited successfully to account number {}", account.get().getAccountNumber());
				Message mail = new Message(
						input.getAmount() + " credited to account number " + account.get().getAccountNumber()
								+ " ...available balance in your account is "+updatedBalance);
				notification.sendEmail(customer.get().getEmailId(), mail);
				return new Message("Balance in account is " + updatedBalance);
			} else {
				logger.error(ACCOUNTNOTFOUND, input.getAccountNumber());		
				throw new AccountNotFoundException();
			}
		}else {
			logger.error(CUSTOMERNOTFOUND, input.getCustomerId());		
			throw new CustomerNotFoundException();
		}
	}

	public Message withdraw(TransactionInput input) {
		Optional<Account> account = accountRepository.findById(input.getAccountNumber());
		Optional<Customer> customer = customerRepository.findById(input.getCustomerId());
		if(customer.isPresent()) {
			if (account.isPresent() && account.get().getCustomerId().equals(input.getCustomerId())) {
				if (account.get().getBalance() < input.getAmount()) {
					logger.error("Low Balance to withdraw");
					throw new LowBalanceException();
				} else {
					Double updatedBalance = account.get().getBalance() - input.getAmount();
					accountRepository.updateBalance(updatedBalance, input.getAccountNumber());
					Statement statement = new Statement(input.getAccountNumber(), "withdraw", input.getAmount(),
							updatedBalance, input.getDate());
					statementRepository.save(statement);
					logger.info("Amount withdraw of {} successfull", input.getAmount());
					Message mail = new Message(
							input.getAmount() + " debited from account number " + account.get().getAccountNumber()
									+ " ...available balance in your account is "+updatedBalance);
					notification.sendEmail(customer.get().getEmailId(), mail);
					return new Message("Balance in Account is "+updatedBalance);
				}
			} else {
				logger.error(ACCOUNTNOTFOUND, input.getAccountNumber());
				throw new AccountNotFoundException();
			}
		}else {
			logger.error(CUSTOMERNOTFOUND, input.getCustomerId());		
			throw new CustomerNotFoundException();
		}
	}

	public Message transfer(TransferInput input) {
		Optional<Account> fromAccount = accountRepository.findById(input.getFromAccountNumber());
		Optional<Account> toAccount = accountRepository.findById(input.getToAccountNumber());
		Optional<Customer> customer = customerRepository.findById(input.getCustomerId());
		if(customer.isPresent()) {
			if (toAccount.isPresent()) {
				if (fromAccount.isPresent() && fromAccount.get().getCustomerId().equals(input.getCustomerId())) {
					if (fromAccount.get().getBalance() < input.getAmount()) {
						logger.error("Low Balance to transfer amount");
						throw new LowBalanceException();
					} else {
						Double senderBalance = fromAccount.get().getBalance() - input.getAmount();
						Double receiverBalance = toAccount.get().getBalance() + input.getAmount();
						accountRepository.updateBalance(senderBalance, input.getFromAccountNumber());
						accountRepository.updateBalance(receiverBalance, input.getToAccountNumber());
						Statement statement1 = new Statement(input.getFromAccountNumber(), "withdraw", input.getAmount(),
								senderBalance, input.getDate());
						Statement statement2 = new Statement(input.getToAccountNumber(), "deposit", input.getAmount(),
								receiverBalance, input.getDate());
						statementRepository.save(statement1);
						statementRepository.save(statement2);
						logger.info("Transfer of amount {} successfull", input.getAmount());
						return new Message("Balance in account is "+senderBalance);
					}
	
				} else {
					logger.error("Sender Account {} not found", input.getToAccountNumber());
					throw new AccountNotFoundException();
				}
			} else {
				logger.error("Receiver Account {} not found", input.getFromAccountNumber());
				throw new AccountNotFoundException();
			}
		}else {
			logger.error(CUSTOMERNOTFOUND, input.getCustomerId());		
			throw new CustomerNotFoundException();
		}
	}

	public Message fetchBalance(AccountNumberInput input) {
		Optional<Customer> customer = customerRepository.findById(input.getCustomerId());
		Optional<Account> account = accountRepository.findById(input.getAccountNumber());
		if(customer.isPresent()) {
			if (account.isPresent() && account.get().getCustomerId().equals(input.getCustomerId())) {
				logger.info("Balance in account {} is {}", input.getAccountNumber(), account.get().getBalance());
				return new Message("Balance in account is"+account.get().getBalance());
			} else {
				logger.error(ACCOUNTNOTFOUND, input.getAccountNumber());
				throw new AccountNotFoundException();
			}
		}else {
			logger.error(CUSTOMERNOTFOUND, input.getCustomerId());		
			throw new CustomerNotFoundException();
		}
	}

	public List<Statement> statement(StatementInput statementInput) {
		Optional<Customer> customer = customerRepository.findById(statementInput.getCustomerId());
		Optional<Account> accountOp = accountRepository.findById(statementInput.getAccountNumber());
		if(customer.isPresent()) {
			if (accountOp.isPresent() && accountOp.get().getCustomerId().equals(statementInput.getCustomerId())) {
				logger.info("Statement generated successfully");
				return (statementRepository.getStatement(statementInput.getAccountNumber(), statementInput.getFromDate(),
						statementInput.getToDate()));
			} else {
				logger.error(ACCOUNTNOTFOUND, statementInput.getAccountNumber());
				throw new AccountNotFoundException();
			}
		}else {
			logger.error(CUSTOMERNOTFOUND, statementInput.getCustomerId());		
			throw new CustomerNotFoundException();
		}
	}
}
