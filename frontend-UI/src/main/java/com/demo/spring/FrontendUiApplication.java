package com.demo.spring;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FrontendUiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(FrontendUiApplication.class, args);
	}
	
	@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

       return restTemplate;
    }
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
       
        registry.addViewController("/openAccount").setViewName("openAccount");
        registry.addViewController("/checkBalance").setViewName("checkBalance");
        registry.addViewController("/withdraw").setViewName("withdrawInput");
        registry.addViewController("/deposit").setViewName("depositInput");
        registry.addViewController("/transfer").setViewName("transferInput");
        registry.addViewController("/changeStatus").setViewName("accountStatus");
        registry.addViewController("/statement").setViewName("statementPage");
        registry.addViewController("/openAccountByEmployee").setViewName("openAccountByEmployee");
        registry.addViewController("/listcustomer").setViewName("customerList");
        registry.addViewController("/details").setViewName("customerDetailsInput");
        registry.addViewController("/customerhome").setViewName("customerHome");
        registry.addViewController("/employeehome").setViewName("employeeHome");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("registration");
        registry.addViewController("/customerHome").setViewName("customerHome");
        
    }
}
