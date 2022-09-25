package com.kadli.starmony;

import org.hibernate.dialect.Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Types;

@SpringBootApplication
public class StarmonyApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(StarmonyApplication.class, args);
	}
}

