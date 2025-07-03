package com.reda;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.reda.customer.Customer;
import com.reda.customer.CustomerRepository;
import org.aspectj.weaver.ast.Var;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Random random = new Random();
            Faker faker = new Faker();
            Name name = faker.name();
            String FirstName = name.firstName();
            String LastName = name.lastName();

            Customer customer = new Customer(FirstName+" "+LastName,
                                            random.nextInt(17,70),
                            FirstName+"."+LastName+"@redondo.com");
            customerRepository.save(customer);
        };
    }

}
