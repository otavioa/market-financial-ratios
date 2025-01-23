package br.com.mfr.service;

import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private final CurrencyProvider currencyProvider;

    public CurrencyService(CurrencyProvider currencyProvider) {
        this.currencyProvider = currencyProvider;
    }

    public String getCurrencyBy(String symbol) {
        return currencyProvider.retrieve(symbol);
    }

}
