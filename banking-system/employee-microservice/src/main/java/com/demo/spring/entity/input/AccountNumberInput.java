package com.demo.spring.entity.input;

public class AccountNumberInput {
	
	private Integer accountNumber;
	
	public AccountNumberInput() {
	}

	public AccountNumberInput(Integer accountNumber) {
		super();
		this.accountNumber = accountNumber;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

}
