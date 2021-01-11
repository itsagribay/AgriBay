package com.agribay.agribayapp;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agribay.agribayapp.model.Item;
import com.agribay.agribayapp.model.ItemCategory;
import com.agribay.agribayapp.model.Product;
import com.agribay.agribayapp.model.Unit;
import com.agribay.agribayapp.model.User;
import com.agribay.agribayapp.repository.ItemRepository;
import com.agribay.agribayapp.repository.ProductRepository;
import com.agribay.agribayapp.repository.UserRepository;

@Configuration
class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, ItemRepository itemRepository,
			ProductRepository productRepository) {

		return args -> {
			User user1 = new User();
			User user2 = new User();
			user1.setId((long) 1);
			user1.setUsername("Bilbo Baggins");
			user2.setId((long) 2);
			user2.setUsername("Frodo Baggins");
			log.info("Preloading " + userRepository.save(user1));
			log.info("Preloading " + userRepository.save(user2));

			Item item1 = new Item((long) 3, "tomato", ItemCategory.vegetables, Unit.kg, "");
			Item item2 = new Item((long) 4, "banana", ItemCategory.fruits, Unit.dozen, "");
			log.info("Preloading " + itemRepository.save(item1));
			log.info("Preloading " + itemRepository.save(item2));

			Product product1 = new Product((long) 5, user1, "", new BigDecimal("20"), new BigDecimal("4"), "", "",
					"some desc here", item1);
			Product product2 = new Product((long) 6, user2, "", new BigDecimal("30"), new BigDecimal("3"), "", "",
					"some desc here", item2);
			log.info("Preloading " + productRepository.save(product1));
			log.info("Preloading " + productRepository.save(product2));
		};
	}
}
