package com.goktug.services;

import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoAddressIU;

public interface IAddressService {

    DtoAddress saveAddress(DtoAddressIU address);
}
