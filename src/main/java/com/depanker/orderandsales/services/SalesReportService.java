package com.depanker.orderandsales.services;

import com.depanker.orderandsales.web.exception.NoContentException;
import com.depanker.orderandsales.web.order.OrderDTO;

import java.util.List;

public interface SalesReportService {
    List<OrderDTO> getReport(String companyName) throws NoContentException;
}
