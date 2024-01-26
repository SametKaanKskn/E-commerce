package com.sametkagankeskin.productservice3.services.grpc;

import com.sametkagankeskin.grpc.DiscountRequest;
import com.sametkagankeskin.grpc.DiscountResponse;

public interface DiscountGrpcService {

    DiscountResponse getDiscount(DiscountRequest discountRequest);
}
