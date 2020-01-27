package com.depanker.orderandsales.services;

import com.depanker.orderandsales.bean.ProductAdapter;
import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.product.Product;

public interface ProductAdapterService {
    Product mapToProduct(ProductAdapter productAdapter, Company company);
}
