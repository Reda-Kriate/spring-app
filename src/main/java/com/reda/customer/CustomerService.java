package com.reda.customer;

import com.reda.exception.NotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final DaoCustomerInt daoCustomerInt;


    public CustomerService(@Qualifier("jpa") DaoCustomerInt daoCustomerInt) {
        this.daoCustomerInt = daoCustomerInt;
    }
    public List<Customer> getAllCustomers(){
        return daoCustomerInt.selectAllCustomers();
    }
    public Customer getCustomersById(Integer id){
        return daoCustomerInt.selectById(id)
                .orElseThrow(()-> new NotFound("customer with id [%s] not found".formatted(id)));
    }
}
