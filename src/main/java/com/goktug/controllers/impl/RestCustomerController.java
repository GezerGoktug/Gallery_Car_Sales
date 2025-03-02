package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestCustomerController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoCustomer;
import com.goktug.dto.DtoCustomerIU;
import com.goktug.services.ICustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/customer")
public class RestCustomerController extends RestBaseController implements IRestCustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/add")
    public RootEntity<DtoCustomer> saveCustomer(@Valid @RequestBody DtoCustomerIU customer) {
        System.out.println(customer);
        return ok(customerService.saveCustomer(customer), 200);
    }

}
