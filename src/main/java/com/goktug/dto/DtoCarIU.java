package com.goktug.dto;

import java.math.BigDecimal;

import com.goktug.enums.CarStatus;
import com.goktug.enums.CurrencyType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCarIU {

    @NotNull
    private String numberPlate;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    private String productionYear;
    @NotNull
    private BigDecimal price;
    @NotNull
    private CurrencyType currencyType;
    @NotNull
    private BigDecimal damagePrice;
    @NotNull
    private CarStatus carStatus;
}
