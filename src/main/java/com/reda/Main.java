package com.reda;

import com.reda.customer.Customer;
import com.reda.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Customer reda = new Customer("Reda",22,"reda@gmail.com");
            Customer abdo = new Customer("Abdo",23,"abdo@gmail.com");
            List<Customer> customers = List.of(reda,abdo);
            customerRepository.saveAll(customers);
        };
    }

}
