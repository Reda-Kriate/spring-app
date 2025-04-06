package com.reda.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{
    boolean existsCustomerEmail(String email);
}
