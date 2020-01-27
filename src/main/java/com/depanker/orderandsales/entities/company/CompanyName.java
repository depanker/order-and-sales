package com.depanker.orderandsales.entities.company;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompanyName {
    @Size(max = 50)
    @NotBlank
    @Column(length = 50, unique = true)
    String companyName;
}
