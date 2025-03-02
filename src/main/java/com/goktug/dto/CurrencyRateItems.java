package com.goktug.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRateItems {
    
    @JsonProperty("Tarih")
    private String date;

    @JsonProperty("TP_DK_USD_A")
    private String usd;
}
