package com.goktug.controllers;

import com.goktug.dto.CurrencyRateResponse;

public interface IRestCurrencyRateController {

    RootEntity<CurrencyRateResponse> getCurrencyRate(String startDate, String endDate);
}
