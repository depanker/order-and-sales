package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.bean.ProductAdapter;
import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.product.Product;
import com.depanker.orderandsales.services.CurrencyService;
import com.depanker.orderandsales.services.ProductAdapterService;
import com.depanker.orderandsales.web.currency.ExchangeRates;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductAdapterServiceImpl implements ProductAdapterService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CurrencyService currencyServiceImpl;


    @Value("${app.default.currency:EUR}")
    String defaultCurrency;

    private static final String NUMBER_FILTER = "[^0-9.]";

    @Override
    public Product mapToProduct(ProductAdapter productAdapter, Company company) {

        Product product =  new Product();
        productAdapter.setCost(cleanCostValue(productAdapter.getCost()));
        modelMapper.map(productAdapter, product);
        ExchangeRates exchangeRates = currencyServiceImpl.getExchangeRates();
        if (!defaultCurrency.equals(product.getCurrency())) {
            Float productCost = product.getCost();
            Float updatePrice =  currencyServiceImpl.calculateRates(productCost, productAdapter.getCurrency(), exchangeRates);
            if (updatePrice != null) {
                product.setCost(updatePrice);
                product.setCurrency(defaultCurrency);
                product.setCompany(company);
            } else {
                //@TODO: Do something better to report and analyze faulty data, for now just logging the data.
                log.warn("No currency found for {}", productAdapter.toString());
                product = null;
            }
        }
        return product;
    }

    private String cleanCostValue(String cost) {
        return  cost.replaceAll(NUMBER_FILTER, "");
    }
}
