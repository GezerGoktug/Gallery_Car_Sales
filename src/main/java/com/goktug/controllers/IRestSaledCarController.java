package com.goktug.controllers;

import com.goktug.dto.DtoSaledCar;
import com.goktug.dto.DtoSaledCarIU;

public interface IRestSaledCarController {

    RootEntity<DtoSaledCar> buyCar(DtoSaledCarIU saledCar);
}
