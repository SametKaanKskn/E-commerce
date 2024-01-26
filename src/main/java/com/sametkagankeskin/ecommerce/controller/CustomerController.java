package com.sametkagankeskin.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

import com.sametkagankeskin.ecommerce.model.dto.CustomerDto;
import com.sametkagankeskin.ecommerce.model.vm.AddCustomerVm;
import com.sametkagankeskin.ecommerce.model.vm.UpdatePasswordVm;
import com.sametkagankeskin.ecommerce.model.vm.UpdateWalletVm;
import com.sametkagankeskin.ecommerce.service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/customers/")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CustomerDto getByCustomerId(@PathVariable("customerId") int customerId) {
        return customerService.getByCustomerId(customerId);
    }

    @PostMapping(value = "add")
    public int addNewCustomer(@Valid @RequestBody AddCustomerVm customerVm) {
        return customerService.addNewCustomer(customerVm);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteByCustomerId(@PathVariable("customerId") int customerId) {
        return customerService.deleteByCustomerId(customerId);
    }

    @PutMapping(value = "/updateWallet")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String updateWalletByCustomerId(@Valid @RequestBody UpdateWalletVm walletVm) {
        return customerService.updateWallet(walletVm);
    }

    @PutMapping(value = "/changePassword")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String updatePasswordByCustomerId(@Valid @RequestBody UpdatePasswordVm passwordVm) {
        return customerService.updatePassword(passwordVm);
    }
}
