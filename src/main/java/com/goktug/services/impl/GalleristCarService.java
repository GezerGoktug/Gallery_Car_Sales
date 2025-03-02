package com.goktug.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoCar;
import com.goktug.dto.DtoGallerist;
import com.goktug.dto.DtoGalleristCar;
import com.goktug.dto.DtoGalleristCarIU;
import com.goktug.exceptions.BaseException;
import com.goktug.exceptions.ErrorMessage;
import com.goktug.exceptions.MessageType;
import com.goktug.models.Car;
import com.goktug.models.Gallerist;
import com.goktug.models.GalleristCar;
import com.goktug.repositories.CarRepository;
import com.goktug.repositories.GalleristCarRepository;
import com.goktug.repositories.GalleristRepository;
import com.goktug.services.IGalleristCarService;

@Service
public class GalleristCarService implements IGalleristCarService {

    @Autowired
    private GalleristCarRepository galleristCarRepository;

    @Autowired
    private GalleristRepository galleristRepository;

    @Autowired
    private CarRepository carRepository;

    private GalleristCar createGalleristCar(DtoGalleristCarIU galleristCar) {
        Optional<Car> optCar = carRepository.findById(galleristCar.getCarId());

        if (optCar.isEmpty()) {
            throw new BaseException(new ErrorMessage(galleristCar.getCarId().toString(), MessageType.NO_RECORD_EXIST),
                    404);
        }

        Optional<Gallerist> optGallerist = galleristRepository.findById(galleristCar.getGalleristId());

        if (optGallerist.isEmpty()) {
            throw new BaseException(
                    new ErrorMessage(galleristCar.getGalleristId().toString(), MessageType.NO_RECORD_EXIST),
                    404);
        }

        GalleristCar newGalleristCar = new GalleristCar();

        newGalleristCar.setCreatedAt(new Date());

        newGalleristCar.setGallerist(optGallerist.get());
        newGalleristCar.setCar(optCar.get());

        return newGalleristCar;

    }

    @Override
    public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU galleristCar) {
        GalleristCar dbGalleristCar = galleristCarRepository.save(createGalleristCar(galleristCar));

        DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
        DtoCar dtoCar = new DtoCar();
        DtoGallerist dtoGallerist = new DtoGallerist();
        DtoAddress dtoAddress = new DtoAddress();

        BeanUtils.copyProperties(dbGalleristCar, dtoGalleristCar);
        BeanUtils.copyProperties(dbGalleristCar.getCar(), dtoCar);
        BeanUtils.copyProperties(dbGalleristCar.getGallerist(), dtoGallerist);
        BeanUtils.copyProperties(dbGalleristCar.getGallerist().getAddress(), dtoAddress);

        dtoGalleristCar.setCar(dtoCar);
        dtoGalleristCar.setGallerist(dtoGallerist);
        dtoGalleristCar.getGallerist().setAddress(dtoAddress);

        return dtoGalleristCar;
    }
}
