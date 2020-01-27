package com.depanker.orderandsales.services;

import com.depanker.orderandsales.bean.OrderAdapter;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.web.currency.ExchangeRates;

public interface OrderAdapterService {
    Order map(OrderAdapter orderAdapter, ExchangeRates exchangeRates) throws Exception;
    Order addLineItem(Order order, OrderAdapter orderAdapter, ExchangeRates exchangeRates) throws Exception;
}
