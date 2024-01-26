package com.sametkagankeskin.productservice3.services.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sametkagankeskin.grpc.DiscountRequest;
import com.sametkagankeskin.grpc.DiscountResponse;
import com.sametkagankeskin.grpc.DiscountServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class DiscountGrpcServiceImpl implements DiscountGrpcService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountGrpcServiceImpl.class);

    private DiscountServiceGrpc.DiscountServiceBlockingStub discountServiceBlockingStub;
    private ManagedChannel channel;

    /*
     * Burada bir channel oluşturuldu.Bu channel grpc host ve grpc port'a göre
     * oluşturuldu.
     * Channel oluştururken ManagedChannelBuilder kullanıldı.Sonra bunu build
     * ediyoruz.Bir channel oluşuyor
     * Sonra bunu aşagıda newBlockingStub a veriyorum.
     */
    public DiscountGrpcServiceImpl(@Value("${discount.grpc.host}") String grpcHost,
            @Value("${discount.grpc.port}") int grpcPort) {
        try {
            channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort).usePlaintext().build();
            logger.info("---> Discount grpc: {} {}", grpcHost, grpcPort);
        } catch (Exception e) {
            logger.error("Error creating gRPC channel", e);
            // Hata durumunda gerekli işlemleri yapabilirsiniz.
        }
    }

    /*
     * newBlockingStub, yaşam döngüsü tamamlanıncaya kadar dur.Yani senkron çalışma
     * prensibi.Asenkronda ise
     * newFutureStub kullanılabilir.Buradaki başlatma işlemini bizim için yapan bir
     * dependency var.Bu dependency
     * annotation lar ile çalışıyor.Bu annotation'lar @GrpcService ve @GrpcClient
     */
    @Override
    public DiscountResponse getDiscount(DiscountRequest discountRequest) {

        discountServiceBlockingStub = DiscountServiceGrpc.newBlockingStub(channel);
        DiscountResponse discountResponse = discountServiceBlockingStub.getDiscount(discountRequest);
        return discountResponse;
    }

    /*
     * Bu servicede normal ManagedChannelBuilder ile channel ımızı oluştururken
     * Discount Service
     * de ise @GrpcService annotation ımızı kullanarak channel ımızı
     * oluşturacagız.Her ikisi de yapılabilir.
     */

}
