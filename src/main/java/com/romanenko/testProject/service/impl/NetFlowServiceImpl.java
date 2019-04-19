package com.romanenko.testProject.service.impl;

import com.romanenko.testProject.config.SparkConfig;
import com.romanenko.testProject.service.NetFlowService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.apache.spark.sql.functions.*;

@Service
public class NetFlowServiceImpl implements NetFlowService {

    @Autowired
    SparkConfig config;

    @Override
    public void saveToJson(Dataset<?> dataset, String nameFile) {
        String rootPath = null;
        try {
            rootPath = ResourceUtils.getFile("classpath:static").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String resultDirectory = rootPath + File.separator + "result";
        String tempDirectory = resultDirectory + File.separator + "temp";

        dataset.write()
                .format("json")
                .mode(SaveMode.Overwrite)
                .save(tempDirectory);

        File file = new File(tempDirectory);
        for (String currentFile : file.list()) {
            if (currentFile.endsWith(".json")) {
                String pathToJsonFile = tempDirectory + File.separator + currentFile;
                File jsonFile = new File(pathToJsonFile);
                jsonFile.renameTo(new File(resultDirectory + File.separator + nameFile + "_"
                        + LocalDateTime.now().toString() + ".json"));
            }
            String trashPath = tempDirectory + File.separator + currentFile;
            File trash = new File(trashPath);
            trash.delete();
        }
        file.delete();
    }

    @Override
    public Dataset<Row> getTopReceiversByBytesIn(String date) {
        Dataset<Row> topReceiversByBytesIn = config.getRootDataset()
                    .where("date = \'"+ date + "\'")
                    .filter(config.getRootDataset().col("applicationName").isNotNull())
                    .sort(col("bytesIn").desc())
                    .distinct()
                    .limit(10);
        return topReceiversByBytesIn;
    }

    @Override
    public Dataset<Row> getTopReceiversByPacketsIn(String date) {
        Dataset<Row> topReceiversByPacketsIn =  config.getRootDataset()
                    .where("date = \'"+ date + "\'")
                    .filter(config.getRootDataset().col("applicationName").isNotNull())
                    .sort(desc("packetsIn"))
                    .distinct()
                    .limit(10);
        return topReceiversByPacketsIn;
    }

    @Override
    public Dataset<Row> getTopTransmittersByBytesOut(String date) {
        Dataset<Row> topTransmittersByBytesOut =  config.getRootDataset()
                    .where("date = \'"+ date + "\'")
                    .filter(config.getRootDataset().col("applicationName").isNotNull())
                    .sort(desc("bytesOut"))
                    .distinct()
                    .limit(10);
        return  topTransmittersByBytesOut;
    }

    @Override
    public Dataset<Row> getTopTransmittersByPacketsOut(String date) {
        Dataset<Row> topTransmittersByPacketsOut = config.getRootDataset()
                    .where("date = \'"+ date + "\'")
                    .filter(config.getRootDataset().col("applicationName").isNotNull())
                    .sort(desc("packetsOut"))
                    .distinct()
                    .limit(10);
        return topTransmittersByPacketsOut;
    }

    @Override
    public Dataset<Row> getTopUsedProtocols(String date) {
        Dataset<Row> topUsedProtocols = config.getRootDataset()
                    .where("date = \'"+ date + "\'")
                    .filter(config.getRootDataset().col("applicationName").isNotNull())
                    .sort(desc("protocolNumber"))
                    .distinct()
                    .limit(3);
        return topUsedProtocols;
    }

    @Override
    public Dataset<Row> getTopUsedApplications(String date) {
        Dataset<Row> topUsedApplications = config.getRootDataset()
                    .where("date = \'"+ date + "\'")
                    .filter(config.getRootDataset().col("applicationName").isNotNull())
                    .groupBy("applicationName")
                    .agg(count("applicationName").name("count"))
                    .sort(desc("count"))
                    .limit(10);
        return topUsedApplications;
    }
}
