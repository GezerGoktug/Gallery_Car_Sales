package com.goktug.services.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoAddressIU;
import com.goktug.models.Address;
import com.goktug.repositories.AddressRepository;
import com.goktug.services.IAddressService;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    private Address createAddress(DtoAddressIU address) {
        Address newAddress = new Address();
        newAddress.setCreatedAt(new Date());

        BeanUtils.copyProperties(address, newAddress);

        return newAddress;
    }

    public DtoAddress saveAddress(DtoAddressIU address) {
        Address dbAddress = addressRepository.save(createAddress(address));

        DtoAddress dtoAddress = new DtoAddress();

        BeanUtils.copyProperties(dbAddress, dtoAddress);

        return dtoAddress;
    }
}
