package com.sametkagankeskin.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// gRPC servisime bağlanıyorum
@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final String bServiceBaseUrl = "http://localhost:8082/api/discounts";

    @GetMapping("")
    public ResponseEntity<String> getData(@RequestParam(required = false) String code) {
        String bServiceUrl = buildBServiceUrl(code);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(bServiceUrl, String.class);

        return ResponseEntity.ok(response.getBody());
    }

    private String buildBServiceUrl(String code) {
        StringBuilder urlBuilder = new StringBuilder(bServiceBaseUrl);
        urlBuilder.append("?productId=1");
        if (code != null && !code.isEmpty()) {
            urlBuilder.append("&code=").append(code);
        }
        return urlBuilder.toString();
    }
}
