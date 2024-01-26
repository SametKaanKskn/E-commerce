package com.sametkagankeskin.ecommerce.service;

import java.util.List;

import com.sametkagankeskin.ecommerce.model.vm.AddPaymentTypeVm;
import com.sametkagankeskin.ecommerce.model.vm.GetPaymentTypeVm;

public interface PaymentTypeService {
    List<GetPaymentTypeVm> getAllPaymentTypeVm();

    int addPaymentType(AddPaymentTypeVm addPaymentTypeVm);
}
