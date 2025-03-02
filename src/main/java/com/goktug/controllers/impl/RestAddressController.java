package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestAddressController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoAddressIU;
import com.goktug.services.IAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/address")
public class RestAddressController extends RestBaseController implements IRestAddressController {
    
    @Autowired
    private IAddressService addressService;

    @PostMapping("/add")
    public RootEntity<DtoAddress> saveAddress(@Valid @RequestBody DtoAddressIU address) {
        return ok(addressService.saveAddress(address),200);
    }
}
