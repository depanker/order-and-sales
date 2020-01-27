package com.depanker.orderandsales.services;
@FunctionalInterface
public interface CurrencyConverter {
    Double converExchange(Double rate, Double value);
}
