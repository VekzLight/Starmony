package com.kadli.starmony;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.kadli.starmony.repository", repositoryImplementationPostfix = "CustomImp")
public class Config {
}
