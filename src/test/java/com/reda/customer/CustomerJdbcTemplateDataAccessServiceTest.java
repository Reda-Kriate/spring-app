package com.reda.customer;

import net.bytebuddy.NamingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reda.AbstractTestcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class CustomerJdbcTemplateDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJdbcTemplateDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJdbcTemplateDataAccessService(
                getJdbcTemplate(),
                customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        //GIVEN
        Customer customer = new Customer(
                FAKER.name().fullName(),
                20,
                FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID()
        );
        underTest.insertCustomer(customer);
        //WHEN
        List<Customer> customers = underTest.selectAllCustomers();
        //THEN
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectById() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void insertCustomer() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void existsCustomerWithEmail() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void deleteCustomer() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void existsCustomerWithId() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void updateCustomerWithId() {
        //GIVEN

        //WHEN

        //THEN
    }
}