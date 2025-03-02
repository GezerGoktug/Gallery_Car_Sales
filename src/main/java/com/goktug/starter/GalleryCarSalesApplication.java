package com.goktug.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "com.goktug" })
@ComponentScan(basePackages = { "com.goktug" })
@EnableJpaRepositories(basePackages = { "com.goktug" })
@SpringBootApplication
public class GalleryCarSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalleryCarSalesApplication.class, args);
	}

}
