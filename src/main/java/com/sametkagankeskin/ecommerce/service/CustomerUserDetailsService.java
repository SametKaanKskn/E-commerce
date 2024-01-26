package com.sametkagankeskin.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sametkagankeskin.ecommerce.config.CustomerUserDetails;
import com.sametkagankeskin.ecommerce.model.entity.Customer;
import com.sametkagankeskin.ecommerce.repository.CustomerRepository;

@Component
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        return customer.map(CustomerUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

}
