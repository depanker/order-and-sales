package com.depanker.orderandsales.repositories;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Optional<Product> findByCompanyAndProductCompanyId(Company company, Long productCompanyId);
    @Query("select SUM(p.cost) from Product p where p.company = :company group by p.company")
    Double getTotalCost(@Param("company") Company company);
}
