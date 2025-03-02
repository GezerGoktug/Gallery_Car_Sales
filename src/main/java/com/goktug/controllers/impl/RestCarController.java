package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestCarController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoCar;
import com.goktug.dto.DtoCarIU;
import com.goktug.services.ICarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/car")
public class RestCarController extends RestBaseController implements IRestCarController {

    @Autowired
    private ICarService carService;

    @PostMapping("/add")
    @Override
    public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarIU car) {

        return ok(carService.saveCar(car), 200);
    }

}
