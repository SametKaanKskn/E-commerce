package com.sametkagankeskin.ecommerce.service;

import java.util.List;

import com.sametkagankeskin.ecommerce.model.dto.CustomerDto;
import com.sametkagankeskin.ecommerce.model.vm.AddCustomerVm;
import com.sametkagankeskin.ecommerce.model.vm.UpdatePasswordVm;
import com.sametkagankeskin.ecommerce.model.vm.UpdateWalletVm;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getByCustomerId(int customerId);

    int addNewCustomer(AddCustomerVm customerVm);

    String deleteByCustomerId(int customerId);

    boolean existsById(int customerId);

    String updatePassword(UpdatePasswordVm passwordVm);

    String updateWallet(UpdateWalletVm walletVm);
}
