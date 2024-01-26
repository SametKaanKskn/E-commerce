package com.sametkagankeskin.productservice3.services;

import org.springframework.stereotype.Service;

import com.sametkagankeskin.grpc.DiscountRequest;
import com.sametkagankeskin.grpc.DiscountResponse;
import com.sametkagankeskin.productservice3.model.Product;
import com.sametkagankeskin.productservice3.services.grpc.DiscountGrpcService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountGrpcService discountGrpcService;
    private final ProductService productService;

    public DiscountResponse getDiscount(int productId, String code) {
        Product product = productService.getById(productId);
        DiscountRequest discountRequest = DiscountRequest.newBuilder()
                .setCode(code)
                .setPrice(product.getPrice().floatValue())
                .setExternalCategoryId(product.getCategory().getId())
                .build();

        return discountGrpcService.getDiscount(discountRequest);
    }

}
