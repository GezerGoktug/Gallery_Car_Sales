package com.goktug.exceptions;

import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXIST("1001", "Not found anything record"),
    JWT_TOKEN_EXPIRED_DATE("1005", "Jwt token expired date"),
    USERNAME_NOT_FOUND("1006", "Username not found"),
    USERNAME_OR_PASSWORD_INVALID("1007", "Username or password invalid"),
    REFRESH_TOKEN_NOT_FOUND("1008", "Refresh token not found"),
    REFRESH_TOKEN_EXPIRED_DATE("1009", "Refresh token expired date"),
    CURRENCY_RATES_IS_OCCURED("1010", "Could not be get currency rate"),
    CUSTOMER_AMOUNT_IS_NOT_ENOUGH("1011", "Customer amount is not enough"),
    CAR_STATUS_IS_ALREADY_SALED("1012", "Car already saled"),
    GENERAL_ERROR("9999", "Internal server error");

    private String code;

    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
