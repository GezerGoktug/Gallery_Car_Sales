package com.goktug.services;

import com.goktug.dto.CurrencyRateResponse;

public interface ICurrencyRateService {
    CurrencyRateResponse getCurrencyRate(String startDate, String endDate);
}
