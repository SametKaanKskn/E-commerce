package com.sametkagankeskin.ecommerce.exception;

public class StockAmountNotEnoughException extends RuntimeException {
    public StockAmountNotEnoughException(String message) {
        super(message);
    }

}
