package com.goktug.services.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.DtoCar;
import com.goktug.dto.DtoCarIU;
import com.goktug.models.Car;
import com.goktug.repositories.CarRepository;
import com.goktug.services.ICarService;

@Service
public class CarService implements ICarService {

    @Autowired
    private CarRepository carRepository;

    private Car createCar(DtoCarIU car) {

        Car newCar = new Car();

        newCar.setCreatedAt(new Date());

        BeanUtils.copyProperties(car, newCar);

        return newCar;
    }

    @Override
    public DtoCar saveCar(DtoCarIU car) {
        Car dbCar = carRepository.save(createCar(car));

        DtoCar dtoCar = new DtoCar();

        BeanUtils.copyProperties(dbCar, dtoCar);

        return dtoCar;
    }

}
