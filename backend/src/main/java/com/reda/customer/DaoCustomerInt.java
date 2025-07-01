package com.reda.customer;
import java.util.List;
import java.util.Optional;

public interface DaoCustomerInt {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectById(Integer id);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    void deleteCustomer(Integer id);
    boolean existsCustomerWithId(Integer id);
    void updateCustomerWithId(Customer update);
}
