package com.riozenc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.riozenc.titanTool.mongo.MongoPoolFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.riozenc.api",exclude = MongoAutoConfiguration.class)
@EnableScheduling
public class  InapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(InapiApplication.class, args);
			}
}
