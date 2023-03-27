package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Statement;

public interface StatementRepository extends JpaRepository<Statement, Integer> {
	
	@Query("select s from Statement s where s.accountNumber=:accountNumber and s.date>=:fromDate and s.date<=:toDate")
	public List<Statement> getStatement(Integer accountNumber, String fromDate,String toDate);

}
