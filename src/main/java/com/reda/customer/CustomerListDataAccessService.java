package com.reda.customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements DaoCustomerInt{

        private static final List<Customer> customers;
        static {
        customers = new ArrayList<Customer>();
        Customer reda = new Customer(1,"Reda",22,"reda@gmail.com");
        customers.add(reda);
        Customer abdo = new Customer(2,"Abdo",23,"abdo@gmail.com");
        customers.add(abdo);
        }

    public CustomerListDataAccessService() {
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectById(Integer id) {
        return customers.stream()
                .filter( cus -> cus.getId().equals(id))
                .findFirst();



    }
}
