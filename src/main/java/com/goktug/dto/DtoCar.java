package com.goktug.dto;

import java.math.BigDecimal;

import com.goktug.enums.CarStatus;
import com.goktug.enums.CurrencyType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCar extends BaseDto {

    private String numberPlate;

    private String brand;

    private String model;

    private String productionYear;

    private BigDecimal price;

    private CurrencyType currencyType;

    private BigDecimal damagePrice;

    private CarStatus carStatus;
}
