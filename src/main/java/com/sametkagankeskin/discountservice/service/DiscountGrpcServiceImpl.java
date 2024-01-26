package com.sametkagankeskin.discountservice.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.sametkagankeskin.discountservice.entity.Category;
import com.sametkagankeskin.discountservice.entity.Discount;
import com.sametkagankeskin.discountservice.repository.CategoryRepository;
import com.sametkagankeskin.discountservice.repository.DiscountRepository;
import com.sametkagankeskin.grpc.DiscountRequest;
import com.sametkagankeskin.grpc.DiscountResponse;

import com.sametkagankeskin.grpc.DiscountServiceGrpc;
import com.sametkagankeskin.grpc.Response;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class DiscountGrpcServiceImpl extends DiscountServiceGrpc.DiscountServiceImplBase {

        private final DiscountRepository discountRepository;
        private final CategoryRepository categoryRepository;

        @Override
        public void getDiscount(DiscountRequest request, StreamObserver<DiscountResponse> responseObserver) {
                Category category = categoryRepository.findByExternalId(String.valueOf(request.getExternalCategoryId()))
                                .orElseThrow(() -> new RuntimeException(
                                                "Category has not been found by external category ID"));

                Optional<Discount> discount = discountRepository.findByCodeAndCategoryId(request.getCode(),
                                category.getId());

                if (discount.isPresent()) {
                        BigDecimal newPrice = discount.get().getDiscountPrice()
                                        .subtract(BigDecimal.valueOf(request.getPrice()))
                                        .multiply(BigDecimal.valueOf(-1));
                        responseObserver.onNext(
                                        DiscountResponse.newBuilder()
                                                        .setCode(discount.get().getCode())
                                                        .setOldPrice(request.getPrice())
                                                        .setNewPrice(newPrice.floatValue())
                                                        .setResponse(
                                                                        Response.newBuilder().setStatusCode(true)
                                                                                        .setMessage("Discount has been applied successfully!")
                                                                                        .build())
                                                        .build());
                } else {
                        responseObserver.onNext(DiscountResponse.newBuilder()
                                        .setOldPrice(request.getPrice())
                                        .setNewPrice(request.getPrice())
                                        .setCode(request.getCode())
                                        .setResponse(Response.newBuilder().setMessage("Code and category are invalid.")
                                                        .setStatusCode(false).build())
                                        .build());

                        /*
                         * Burada bunu yazarsak onCompleted dedigimizde kanalı kapatmış ve ardından
                         * aşagıda super ile yeniden çağırıp
                         * else durumunda kanalı yeniden kapatmaya çalışıyor olacagız bu sebeple bunu
                         * else içerisine yazmamız gerekli
                         */
                        // responseObserver.onCompleted();

                }

                responseObserver.onCompleted();

        }

}
