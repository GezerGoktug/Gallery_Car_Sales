package com.goktug.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoSaledCar extends BaseDto{
    
    private DtoCustomer customer;

    private DtoGallerist gallerist;

    private DtoCar car;
}
