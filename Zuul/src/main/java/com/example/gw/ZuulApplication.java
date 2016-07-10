package com.example.gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
@SpringBootApplication
@EnableAutoConfiguration
@EnableZuulProxy
public class ZuulApplication {

	@RequestMapping(value = "/available")
	public String available() {
		return "Spring in Action";
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

}