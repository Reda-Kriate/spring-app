package com.reda;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.reda.customer.Customer;
import com.reda.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository,
                             PasswordEncoder passwordEncoder){
        return args -> {
            Random random = new Random();
            Faker faker = new Faker();
            Name name = faker.name();
            String FirstName = name.firstName();
            String LastName = name.lastName();

            Customer customer = new Customer(FirstName+" "+LastName,
                                            random.nextInt(17,70),
                    FirstName+"."+LastName+"@hbib.com",
                    passwordEncoder.encode(UUID.randomUUID().toString()),
                    "Male");
            customerRepository.save(customer);
        };
    }

}
