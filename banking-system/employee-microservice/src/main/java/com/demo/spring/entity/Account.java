package com.demo.spring.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNTS")
public class Account {
	
	@Id
    @SequenceGenerator(sequenceName = "account_sequence",initialValue = 100003,allocationSize = 1, name = "account_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_generator")
    @Column(name="ACCOUNTNUMBER")
	private Integer accountNumber;
	
	@Column(name="ACCOUNTTYPE")
	private String accountType;
	
	@Column(name="BALANCE")
	private Double balance = 0.0;
	
	@Column(name="CUSTOMERID")
	private Integer customerId;
	
	@Column(name="STATUS")
	private String status = "active";
	
	@Column(name="BRANCH")
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
