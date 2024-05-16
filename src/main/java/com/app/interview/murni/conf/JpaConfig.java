package com.app.interview.murni.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {
				"com.app.interview.murni.model"
				})
@EnableJpaRepositories(
		repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class,
		basePackages = {
				"com.app.interview.murni.repo",
				}) 
@EnableTransactionManagement
public class JpaConfig {
}