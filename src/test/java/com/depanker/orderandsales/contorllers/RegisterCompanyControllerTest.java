package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.BaseTest;
import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyFieldMapping;
import com.depanker.orderandsales.entities.company.CompanyName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class RegisterCompanyControllerTest extends BaseTest {

    @Test
    void register() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getString(getMockCompany())))
                .andExpect(status().isOk());

    }

    @Test
    void registerError() throws Exception {
        Company company =  new Company();
        company.setCompanyName(new CompanyName());
        company.setFieldMapping(new CompanyFieldMapping());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getString(company)))
                .andExpect(status().isBadRequest());

    }


}