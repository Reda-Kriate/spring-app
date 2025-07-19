package com.reda.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{
    boolean existsByEmail(String email);
    @Override
    boolean existsById(Integer integer);
    Optional<Customer> findCustomerByEmail(String email);
}
