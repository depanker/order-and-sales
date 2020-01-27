package com.depanker.orderandsales.services;

import org.springframework.web.multipart.MultipartFile;

public interface OrderImportService {
    void importOrder(MultipartFile file) throws Exception;
}
