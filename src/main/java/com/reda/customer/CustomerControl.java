package com.reda.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerControl {
    private final CustomerService customerSrv;

    public CustomerControl(CustomerService customerSrv) {
        this.customerSrv = customerSrv;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerSrv.getAllCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomers(@PathVariable("id") Integer id){
        return customerSrv.getCustomersById(id);
    }

    @GetMapping
    public void registerCustomer( @RequestBody CustomerRegistrationRequest request){
        customerSrv.addCustomer(request);
    }
}
