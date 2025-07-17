package com.reda.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJpaDataAccessServiceTest {

    private CustomerJpaDataAccessService underTest;
    @Mock private CustomerRepository customerRepository;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJpaDataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //WHEN
        underTest.selectAllCustomers();
        //THEN
        verify(customerRepository).findAll();
    }

    @Test
    void selectById() {
        //GIVEN
        int id = 1;

        //WHEN
        underTest.selectById(id);

        //THEN
        verify(customerRepository).findById(id);

    }

    @Test
    void insertCustomer() {
        //GIVEN
        Customer customer = new Customer();
        //WHEN
        underTest.insertCustomer(customer);

        //THEN
        verify(customerRepository).save(customer);

    }

    @Test
    void existsCustomerWithEmail() {
        //GIVEN
        String email = "email@Test-Mockito";

        //WHEN
        underTest.existsCustomerWithEmail(email);

        //THEN
        verify(customerRepository).existsByEmail(email);

    }

    @Test
    void deleteCustomer() {
        //GIVEN
        int id = 1;
        //WHEN
        underTest.deleteCustomer(id);
        //THEN
        verify(customerRepository).deleteById(id);

    }

    @Test
    void existsCustomerWithId() {
        //GIVEN
        int id = 1;

        //WHEN
        underTest.existsCustomerWithId(id);

        //THEN
        verify(customerRepository).existsById(id);
    }

    @Test
    void updateCustomerWithId() {
        //GIVEN
        Customer customer = new Customer();

        //WHEN
        underTest.updateCustomerWithId(customer);

        //THEN
        verify(customerRepository).save(customer);
    }
}