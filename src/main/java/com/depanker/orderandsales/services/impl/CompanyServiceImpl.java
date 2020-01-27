package com.depanker.orderandsales.services.impl;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.repositories.CompanyRepository;
import com.depanker.orderandsales.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void register(Company company) {
        companyRepository.save(company);
    }
}
