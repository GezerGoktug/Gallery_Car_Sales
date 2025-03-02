package com.goktug.controllers;

import com.goktug.dto.DtoCar;
import com.goktug.dto.DtoCarIU;

public interface IRestCarController {

    RootEntity<DtoCar> saveCar(DtoCarIU car);
}
