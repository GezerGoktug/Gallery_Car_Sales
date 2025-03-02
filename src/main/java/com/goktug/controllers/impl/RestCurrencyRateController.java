package com.goktug.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goktug.controllers.IRestCurrencyRateController;
import com.goktug.controllers.RestBaseController;
import com.goktug.controllers.RootEntity;
import com.goktug.dto.CurrencyRateResponse;
import com.goktug.services.ICurrencyRateService;

@RestController
@RequestMapping(value = "/rest/api")
public class RestCurrencyRateController extends RestBaseController implements IRestCurrencyRateController {

    @Autowired
    private ICurrencyRateService currencyRateService;

    @GetMapping("/currencyRate")
    public RootEntity<CurrencyRateResponse> getCurrencyRate(
            @RequestParam(name = "startDate", required = true) String startDate,
            @RequestParam(name = "endDate", required = true) String endDate) {

        return ok(currencyRateService.getCurrencyRate(startDate, endDate), 200);
    }

}
