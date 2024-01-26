package com.sametkagankeskin.ecommerce.model.vm;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductsInCartVm {

    @NotNull
    private int customerId;
    @NotNull
    private int productId;
    private int quantity;

}
