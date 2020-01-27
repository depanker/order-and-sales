package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.services.CurrencyService;
import com.depanker.orderandsales.web.currency.ExchangeRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${app.currency.exchange.uri}")
    String exchangeRatesUri;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ExchangeRates getExchangeRates() {
        /**
         * @TODO: Write error handling on controller advice
         */
        return restTemplate.getForEntity(exchangeRatesUri, ExchangeRates.class)
                .getBody();
    }

    @Override
    public Float calculateRates(Float currentPrice, String currentCurrency, ExchangeRates exchangeRates) {
        if (exchangeRates.getRates().containsKey(currentCurrency)) {
            return currentPrice/exchangeRates.getRates().get(currentCurrency);
        }
        return null;
    }


}
