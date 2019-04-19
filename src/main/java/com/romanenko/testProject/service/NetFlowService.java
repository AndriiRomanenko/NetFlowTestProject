package com.romanenko.testProject.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.FileNotFoundException;

public interface NetFlowService {
    Dataset<Row> getTopReceiversByBytesIn(String date);
    Dataset<Row> getTopReceiversByPacketsIn(String date);
    Dataset<Row> getTopTransmittersByBytesOut(String date);
    Dataset<Row> getTopTransmittersByPacketsOut(String date);
    Dataset<Row> getTopUsedProtocols(String date);
    Dataset<Row> getTopUsedApplications(String date);
    void saveToJson(Dataset<?> dataset, String nameFile) throws FileNotFoundException;
}
