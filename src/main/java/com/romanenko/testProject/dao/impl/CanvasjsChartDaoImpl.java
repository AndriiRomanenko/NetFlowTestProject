package com.romanenko.testProject.dao.impl;

import com.romanenko.testProject.dao.CanvasjsChartDao;
import com.romanenko.testProject.model.CanvasjsChartData;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CanvasjsChartDaoImpl implements CanvasjsChartDao {
    @Override
    public List<List<Map<Object, Object>>> getCanvasjsChartData(List<Row> l, String param1, String param2, int rowcol1, int rowcol2) {
        return CanvasjsChartData.getCanvasjsDataList(l, param1, param2, rowcol1, rowcol2);
    }
}
