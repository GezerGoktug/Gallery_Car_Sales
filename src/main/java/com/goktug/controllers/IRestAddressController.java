package com.goktug.controllers;

import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoAddressIU;

public interface IRestAddressController {

    RootEntity<DtoAddress> saveAddress(DtoAddressIU address);
}
