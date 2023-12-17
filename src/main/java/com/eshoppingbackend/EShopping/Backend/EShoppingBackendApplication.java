package com.eshoppingbackend.EShopping.Backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(info = @Info(title = "Amazon Shopping Website Backend", version = "1.0", description = "it contains all the api", contact = @Contact(name = "Arjun", url = "https://github.com/arjun-chand/E-shopping-Backend", email = "chandarjun10@gmail.com")))
@SpringBootApplication
public class EShoppingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShoppingBackendApplication.class, args);
	}

}


