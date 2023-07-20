package com.tudux.kafkamicros.auditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class AuditingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditingApplication.class, args);
	}

}
