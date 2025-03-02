package com.goktug.controllers;

import com.goktug.dto.DtoAccount;
import com.goktug.dto.DtoAccountIU;

public interface IRestAccountController {

    RootEntity<DtoAccount> saveAccount(DtoAccountIU account);
}
