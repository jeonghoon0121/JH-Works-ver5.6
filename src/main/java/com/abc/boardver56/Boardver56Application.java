package com.abc.boardver56;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({"com.abc.boardver56.model.dao","a"})
public class Boardver56Application {

	public static void main(String[] args) {
		SpringApplication.run(Boardver56Application.class, args);
	}

}
