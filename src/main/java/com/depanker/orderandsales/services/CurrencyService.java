package com.depanker.orderandsales.services;

import com.depanker.orderandsales.web.currency.ExchangeRates;

public interface CurrencyService {
     ExchangeRates getExchangeRates();

     Float calculateRates(Float currentPrice, String currentCurrency, ExchangeRates exchangeRates);
}
