package com.BKHOSTEL.BKHOSTEL;

import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableMongoRepositories
public class BkhostelApplication {
	@Value("${server.port}")
	private String port;
	UserRepository userRepository;
	MongoTemplate mongoTemplate;

	@Autowired
	public BkhostelApplication(UserRepository userRepository, MongoTemplate mongoTemplate) {
		this.userRepository = userRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public static void main(String[] args) {

		SpringApplication.run(BkhostelApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(String[] args){
		return runner ->{
			System.out.println("Running on port " + port);
			try {
				Set<String> collectionNames = mongoTemplate.getCollectionNames();
//				System.out.println(collectionNames.toString());
				System.out.println("Connected to MongoDB");
//				List<User> userList=userRepository.findAll();
//				userList.stream().forEach(user ->System.out.println(user.getUserName()));
			} catch (Exception e) {
				System.err.println("Error connecting to MongoDB: " + e.getMessage());
			}
		};
	}

}
