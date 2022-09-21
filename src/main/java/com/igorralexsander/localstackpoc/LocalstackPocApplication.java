package com.igorralexsander.localstackpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@SpringBootApplication
public class LocalstackPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalstackPocApplication.class, args);
	}

	@Bean
	public S3Client s3AsyncClient(){
		return S3Client.builder()
				.region(Region.US_EAST_1)
				.endpointOverride(URI.create("http://localhost:4566"))
				.build();
	}
}
