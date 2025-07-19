package com.reda.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final DaoCustomerInt daoCustomerInt;

    public CustomerUserDetailsService(@Qualifier("jpa") DaoCustomerInt daoCustomerInt) {
        this.daoCustomerInt = daoCustomerInt;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return daoCustomerInt.selectUserByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username " + " not Found"));
    }
}
