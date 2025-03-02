package com.goktug.services;

import com.goktug.dto.DtoGalleristCar;
import com.goktug.dto.DtoGalleristCarIU;

public interface IGalleristCarService {

    DtoGalleristCar saveGalleristCar(DtoGalleristCarIU galleristCar);
}
