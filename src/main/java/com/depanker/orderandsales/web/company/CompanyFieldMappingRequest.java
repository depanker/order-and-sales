package com.depanker.orderandsales.web.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyFieldMappingRequest {
    @Size(max = 20)
    @NotBlank
    private String productFieldName;
    @Size(max = 20)
    @NotBlank
    private String costFieldName;
    @Size(max = 20)
    @NotBlank()
    private String currencyFieldName;
}
