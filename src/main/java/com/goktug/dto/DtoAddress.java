package com.goktug.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAddress extends BaseDto {

    private String city;

    private String district;

    private String neighborhood;

    private String street;

}
