package com.goktug.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRateResponse {

    private Integer totalCount;

    private List<CurrencyRateItems> items;
}
