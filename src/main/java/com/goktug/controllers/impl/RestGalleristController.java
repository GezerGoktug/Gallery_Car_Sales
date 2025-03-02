package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestGalleristController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoGallerist;
import com.goktug.dto.DtoGalleristIU;
import com.goktug.services.IGalleristService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/gallerist")
public class RestGalleristController extends RestBaseController implements IRestGalleristController {

    @Autowired
    private IGalleristService galleristService;

    @PostMapping("/add")
    public RootEntity<DtoGallerist> saveGallerist(@Valid @RequestBody DtoGalleristIU gallerist) {

        return ok(galleristService.saveGallerist(gallerist), 200);
    }

}
