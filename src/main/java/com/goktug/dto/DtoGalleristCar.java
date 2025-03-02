package com.goktug.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoGalleristCar extends BaseDto {

    private DtoGallerist gallerist;

    private DtoCar car;
}
