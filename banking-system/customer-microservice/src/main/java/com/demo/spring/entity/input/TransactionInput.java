package com.demo.spring.entity.input;

public class TransactionInput {
	
	private Integer customerId;
	private Integer accountNumber;
	private Double amount;
	private String date;
	
	public TransactionInput() {
	}

	public TransactionInput(Integer customerId, Integer accountNumber, Double amount, String date) {
		super();
		this.customerId = customerId;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.date = date;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
