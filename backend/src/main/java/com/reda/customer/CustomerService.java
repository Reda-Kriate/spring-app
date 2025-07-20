package com.reda.customer;

import com.reda.exception.DuplicateResourceException;
import com.reda.exception.NotFoundException;
import com.reda.exception.RequestValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final DaoCustomerInt daoCustomerInt;
    private final PasswordEncoder passwordEncoder;

    public final String variable = "jdbc";
    public CustomerService(@Qualifier(variable) DaoCustomerInt daoCustomerInt , PasswordEncoder passwordEncoder) {
        this.daoCustomerInt = daoCustomerInt;
        this.passwordEncoder = passwordEncoder;
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
                                    customerRegistrationRequest.email(),
                                    passwordEncoder.encode(customerRegistrationRequest.password()),
                                    customerRegistrationRequest.gender());
        daoCustomerInt.insertCustomer(customer);
    }

    public void deleteCustomer(Integer id){
        if(!daoCustomerInt.existsCustomerWithId(id)){
            throw new NotFoundException("ID [%s] not found !".formatted(id));
        }
        daoCustomerInt.deleteCustomer(id);
    }

    public void updateById(Integer id,CustomerUpdateRegistration update){
        boolean changes = false;
        Customer customer = getCustomersById(id);

        if(update.name() != null && !update.name().equals(customer.getName())){
            customer.setName(update.name());
            daoCustomerInt.updateCustomerWithId(customer);
            changes = true;
        }
        if(update.age() != null && !update.age().equals(customer.getAge())){
            customer.setAge(update.age());
            daoCustomerInt.updateCustomerWithId(customer);
            changes = true;
        }
        if(update.gender() != null && !update.gender().equals(customer.getGender())){
            customer.setGender(update.gender());
            daoCustomerInt.updateCustomerWithId(customer);
            changes = true;
        }
        if(update.email() != null && !update.email().equals(customer.getEmail())) {
            if (daoCustomerInt.existsCustomerWithEmail(update.email())) {
                throw new DuplicateResourceException("Email already taken !");
            } else {
                customer.setEmail(update.email());
                daoCustomerInt.updateCustomerWithId(customer);
                changes = true;
            }
        }
        if (!changes){
            throw new RequestValidationException("no data source changes !");
        }
    }
}
