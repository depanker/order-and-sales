package com.depanker.orderandsales.services;


import com.opencsv.CSVReader;

public interface CsvAdapter<A, B> {
    B parse(A a, CSVReader reader);
}
