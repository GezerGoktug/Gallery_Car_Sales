package com.goktug.services;

import com.goktug.dto.DtoSaledCar;
import com.goktug.dto.DtoSaledCarIU;

public interface ISaledCarService {

    DtoSaledCar buyCar(DtoSaledCarIU saledCar);
}
