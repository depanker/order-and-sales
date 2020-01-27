package com.depanker.orderandsales.contorllers;

import com.depanker.orderandsales.services.OrderImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SalesImporterController {
    @Autowired
    OrderImportService orderImportServiceImpl;

    @PostMapping("/upload-order")
    public void uploadFile(@RequestPart MultipartFile salesFile) throws Exception {
        orderImportServiceImpl.importOrder(salesFile);
    }
}
