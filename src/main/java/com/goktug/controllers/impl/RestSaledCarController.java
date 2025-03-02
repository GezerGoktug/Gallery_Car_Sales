package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestSaledCarController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoSaledCar;
import com.goktug.dto.DtoSaledCarIU;
import com.goktug.services.ISaledCarService;

@RestController
@RequestMapping(value = "/rest/api/saledCar")
public class RestSaledCarController extends RestBaseController implements IRestSaledCarController {

    @Autowired
    private ISaledCarService saledCarService;

    @PostMapping("/add")
    @Override
    public RootEntity<DtoSaledCar> buyCar(@RequestBody DtoSaledCarIU saledCar) {
        return ok(saledCarService.buyCar(saledCar), 200);
    }
}
