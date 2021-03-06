package com.njry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.njry.mapper","com.njry.mapper.*"})
public class TzweixinApplication {

	public static void main(String[] args) {
		SpringApplication.run(TzweixinApplication.class, args);
	}

}
