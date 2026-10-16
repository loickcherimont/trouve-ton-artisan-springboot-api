package com.github.loickcherimont.trouve_ton_artisan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TrouveTonArtisanApplicationTests {

	@Test
	void contextLoads() {
	}

}
