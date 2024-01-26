package com.sametkagankeskin.productservice3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sametkagankeskin.grpc.DiscountResponse;
import com.sametkagankeskin.productservice3.model.dto.DiscountResponseDTO;
import com.sametkagankeskin.productservice3.services.DiscountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public ResponseEntity<DiscountResponseDTO> getDiscount(String code, int productId) {
        DiscountResponse discountResponse = discountService.getDiscount(productId, code);
        return ResponseEntity.ok(
                DiscountResponseDTO.builder()
                        .newPrice(discountResponse.getNewPrice())
                        .oldPrice(discountResponse.getOldPrice())
                        .code(discountResponse.getCode())
                        .build());

    }

}
