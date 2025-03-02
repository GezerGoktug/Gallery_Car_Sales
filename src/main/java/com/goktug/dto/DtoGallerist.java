package com.goktug.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoGallerist extends BaseDto {

    private String firstName;

    private String lastName;

    private DtoAddress address;
}
