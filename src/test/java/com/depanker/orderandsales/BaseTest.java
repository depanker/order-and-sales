package com.depanker.orderandsales;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyFieldMapping;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.entities.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public abstract class BaseTest {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{class-name}/{method-name}/",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))).build();
    }
    @Autowired
    private ObjectMapper objectMapper;

    protected String getString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected Company getMockCompany() {
        Company company =  new Company();
        company.setCompanyName(new CompanyName("A"));
        CompanyFieldMapping companyFieldMapping =  new CompanyFieldMapping();
        companyFieldMapping.setCostFieldName("Purchase price");
        companyFieldMapping.setCurrencyFieldName("Currency");
        companyFieldMapping.setProductCompanyIdFieldName("Prod. Id");
        companyFieldMapping.setProductFieldName("Description");
        company.setFieldMapping(companyFieldMapping);
        return company;
    }

    protected Product getMockProduct() {
        Product product =  new Product();
        product.setCompany(getMockCompany());
        product.setCurrency("EUR");
        product.setCost(101.0f);
        product.setDescription("P1");
        product.setProductCompanyId(1l);
        return product;
    }

    protected Product getMockProduct(String name, Float cost, Long productId) {
        Product product =  new Product();
        product.setCompany(getMockCompany());
        product.setCurrency("EUR");
        product.setCost(cost);
        product.setDescription(name);
        product.setProductCompanyId(productId);
        return product;
    }

    protected File getFile(String filePath) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource(filePath).getFile());
        return file;
    }

    protected MockMultipartFile mockMultipartFile(String httpField, String file) throws IOException {
        FileInputStream fis = new FileInputStream(getFile(file));
        return new MockMultipartFile(httpField, file, "text/csv",fis);
    }

    protected MediaType mediaType() {
        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", "265001916915724");
        return new MediaType("multipart", "form-data", contentTypeParams);
    }
}