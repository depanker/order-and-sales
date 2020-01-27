package com.depanker.orderandsales.web.company;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CompanyRequest  {

    @NotNull
    @Valid
    CompanyNameRequest companyName;

    @NotNull
    @Valid
    CompanyFieldMappingRequest fieldMapping;
}
