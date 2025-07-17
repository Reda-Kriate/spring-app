package com.reda.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import com.reda.customer.testContConfig.AbstractTestcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
//        underTest.deleteAll();
        System.out.println("bean count : " + applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsByEmail() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                20,
                email,
                "male"
        );
        underTest.save(customer);
        //WHEN
        boolean actual = underTest.existsByEmail(email);
        //THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsByEmailWhenReturnFalse() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        //WHEN
        boolean actual = underTest.existsByEmail(email);
        //THEN
        assertThat(actual).isFalse();
    }

    @Test
    void existsById() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                20,
                email,
                "female"
        );
        underTest.save(customer);
        //WHEN
        int id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId())
                .findFirst().orElseThrow();
        boolean actual = underTest.existsById(id);
        //THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsByIdWhenReturnFalse() {
        //GIVEN
        int id = -1;
        //WHEN
        boolean actual = underTest.existsById(id);
        //THEN
        assertThat(actual).isFalse();
    }
}