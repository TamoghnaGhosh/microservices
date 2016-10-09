package com.bookstore.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "com.bookstore", entityManagerFactoryRef = "bookStoreEntityManagerFactory")
public class MysqlConfig {

	@Bean(name = "bookstore")
	@ConfigurationProperties(prefix = "datasource.bookstore")
	public DataSource bookStoreDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean bookStoreEntityManagerFactory(

	EntityManagerFactoryBuilder builder) {

		return builder
		.dataSource(bookStoreDataSource())
		.packages("com.bookstore")
		.persistenceUnit("bookStoreEntityManager")
		.build();

	}

	@Bean
	public EntityManager bookStoreEntityManager(
			EntityManagerFactory bookStoreEntityManagerFactory) {

		return bookStoreEntityManagerFactory.createEntityManager();
	}
}