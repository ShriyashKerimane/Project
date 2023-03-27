package com.demo.spring.dto;

public class Account {
	
	private Integer accountNumber;
	private String accountType;
	private Double balance = 0.0;
	private Integer customerId;
	private String status = "active";
	private String branch;
	
	public Account() {
	}

	public Account(Integer accountNumber,String accountType, Double balance, Integer customerId, String status,
			String branch) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.customerId = customerId;
		this.status = status;
		this.branch = branch;
	}
	
	public Account(String accountType, Double balance, Integer customerId, String branch) {
		super();
		this.accountType = accountType;
		this.balance = balance;
		this.customerId = customerId;
		this.branch = branch;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
}
