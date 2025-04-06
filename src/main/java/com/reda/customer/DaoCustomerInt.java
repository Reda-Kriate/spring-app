package com.reda.customer;
import java.util.List;
import java.util.Optional;

public interface DaoCustomerInt {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectById(Integer id);
}
