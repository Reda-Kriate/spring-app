package com.reda.journeyControler;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.reda.customer.Customer;
import com.reda.customer.CustomerRegistrationRequest;
import com.reda.customer.CustomerUpdateRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerControlTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canRegisterACustomer(){
        Faker faker = new Faker();
        Random random = new Random();
        String name = faker.name().fullName();
        int age = random.nextInt(1,60);
        String email = name +"-"+ UUID.randomUUID()+"@gmailTest.com";
        //create registration request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,age,email
        );
        //send a post request
        webTestClient.post()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request) , CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>(){})
                .returnResult()
                .getResponseBody();
        //make sure that customer present
        Customer expect = new Customer(
                name,age,email
        );
        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expect);
        //get customer by id
        int id = allCustomers.stream()
                .filter(c->c.getEmail().equals(email))
                .map(c->c.getId())
                .findFirst()
                .orElseThrow();
        expect.setId(id);

        webTestClient.get()
                .uri("/api/v1/customer/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>(){})
                .isEqualTo(expect);
    }
    @Test
    void canDeleteACustomer(){
        Faker faker = new Faker();
        Random random = new Random();
        String name = faker.name().fullName();
        int age = random.nextInt(1,60);
        String email = name +"-"+ UUID.randomUUID()+"@gmailTest.com";
        //create registration request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,age,email
        );
        //send a post request
        webTestClient.post()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request) , CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>(){})
                .returnResult()
                .getResponseBody();

        //get customer by id
        int id = allCustomers.stream()
                .filter(c->c.getEmail().equals(email))
                .map(c->c.getId())
                .findFirst()
                .orElseThrow();

        webTestClient.delete()
                .uri("/api/v1/customer/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri("/api/v1/customer/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateACustomer(){
        Faker faker = new Faker();
        Random random = new Random();
        String name = faker.name().fullName();
        int age = random.nextInt(1,60);
        String email = name +"-"+ UUID.randomUUID()+"@gmailTest.com";
        //create registration request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,age,email
        );
        //send a post request
        webTestClient.post()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request) , CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>(){})
                .returnResult()
                .getResponseBody();

        //get customer by id
        int id = allCustomers.stream()
                .filter(c->c.getEmail().equals(email))
                .map(c->c.getId())
                .findFirst()
                .orElseThrow();
        String newName = "newReda";
        CustomerUpdateRegistration update = new CustomerUpdateRegistration(
                newName , null , null
        );

        webTestClient.put()
                .uri("/api/v1/customer/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerUpdateRegistration.class)
                .exchange()
                .expectStatus()
                .isOk();

        Customer updaterCust =  webTestClient.get()
                .uri("/api/v1/customer/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();
        Customer expected = new Customer(
                id,newName,age,email
        );
        assertThat(updaterCust).isEqualTo(expected);
    }
}
