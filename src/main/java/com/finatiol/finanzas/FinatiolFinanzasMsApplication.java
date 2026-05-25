package com.finatiol.finanzas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinatiolFinanzasMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinatiolFinanzasMsApplication.class, args);
	}

}
