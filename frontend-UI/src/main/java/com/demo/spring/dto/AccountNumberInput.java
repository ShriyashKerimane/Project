package com.demo.spring.dto;

public class AccountNumberInput {
	
	private Integer customerId = 0;
	private Integer accountNumber;
	
	public AccountNumberInput() {
	}

	public AccountNumberInput(Integer customerId, Integer accountNumber) {
		super();
		this.customerId = customerId;
		this.accountNumber = accountNumber;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

}
