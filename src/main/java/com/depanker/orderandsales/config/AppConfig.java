package com.depanker.orderandsales.config;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.util.function.Function;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public HeaderColumnNameTranslateMappingStrategy mappingStrategy() {
        return new HeaderColumnNameTranslateMappingStrategy();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public CsvToBean csvToBean() {
        return new CsvToBean();
    }

    @Bean
    Function<Reader, CSVReader> readerHelper() {
        return reader ->  csvReader(reader);
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public CSVReader csvReader(Reader fileReader) {
        return new CSVReader(fileReader);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
