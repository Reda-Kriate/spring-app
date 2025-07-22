package com.reda.journeyControler;

import com.github.javafaker.Faker;
import com.reda.customer.Customer;
import com.reda.customer.CustomerDTO;
import com.reda.customer.CustomerRegistrationRequest;
import com.reda.customer.CustomerUpdateRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Test
    void canRegisterACustomer(){
        Faker faker = new Faker();
        Random random = new Random();
        String name = faker.name().fullName();
        int age = random.nextInt(1,60);
        String email = name +"-"+ UUID.randomUUID()+"@gmailTest.com";
        String gender = "male";
        //create registration request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,age,email, "password", gender
        );
        //send a post request et exctraction de Token
        String jwtToken = webTestClient.post()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        //get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,String.format("Bearer %s",jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>(){})
                .returnResult()
                .getResponseBody();

        //get customer by id
        int id = allCustomers.stream()
                .filter(c->c.email().equals(email))
                .map(c->c.id())
                .findFirst()
                .orElseThrow();
        //make sure that customer present
        CustomerDTO expect = new CustomerDTO(
                id,
                name,
                age,
                email,
                gender,
                List.of("ROLE_USER"),
                email
        );
        assertThat(allCustomers)
                .contains(expect);

        webTestClient.get()
                .uri("/api/v1/customer/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,String.format("Bearer %s",jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerDTO>(){})
                .isEqualTo(expect);
    }
    @Test
    void canDeleteACustomer(){
        Faker faker = new Faker();
        Random random = new Random();
        String name = faker.name().fullName();
        int age = random.nextInt(1,60);
        String email = name +"-"+ UUID.randomUUID()+"@gmailTest.com";
        String gender = "male";
        //create registration request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,age,email, "password", gender
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
        String gender = "male";
        //create registration request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,age,email, "password", gender
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
                newName , null , null,null
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
                id,newName,age,email, "password", gender
        );
        assertThat(updaterCust).isEqualTo(expected);
    }
}
