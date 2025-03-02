package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestGalleristCarController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoGalleristCar;
import com.goktug.dto.DtoGalleristCarIU;
import com.goktug.services.IGalleristCarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/galleristCar")
public class RestGalleristCarController extends RestBaseController implements IRestGalleristCarController {

    @Autowired
    private IGalleristCarService galleristCarService;

    @PostMapping("/add")
    @Override
    public RootEntity<DtoGalleristCar> saveGalleristCar(@Valid @RequestBody DtoGalleristCarIU galleristCar) {
        return ok(galleristCarService.saveGalleristCar(galleristCar), 200);
    }
}
