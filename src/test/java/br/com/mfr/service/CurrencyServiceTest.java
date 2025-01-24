package br.com.mfr.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyProvider provider;

    @Test
    void retrieveFromProvider(){
        Mockito.when(provider.retrieve(ArgumentMatchers.anyString())).thenReturn("6.0");

        String currencyPrice = new CurrencyService(provider).getCurrencyBy("BRL");

        Assertions.assertEquals("6.0", currencyPrice);
        Mockito.verify(provider).retrieve("BRL");
    }
}