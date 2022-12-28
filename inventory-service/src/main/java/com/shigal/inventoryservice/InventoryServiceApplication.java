package com.shigal.inventoryservice;

import com.shigal.inventoryservice.model.Inventory;
import com.shigal.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("vivo_17_black");
			inventory.setQuantity(120);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("vivo_19_blue");
			inventory1.setQuantity(80);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("vivo_19_white");
			inventory2.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}
}
