package com.goktug.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.goktug.dto.CurrencyRateResponse;
import com.goktug.exceptions.BaseException;
import com.goktug.exceptions.ErrorMessage;
import com.goktug.exceptions.MessageType;
import com.goktug.services.ICurrencyRateService;

@Service
public class CurrencyRateService implements ICurrencyRateService {

    @Value("${API_KEY}")
    private String apiKey;

    @Override
    public CurrencyRateResponse getCurrencyRate(String startDate, String endDate) {
        String rootURL = "https://evds2.tcmb.gov.tr/service/evds/";
        String series = "TP.DK.USD.A";
        String type = "json";

        String endpoint = rootURL + "series=" + series + "&startDate=" + startDate + "&endDate=" + endDate + "&type="
                + type;

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("key", apiKey);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CurrencyRateResponse> response = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<CurrencyRateResponse>() {

                    });
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.CURRENCY_RATES_IS_OCCURED), 500);
        }

        return null;
    }
}
