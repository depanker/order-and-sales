package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.services.ProductImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductImporterController {
    @Autowired
    ProductImportService productImportServiceImpl;

    @PostMapping("/upload-product")
    public void uploadFile(@RequestPart MultipartFile productFile) throws Exception {
        String fileName = productFile.getOriginalFilename();
        productImportServiceImpl.importProducts(fileName, productFile);
    }
}