package com.goktug.services.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.DtoAccount;
import com.goktug.dto.DtoAccountIU;
import com.goktug.models.Account;
import com.goktug.repositories.AccountRepository;
import com.goktug.services.IAccountService;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    private Account createAccount(DtoAccountIU account) {
        Account newAccount = new Account();
        newAccount.setCreatedAt(new Date());

        BeanUtils.copyProperties(account, newAccount);

        return newAccount;
    }

    @Override
    public DtoAccount saveAccount(DtoAccountIU account) {
        Account dbAccount = accountRepository.save(createAccount(account));

        DtoAccount dtoAccount = new DtoAccount();

        BeanUtils.copyProperties(dbAccount, dtoAccount);

        return dtoAccount;

    }

}
