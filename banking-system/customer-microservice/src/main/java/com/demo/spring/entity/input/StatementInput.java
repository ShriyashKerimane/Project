package com.demo.spring.entity.input;

public class StatementInput {
	
	private Integer customerId;
	private Integer accountNumber;
	private String fromDate;
	private String toDate;
	
	public StatementInput() {
	}

	public StatementInput(Integer customerId, Integer accountNumber, String fromDate, String toDate) {
		super();
		this.customerId = customerId;
		this.accountNumber = accountNumber;
		this.fromDate = fromDate;
		this.toDate = toDate;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}
