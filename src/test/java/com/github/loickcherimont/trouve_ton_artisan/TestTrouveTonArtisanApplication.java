package com.github.loickcherimont.trouve_ton_artisan;

import org.springframework.boot.SpringApplication;

public class TestTrouveTonArtisanApplication {

	public static void main(String[] args) {
		SpringApplication.from(TrouveTonArtisanApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
