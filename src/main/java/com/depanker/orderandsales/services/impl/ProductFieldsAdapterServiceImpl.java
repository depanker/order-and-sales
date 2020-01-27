package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.bean.ProductAdapter;
import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.product.Product;
import com.depanker.orderandsales.services.CsvAdapter;
import com.depanker.orderandsales.services.ProductAdapterService;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductFieldsAdapterServiceImpl implements CsvAdapter<Company, List<Product>> {

    @Autowired
    HeaderColumnNameTranslateMappingStrategy mappingStrategy;

    @Autowired
    CsvToBean csvToBean;

    @Autowired
    ProductAdapterService productAdapterServiceImpl;

    @Override
    public List<Product> parse(Company company, CSVReader reader) {
        Map<String, String> map = new HashMap<>();
        map.put(company.getFieldMapping().getProductFieldName(), "description");
        map.put(company.getFieldMapping().getProductCompanyIdFieldName(), "productCompanyId");
        map.put(company.getFieldMapping().getCostFieldName(), "cost");
        map.put(company.getFieldMapping().getCurrencyFieldName(), "currency");
        mappingStrategy.setColumnMapping(map);
        mappingStrategy.setType(ProductAdapter.class);
        csvToBean.setCsvReader(reader);
        csvToBean.setMappingStrategy(mappingStrategy);
        List<ProductAdapter> products =  csvToBean.parse();
        return products.stream().map(productAdapter -> productAdapterServiceImpl.mapToProduct(productAdapter, company))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }


}
