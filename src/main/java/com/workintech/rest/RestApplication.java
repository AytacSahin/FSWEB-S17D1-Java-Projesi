package com.workintech.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// @ işareti ile tag'lediğimiz değerlere annotation diyoruz.
// @SpringBootApplication annotation'ı bizi boiler plate kodlardan kurtarır.

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

}
