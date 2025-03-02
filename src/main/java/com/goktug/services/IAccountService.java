package com.goktug.services;

import com.goktug.dto.DtoAccount;
import com.goktug.dto.DtoAccountIU;

public interface IAccountService {

    DtoAccount saveAccount(DtoAccountIU account);
}
