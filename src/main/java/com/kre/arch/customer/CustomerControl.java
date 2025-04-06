package com.kre.arch.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerControl {
    private final CustomerService customerSrv;

    public CustomerControl(CustomerService customerSrv) {
        this.customerSrv = customerSrv;
    }

    @GetMapping("/api/v1/customer")
    public List<Customer> getCustomers(){
        return customerSrv.getAllCustomers();
    }

    @GetMapping("/api/v1/customer/{id}")
    public Customer getCustomers(@PathVariable("id") Integer id){
        return customerSrv.getCustomersById(id);
    }
}
