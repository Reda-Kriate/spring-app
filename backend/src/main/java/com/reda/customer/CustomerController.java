package com.reda.customer;

import com.reda.JWT.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerSrv;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerSrv, JWTUtil jwtUtil) {
        this.customerSrv = customerSrv;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<CustomerDTO> getCustomersByID(){
        return customerSrv.getAllCustomers();
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomersByID(@PathVariable("id") Integer id){
        return customerSrv.getCustomersById(id);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerSrv.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(),"ROLE_USER");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwtToken).build();
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
