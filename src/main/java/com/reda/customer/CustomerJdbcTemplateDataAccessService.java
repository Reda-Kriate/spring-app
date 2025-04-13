package com.reda.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJdbcTemplateDataAccessService implements DaoCustomerInt{

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcTemplateDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, age, email
                FROM customer
                """;

        List<Customer> customers = jdbcTemplate.query(sql,(rs,rowNum)->{
            Customer customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("email")
            );
            return customer;
        });

        return customers;
    }

    @Override
    public Optional<Customer> selectById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, age, email)
                VALUES(?, ?, ?)
                """;
        jdbcTemplate.update( sql,
                customer.getName(),
                customer.getAge(),
                customer.getEmail() );
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return false;
    }

    @Override
    public void deleteCustomer(Integer id) {

    }

    @Override
    public boolean existsCustomerWithId(Integer id) {
        return false;
    }

    @Override
    public void updateCustomerWithId(Customer update) {

    }
}
