package com.reda.customer;

import com.reda.exception.DuplicateResourceException;
import com.reda.exception.NotFoundException;
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
                .orElseThrow(()-> new NotFoundException("customer with id [%s] not found".formatted(id)));
    }
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check email exists
        if(daoCustomerInt.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("email already used!");
        }
        //add customer
        Customer customer = new Customer( customerRegistrationRequest.name(),
                                        customerRegistrationRequest.age(),
                                    customerRegistrationRequest.email() );
        daoCustomerInt.insertCustomer(customer);

    }
}
