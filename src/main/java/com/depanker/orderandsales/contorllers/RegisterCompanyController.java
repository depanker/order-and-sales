package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterCompanyController {
    @Autowired
    private CompanyService companyServiceImpl;

    @PostMapping("/register-company")
    public void register(@Valid @RequestBody Company company) {
        companyServiceImpl.register(company);
    }

}
