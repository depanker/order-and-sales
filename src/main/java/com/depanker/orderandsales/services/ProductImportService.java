package com.depanker.orderandsales.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ProductImportService {
    void importProducts(String fileName, MultipartFile file) throws Exception;
}
