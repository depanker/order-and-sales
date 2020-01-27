package com.depanker.orderandsales.repositories;

import com.depanker.orderandsales.entities.company.Company;
import com.depanker.orderandsales.entities.company.CompanyName;
import com.depanker.orderandsales.entities.order.Order;
import com.depanker.orderandsales.web.revenue.RevenueDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByCompanyCompanyName(CompanyName companyName);
    @Query("select NEW com.depanker.orderandsales.web.revenue.RevenueDTO(FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate),SUM(o.total)) from Order o where o.company = :company group by  FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate) ")
    List<RevenueDTO> getRevenues(@Param("company") Company company);
}
