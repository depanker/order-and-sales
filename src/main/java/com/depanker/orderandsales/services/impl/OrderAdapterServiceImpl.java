package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.bean.OrderAdapter;
import com.depanker.orderandsales.entities.order.LineItem;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.repositories.ProductRepository;
import com.depanker.orderandsales.services.CurrencyService;
import com.depanker.orderandsales.services.OrderAdapterService;
import com.depanker.orderandsales.web.currency.ExchangeRates;
import com.depanker.orderandsales.web.exception.AppException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderAdapterServiceImpl implements OrderAdapterService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CurrencyService currencyServiceImpl;

    @Value("${app.default.currency:EUR}")
    String defaultCurrency;

    @Override
    public Order map(OrderAdapter orderAdapter, ExchangeRates exchangeRates) throws Exception {
        final Order order =  new Order();

        modelMapper.map(orderAdapter, order);
        companyRepository.findByCompanyName(order.getCompany().getCompanyName())
                .map(company -> {
                    order.setCompany(company);
                return order;
                })
                .orElseThrow(() -> new AppException("Company with name " + order.getCompany().getCompanyName().getCompanyName() + " is not registered."));
        addLineItem(order, orderAdapter, exchangeRates);

        return order;
    }

    @Override
    public Order addLineItem(Order order, OrderAdapter orderAdapter, ExchangeRates exchangeRates) throws Exception {
        LineItem item = productRepository.findByCompanyAndProductCompanyId(order.getCompany(), orderAdapter.getCompanyProductId())
        .map(product -> {
            LineItem lineItem =  new LineItem();
            lineItem.setProduct(product);
            Float updatePrice =  orderAdapter.getSalesPrice();
            if (!defaultCurrency.equals(order.getCurrency())) {
                updatePrice = currencyServiceImpl.calculateRates(orderAdapter.getSalesPrice(), order.getCurrency(), exchangeRates);
                order.setCurrency(defaultCurrency);
            }
            lineItem.setQuantity(orderAdapter.getQuantity());
            lineItem.setSalesPrice(updatePrice);

            return lineItem;
        }).orElseThrow(() ->  new AppException("Product with this product id "+orderAdapter.getCompanyProductId()+" has not been registered by this company " + order.getCompany().getCompanyName() + " is not registered."));

        order.addLinItem(item);
        return order;
    }

}
