package com.xpos.mtdzlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages =  {"com.xpos.mtdzlog"})
@EnableJpaRepositories(basePackages = {"com.xpos.mtdzlog"})
@EntityScan(basePackages =  {"com.xpos.mtdzlog"})
@MapperScan(basePackages =  {"com.xpos.mtdzlog"})
@EnableFeignClients
@EnableScheduling
public class MtdzlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtdzlogApplication.class, args);
	}

}
