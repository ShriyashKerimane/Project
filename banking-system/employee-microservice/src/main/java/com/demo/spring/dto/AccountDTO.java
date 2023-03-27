package com.demo.spring.dto;

public class AccountDTO {
	
	private String accountType;
	private Double balance=0.0;
	private Integer customerId;
	private String branch;
	
	public AccountDTO() {
	}

	public AccountDTO(String accountType, Double balance, Integer customerId, String branch) {
		super();

		this.accountType = accountType;
		this.balance = balance;
		this.customerId=customerId;
		this.branch = branch;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
}
