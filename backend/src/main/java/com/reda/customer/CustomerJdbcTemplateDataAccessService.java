package com.reda.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJdbcTemplateDataAccessService implements DaoCustomerInt{

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;
    public CustomerJdbcTemplateDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, age, email, gender
                FROM customer
                """;

        return jdbcTemplate.query(sql,customerRowMapper);
    }

    @Override
    public Optional<Customer> selectById(Integer id) {
        var sql = """
                SELECT id, name, age, email, gender
                FROM customer
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper,id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, age, email, gender)
                VALUES(?, ?, ?, ?)
                """;
        jdbcTemplate.update( sql,
                customer.getName(),
                customer.getAge(),
                customer.getEmail(),
                customer.getGender());
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT COUNT(id)
                FROM customer
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomer(Integer id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;

        jdbcTemplate.update(sql,id);
        
    }

    @Override
    public boolean existsCustomerWithId(Integer id) {
        var sql = """
                SELECT COUNT(id)
                FROM customer
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void updateCustomerWithId(Customer customer) {
        if(customer.getName() != null){
            var sql = """
                    UPDATE customer SET name = ? WHERE id = ?
                    """;
            jdbcTemplate.update(sql,customer.getName(),customer.getId());
        }
        if(customer.getAge() != null){
            var sql = """
                    UPDATE customer SET age = ? WHERE id = ?
                    """;
            jdbcTemplate.update(sql,customer.getAge(),customer.getId());
        }
        if(customer.getEmail() != null){
            var sql = """
                    UPDATE customer SET email = ? WHERE id = ?
                    """;
            jdbcTemplate.update(sql,customer.getEmail(),customer.getId());
        }
        if(customer.getGender() != null){
            var sql = """
                    UPDATE customer SET gender = ? WHERE id = ?
                    """;
            jdbcTemplate.update(sql,customer.getGender(),customer.getId());
        }
    }
}
