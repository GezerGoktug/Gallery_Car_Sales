package com.goktug.services;

import com.goktug.dto.DtoCar;
import com.goktug.dto.DtoCarIU;

public interface ICarService {

    DtoCar saveCar(DtoCarIU car);
}
