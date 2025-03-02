package com.goktug.controllers;

import com.goktug.dto.DtoCustomer;
import com.goktug.dto.DtoCustomerIU;

public interface IRestCustomerController {

    RootEntity<DtoCustomer> saveCustomer(DtoCustomerIU customer);
}
