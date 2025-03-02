package com.goktug.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goktug.dto.DtoAccount;
import com.goktug.dto.DtoAddress;
import com.goktug.dto.DtoCustomer;
import com.goktug.dto.DtoCustomerIU;
import com.goktug.exceptions.BaseException;
import com.goktug.exceptions.ErrorMessage;
import com.goktug.exceptions.MessageType;
import com.goktug.models.Account;
import com.goktug.models.Address;
import com.goktug.models.Customer;
import com.goktug.repositories.AccountRepository;
import com.goktug.repositories.AddressRepository;
import com.goktug.repositories.CustomerRepository;
import com.goktug.services.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    Customer createCustomer(DtoCustomerIU customer) {
        Customer newCustomer = new Customer();

        Optional<Account> optAccount = accountRepository.findById(customer.getAccountId());

        if (optAccount.isEmpty()) {
            throw new BaseException(new ErrorMessage(customer.getAccountId().toString(), MessageType.NO_RECORD_EXIST),
                    404);
        }

        Optional<Address> optAddress = addressRepository.findById(customer.getAddressId());

        if (optAddress.isEmpty()) {
            throw new BaseException(new ErrorMessage(customer.getAddressId().toString(), MessageType.NO_RECORD_EXIST),
                    404);
        }

        newCustomer.setCreatedAt(new Date());
        newCustomer.setAccount(optAccount.get());
        newCustomer.setAddress(optAddress.get());

        BeanUtils.copyProperties(customer, newCustomer);

        return newCustomer;
    }

    @Override
    public DtoCustomer saveCustomer(DtoCustomerIU customer) {

        Customer dbCustomer = customerRepository.save(createCustomer(customer));

        DtoAccount dtoAccount = new DtoAccount();
        DtoAddress dtoAddress = new DtoAddress();
        DtoCustomer dtoCustomer = new DtoCustomer();

        BeanUtils.copyProperties(dbCustomer, dtoCustomer);
        BeanUtils.copyProperties(dbCustomer.getAccount(), dtoAccount);
        BeanUtils.copyProperties(dbCustomer.getAddress(), dtoAddress);

        dtoCustomer.setAccount(dtoAccount);
        dtoCustomer.setAddress(dtoAddress);

        return dtoCustomer;
    }
}
