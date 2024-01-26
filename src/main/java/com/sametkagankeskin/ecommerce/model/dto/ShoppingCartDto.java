package com.sametkagankeskin.ecommerce.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDto {

    @NotNull
    private int cartId;
    @NotNull
    private int customerId;
    @NotNull
    private int productId;
    private int quantity;
    private double totalAmount;
}
