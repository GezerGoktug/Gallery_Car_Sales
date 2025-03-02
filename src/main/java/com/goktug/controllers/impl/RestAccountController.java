package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestAccountController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.DtoAccount;
import com.goktug.dto.DtoAccountIU;
import com.goktug.services.IAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/rest/api/account")
public class RestAccountController extends RestBaseController implements IRestAccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping("/add")
    @Override
    public RootEntity<DtoAccount> saveAccount(@Valid @RequestBody DtoAccountIU account) {
        return ok(accountService.saveAccount(account), 200);
    }
}
