package com.demo.spring.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ServerConfiguration {

	String employeeServer="http://localhost:8282";

	public String getEmployeeServer() {
		return employeeServer;
	}

	public void setEmployeeServer(String employeeServer) {
		this.employeeServer = employeeServer;
	}
	
}
