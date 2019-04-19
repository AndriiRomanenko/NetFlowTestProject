package com.romanenko.testProject.dao;

import org.apache.spark.sql.Row;

import java.util.List;
import java.util.Map;

public interface CanvasjsChartDao {
    List<List<Map<Object, Object>>> getCanvasjsChartData(List<Row> l, String param1, String param2, int rowcol1, int rowcol2);
}
