package com.sametkagankeskin.ecommerce.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeDto {

    @NotNull
    private int paymentId;

    @NotBlank
    @Size(max = 100)
    private String paymentTypeName;
}
