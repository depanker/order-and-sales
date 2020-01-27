package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.entities.product.Product;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.repositories.ProductRepository;
import com.depanker.orderandsales.services.CsvAdapter;
import com.depanker.orderandsales.services.ProductImportService;
import com.depanker.orderandsales.web.exception.AppException;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProductImportServiceImpl implements ProductImportService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CsvAdapter<Company, List<Product>> productFieldsMappingServiceImpl;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    Function<Reader, CSVReader> readerHelper;

    @Override
    public void importProducts(String fileName, MultipartFile file) throws IOException, AppException {
        String companyName =  getCompanyName(fileName);
        Optional<Company> company = companyRepository.findByCompanyName(new CompanyName(companyName));
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Product> products = company.map(comp -> productFieldsMappingServiceImpl.parse(comp, readerHelper.apply(reader)))
                    .orElseThrow(() -> new AppException("Company with name " + companyName + " is not registered."));
            productRepository.saveAll(products);
        }
    }

    private String getCompanyName(String fileName) {
        return fileName.replace(".csv", "");
    }
}
