package com.goktug.services;

import com.goktug.dto.DtoCustomer;
import com.goktug.dto.DtoCustomerIU;

public interface ICustomerService {

    DtoCustomer saveCustomer(DtoCustomerIU customer);
}
