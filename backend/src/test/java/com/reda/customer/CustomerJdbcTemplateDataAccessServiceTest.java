package com.reda.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.reda.customer.testContConfig.AbstractTestcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
        List<Customer> actuals = underTest.selectAllCustomers();
        //THEN
        assertThat(actuals).isNotEmpty();
    }

    @Test
    void selectById() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                20,
                email
        );
        underTest.insertCustomer(customer);
        //WHEN
        int id = underTest.selectAllCustomers()
                        .stream().filter(c -> c.getEmail().equals(email))
                        .map(c -> c.getId())
                        .findFirst().orElseThrow();
        Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying(c ->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
                });
    }

    @Test
    void willReturnEmptySelectCustomerById(){
        //GIVEN
        int id = -1;
        //WHEN
        Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isEmpty();
    }
    @Test
    void insertCustomer() {
        //GIVEN
        String name = FAKER.name().fullName();
        Customer customer = new Customer(
                name,
                21,
                FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID()
        );
        //WHEN
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getName().equals(name)).
                map(c -> c.getId())
                .findFirst().orElseThrow();

       Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying(c ->{
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void existsCustomerWithEmail() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                22,
                email
        );
        underTest.insertCustomer(customer);
        //WHEN
        boolean emailExists = underTest.existsCustomerWithEmail(email);
        //THEN
        assertThat(emailExists).isTrue();
    }

    @Test
    void willReturnFalseExistsCustomerWithEmail(){
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                22,
                email
        );
        underTest.insertCustomer(customer);
        //WHEN
        boolean emailExists = underTest.existsCustomerWithEmail(FAKER.internet().safeEmailAddress());
        //THEN
        assertThat(emailExists).isFalse();
    }

    @Test
    void deleteCustomer() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                22,
                email
        );
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId()).findFirst().orElseThrow();
        //WHEN
        underTest.deleteCustomer(id);
        //THEN
        boolean existIdAfterDelete = underTest.existsCustomerWithId(id);
        assertThat(existIdAfterDelete).isFalse();
    }

    @Test
    void willReturnFalseExistsCustomerWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                22,
                email
        );
        underTest.insertCustomer(customer);
        //WHEN
        boolean idExists = underTest.existsCustomerWithId(-1);
        //THEN
        assertThat(idExists).isFalse();
    }

    @Test
    void existsCustomerWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                22,
                email
        );
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId()).findFirst().orElseThrow();
        //WHEN
        boolean idExists = underTest.existsCustomerWithId(id);
        //THEN
        assertThat(idExists).isTrue();
    }

    @Test
    void updateCustomerNameWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                21,
                email
        );
        //WHEN
        underTest.insertCustomer(customer);
        System.out.println(customer.toString());
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId()).findFirst().orElseThrow();

        String newName = "Reda";
        Customer customerUpdate = new Customer();
        customerUpdate.setId(id);
        customerUpdate.setName(newName);

        underTest.updateCustomerWithId(customerUpdate);
        Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }
    @Test
    void updateCustomerAgeWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                21,
                email
        );
        //WHEN
        underTest.insertCustomer(customer);
        System.out.println(customer.toString());
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId()).findFirst().orElseThrow();

        Integer newAge = 40;
        Customer customerUpdate = new Customer();
        customerUpdate.setId(id);
        customerUpdate.setAge(newAge);

        underTest.updateCustomerWithId(customerUpdate);
        Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }
    @Test
    void updateCustomerEmailWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                21,
                email
        );
        //WHEN
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId()).findFirst().orElseThrow();

        String newEmail = FAKER.internet().safeEmailAddress();
        Customer customerUpdate = new Customer();
        customerUpdate.setId(id);
        customerUpdate.setEmail(newEmail);

        underTest.updateCustomerWithId(customerUpdate);
        Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }
    @Test
    void updateFullCustomerWithId() {
        //GIVEN
        String email = FAKER.internet().safeEmailAddress()+"-"+UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                21,
                email
        );
        //WHEN
        underTest.insertCustomer(customer);
        System.out.println(customer.toString());
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId()).findFirst().orElseThrow();
        String newName = "Reda";
        Integer newAge = 40;
        String newEmail = FAKER.internet().safeEmailAddress();
        Customer customerUpdate = new Customer();
        customerUpdate.setId(id);
        customerUpdate.setName(newName);
        customerUpdate.setAge(newAge);
        customerUpdate.setEmail(newEmail);

        underTest.updateCustomerWithId(customerUpdate);
        Optional<Customer> actual = underTest.selectById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying( c -> {
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }

}