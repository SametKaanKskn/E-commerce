package com.sametkagankeskin.ecommerce.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sametkagankeskin.ecommerce.model.vm.AddPaymentTypeVm;
import com.sametkagankeskin.ecommerce.model.vm.GetPaymentTypeVm;
import com.sametkagankeskin.ecommerce.service.PaymentTypeService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments/")
public class PaymentTypeController {

    private PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping("")
    List<GetPaymentTypeVm> getAllPaymentTypeVm() {
        return paymentTypeService.getAllPaymentTypeVm();
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public int addPaymentType(@Valid @RequestBody AddPaymentTypeVm addPaymentTypeVm) {
        return paymentTypeService.addPaymentType(addPaymentTypeVm);
    }
}
