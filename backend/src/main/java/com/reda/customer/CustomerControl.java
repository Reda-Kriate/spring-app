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
    public List<Customer> getCustomersByID(){
        return customerSrv.getAllCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomersByID(@PathVariable("id") Integer id){
        return customerSrv.getCustomersById(id);
    }

    @PostMapping
    public void registerCustomer( @RequestBody CustomerRegistrationRequest request){
        customerSrv.addCustomer(request);
    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable("id") Integer id){
        customerSrv.deleteCustomer(id);
    }

    @PutMapping("{id}")
    public void updateCustomer(@PathVariable("id") Integer id ,@RequestBody CustomerUpdateRegistration update){
        customerSrv.updateById(id,update);
    }
}
