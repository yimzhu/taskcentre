package com.qualintech.taskcentre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yimzhu
 */
@SpringBootApplication
@MapperScan(value = "com.qualintech.taskcentre.mapper")
public class StatemachineDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatemachineDemoApplication.class, args);
	}

}
