package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="STATEMENT")
public class Statement {
	
	@Id 
	@SequenceGenerator(sequenceName = "transactionid_sequence",initialValue = 4, allocationSize =1, name = "transactionid_generator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "transactionid_generator")
	@Column(name="TRANSACTIONID")
	private Integer transactionId;
	
	@Column(name="ACCOUNTNUMBER")
	private Integer accountNumber;
	
	@Column(name="TRANSACTIONTYPE")
	private String transactionType;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@Column(name="BALANCE")
	private Double balance;
	
	@Column(name="DATE")
	private String date;
	
	public Statement() {
	}
	
	

	public Statement(Integer transactionId, Integer accountNumber, String transactionType, Double amount,
			Double balance, String date) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.balance = balance;
		this.date = date;
	}



	public Statement(Integer accountNumber, String transactionType, Double amount, Double balance, String date) {
		super();
		this.accountNumber = accountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.balance = balance;
		this.date = date;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
