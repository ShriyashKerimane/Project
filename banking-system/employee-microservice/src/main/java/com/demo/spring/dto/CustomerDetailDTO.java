package com.demo.spring.dto;

import java.util.List;

import com.demo.spring.entity.Account;

public class CustomerDetailDTO {
	
	private Integer customerId;
	private String customerName;
	private String phoneNumber;
	private String emailId;
	private List<Account> accounts;
	
	public CustomerDetailDTO() {
	}

	public CustomerDetailDTO(Integer customerId, String customerName, String phoneNumber, String emailId,
			List<Account> accounts) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.accounts = accounts;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
}
