package com.romanenko.testProject.config;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.apache.spark.sql.functions.*;

@Configuration
@PropertySource("classpath:application.properties")
public class SparkConfig {
    @Value("${spark.app.name}")
    private String appName;
    @Value("${spark.master}")
    private String masterUri;

    @Bean
    public SparkSession getSparkSession(){
        return SparkSession.builder()
                .master(appName)
                .appName(masterUri)
                .getOrCreate();
    }

    @Bean
    public StructType getSchema(){
        StructField[] fields = new StructField[]{
                new StructField("date", DataTypes.StringType, false, Metadata.empty()),
                new StructField("bytesIn", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("bytesOut", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("packetsIn", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("packetsOut", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("applicationName", DataTypes.StringType, false, Metadata.empty()),
                new StructField("destinationAddress", DataTypes.StringType, false, Metadata.empty()),
                new StructField("protocolNumber", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("sourceAddress", DataTypes.StringType, false, Metadata.empty()),
        };
        StructType schema = new StructType(fields);
        return schema;
    }

    @Bean
    public Dataset<Row> getRootDataset() {

        String path = null;
        try {
            path = ResourceUtils.getFile("classpath:static"+ File.separator+"2018-11-20-17-17.csv").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Dataset<Row> dataset = getSparkSession().read()
                .option("header", "true")
                .schema(getSchema())
                .option("timestampFormat", "MM-dd-yyyy'T'HH:mm:ss.SSSZZ")
                .csv(path);
        dataset = dataset.withColumn("date",date_format(to_date(col("date"), "MM/dd/yyyy HH:mm:ss"), "MM-dd-yyyy"));

        return dataset;
    }

}
