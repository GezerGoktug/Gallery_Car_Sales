package com.goktug.controllers;

import com.goktug.dto.DtoGalleristCar;
import com.goktug.dto.DtoGalleristCarIU;

public interface IRestGalleristCarController {

    RootEntity<DtoGalleristCar> saveGalleristCar(DtoGalleristCarIU galleristCar);
}
