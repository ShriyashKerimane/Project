package com.demo.spring.entity.input;

public class CustomerInput {
	
	private Integer customerId;
	
	public CustomerInput() {
	}

	public CustomerInput(Integer customerId) {
		super();
		this.customerId = customerId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
