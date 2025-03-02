package com.goktug.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoGallerist;
import com.goktug.dto.DtoGalleristIU;
import com.goktug.exceptions.BaseException;
import com.goktug.exceptions.ErrorMessage;
import com.goktug.exceptions.MessageType;
import com.goktug.models.Address;
import com.goktug.models.Gallerist;
import com.goktug.repositories.AddressRepository;
import com.goktug.repositories.GalleristRepository;
import com.goktug.services.IGalleristService;

@Service
public class GalleristService implements IGalleristService {

    @Autowired
    private GalleristRepository galleristRepository;

    @Autowired
    private AddressRepository addressRepository;

    private Gallerist createGallerist(DtoGalleristIU gallerist){

        Optional<Address> optAddress = addressRepository.findById(gallerist.getAddressId());

        if(optAddress.isEmpty()){
            throw new BaseException(new ErrorMessage(gallerist.getAddressId().toString(),MessageType.NO_RECORD_EXIST) ,404);
        }

        Gallerist newGallerist = new Gallerist();

        newGallerist.setCreatedAt(new Date());
        newGallerist.setAddress(optAddress.get());

        BeanUtils.copyProperties(gallerist, newGallerist);
        
        return newGallerist;

    }

    @Override
    public DtoGallerist saveGallerist(DtoGalleristIU gallerist) {

        Gallerist dbGallerist = galleristRepository.save(createGallerist(gallerist));

        DtoGallerist dtoGallerist = new DtoGallerist();
        DtoAddress dtoAddress = new DtoAddress();

        BeanUtils.copyProperties(dbGallerist, dtoGallerist);
        BeanUtils.copyProperties(dbGallerist.getAddress(), dtoAddress);

        dtoGallerist.setAddress(dtoAddress);

        return dtoGallerist;
    }
}
