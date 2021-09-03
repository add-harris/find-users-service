package com.example.findusersservice;

import com.example.findusersservice.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class FindUsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindUsersServiceApplication.class, args);
	}

}
