package com.depanker.orderandsales.entities.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CompanyFieldMapping {
    @Size(max = 50)
    @NotBlank
    @Column(length = 50)
    private String productFieldName;
    @Size(max = 50)
    @NotBlank
    @Column(length = 50)
    private String costFieldName;
    @Size(max = 50)
    @NotBlank
    @Column(length = 50)
    private String currencyFieldName;
    @Size(max = 50)
    @NotBlank
    @Column(length = 50)
    private String productCompanyIdFieldName;
}
