package com.romanenko.testProject.model;

import org.apache.spark.sql.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanvasjsChartData {

    public static List<List<Map<Object, Object>>> getCanvasjsDataList(List<Row> l, String param1, String param2, int rowcol1, int rowcol2) {
        Map<Object,Object> map;
        List<List<Map<Object,Object>>> list = new ArrayList<>();
        List<Map<Object,Object>> dataPoints = new ArrayList<>();

        for (Row row : l) {
            map = new HashMap<>();

            map.put(param1, row.get(rowcol1));
            map.put(param2, row.get(rowcol2));

            dataPoints.add(map);
        }

        list.add(dataPoints);

        return list;
    }
}
