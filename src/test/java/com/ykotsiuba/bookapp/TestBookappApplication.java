package com.ykotsiuba.bookapp;

import org.springframework.boot.SpringApplication;

public class TestBookappApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookappApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
