package com.sametkagankeskin.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sametkagankeskin.ecommerce.model.dto.PaymentTypeDto;
import com.sametkagankeskin.ecommerce.model.entity.PaymentType;
import com.sametkagankeskin.ecommerce.model.vm.AddPaymentTypeVm;
import com.sametkagankeskin.ecommerce.model.vm.GetPaymentTypeVm;
import com.sametkagankeskin.ecommerce.repository.PaymentTypeRepository;
import com.sametkagankeskin.ecommerce.service.PaymentTypeService;
import com.sametkagankeskin.ecommerce.util.mapper.ModelMapperManager;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private PaymentTypeRepository paymentTypeRepository;
    private ModelMapperManager modelMapperManager;

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository, ModelMapperManager modelMapperManager) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.modelMapperManager = modelMapperManager;
    }

    @Override
    public List<GetPaymentTypeVm> getAllPaymentTypeVm() {
        List<PaymentType> paymentTypes = paymentTypeRepository.findAll();

        List<PaymentTypeDto> modelsResponse = paymentTypes.stream()
                .map(payment -> this.modelMapperManager.forResponse()
                        .map(payment, PaymentTypeDto.class))
                .collect(Collectors.toList());

        List<GetPaymentTypeVm> getAllPaymentTypeVms = modelsResponse.stream()
                .map(paymentDto -> this.modelMapperManager.forResponse()
                        .map(paymentDto, GetPaymentTypeVm.class))
                .collect(Collectors.toList());

        return getAllPaymentTypeVms;
    }

    @Override
    public int addPaymentType(AddPaymentTypeVm addPaymentTypeVm) {
        PaymentTypeDto paymentTypeDto = modelMapperManager.forRequest().map(addPaymentTypeVm, PaymentTypeDto.class);
        PaymentType paymentType = modelMapperManager.forRequest().map(paymentTypeDto, PaymentType.class);
        paymentTypeRepository.save(paymentType);
        return paymentType.getPaymentId();
    }

}
