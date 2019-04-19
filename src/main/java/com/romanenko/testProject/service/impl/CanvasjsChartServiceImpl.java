package com.romanenko.testProject.service.impl;

import com.romanenko.testProject.dao.CanvasjsChartDao;
import com.romanenko.testProject.service.CanvasjsChartService;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CanvasjsChartServiceImpl implements CanvasjsChartService {

    @Autowired
    private CanvasjsChartDao canvasjsChartDao;

    public void setCanvasjsChartDao(CanvasjsChartDao canvasjsChartDao) {
        this.canvasjsChartDao = canvasjsChartDao;
    }

    @Override
    public List<List<Map<Object, Object>>> getCanvasjsChartData(List<Row> l, String param1, String param2, int rowcol1, int rowcol2) {
        return canvasjsChartDao.getCanvasjsChartData(l, param1, param2, rowcol1, rowcol2);
    }
}
