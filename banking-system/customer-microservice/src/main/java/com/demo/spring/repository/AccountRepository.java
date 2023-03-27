package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.entity.Account;

public interface AccountRepository extends Repository<Account, Integer> {
	
	@Query("update Account a set a.balance=:balance where a.accountNumber=:accountNumber")
	@Modifying
	@Transactional
	public Integer updateBalance(Double balance, Integer accountNumber);
	
}
