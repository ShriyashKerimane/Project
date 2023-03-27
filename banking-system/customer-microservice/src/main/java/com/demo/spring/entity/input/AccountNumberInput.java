package com.demo.spring.entity.input;

public class AccountNumberInput {
	
	private Integer customerId;
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
