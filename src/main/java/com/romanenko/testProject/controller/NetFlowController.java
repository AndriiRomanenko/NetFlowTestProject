package com.romanenko.testProject.controller;

import com.romanenko.testProject.service.CanvasjsChartService;
import com.romanenko.testProject.service.NetFlowService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class NetFlowController {
    @Autowired
    private NetFlowService service;

    @Autowired
    private CanvasjsChartService canvasjsChartService;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/home/topReceiversByBytesInForm")
    public String topReceiversByBytesInForm() {
        return "top-receivers-by-bytes-in-form";
    }

    @RequestMapping(value = "/home/topReceiversByBytesIn", method = RequestMethod.GET)
    public String topReceiversByBytesIn(@RequestParam("date") String date, Model model, ModelMap modelMap) throws FileNotFoundException {
        String nameFile = "top_receivers_by_bytes_in";

        Dataset<Row> dataset = service.getTopReceiversByBytesIn(date);
        service.saveToJson(dataset, nameFile);

        model.addAttribute("message", getPathFile(nameFile, date));

        List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService
                .getCanvasjsChartData(dataset.collectAsList(), "bytesIn", "applicationName", 1, 5);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);

        return "top-receivers-by-bytes-in";
    }

    @RequestMapping("/home/topReceiversByPacketsInForm")
    public String topReceiversByPacketsInForm(){
        return "top-receivers-by-packets-in-form";
    }

    @RequestMapping(value = "/home/topReceiversByPacketsIn", method = RequestMethod.GET)
    public String topReceiversByPacketsIn(@RequestParam("date") String date, Model model, ModelMap modelMap) throws FileNotFoundException{
        String nameFile = "top_receivers_by_packets_in";

        Dataset<Row> dataset = service.getTopReceiversByPacketsIn(date);
        service.saveToJson(dataset, nameFile);

        model.addAttribute("message", getPathFile(nameFile, date));

        List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService
                .getCanvasjsChartData(dataset.collectAsList(), "packetsIn", "applicationName", 3, 5);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);

        return "top-receivers-by-packets-in";
    }


    @RequestMapping("/home/topTransmittersByBytesOutForm")
    public String topTransmittersByBytesOutForm(){
        return "top-transmitters-by-bytes-out-form";
    }

    @RequestMapping(value = "/home/topTransmittersByBytesOut", method = RequestMethod.GET)
    public String topTransmittersByBytesOut(@RequestParam("date") String date, Model model, ModelMap modelMap) throws FileNotFoundException{
        String nameFile = "top_transmitters_by_bytes_out";

        Dataset<Row> dataset = service.getTopTransmittersByBytesOut(date);
        service.saveToJson(dataset, nameFile);

        model.addAttribute("message", getPathFile(nameFile, date));

        List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService
                .getCanvasjsChartData(dataset.collectAsList(), "bytesOut", "applicationName", 2, 5);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);

        return "top-transmitters-by-bytes-out";
    }

    @RequestMapping("/home/topTransmittersByPacketsOutForm")
    public String topTransmittersByPacketsOutForm(){
        return "top-transmitters-by-packets-out-form";
    }

    @RequestMapping(value = "/home/topTransmittersByPacketsOut", method = RequestMethod.GET)
    public String topTransmittersByPacketsOut(@RequestParam("date") String date, Model model, ModelMap modelMap) throws FileNotFoundException{
        String nameFile = "top_transmitters_by_packets_out";

        Dataset<Row> dataset = service.getTopTransmittersByPacketsOut(date);
        service.saveToJson(dataset, nameFile);

        model.addAttribute("message", getPathFile(nameFile, date));

        List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService
                .getCanvasjsChartData(dataset.collectAsList(), "packetsOut", "applicationName", 4, 5);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);

        return "top-transmitters-by-packets-out";
    }

    @RequestMapping("/home/topUsedProtocolsForm")
    public String topUsedProtocolsForm(){
        return "top-used-protocols-form";
    }

    @RequestMapping(value = "/home/topUsedProtocols", method = RequestMethod.GET)
    public String topUsedProtocols(@RequestParam("date") String date, Model model, ModelMap modelMap) throws FileNotFoundException{
        String nameFile = "top_used_protocols";

        Dataset<Row> dataset = service.getTopUsedProtocols(date);
        service.saveToJson(dataset, nameFile);

        model.addAttribute("message", getPathFile(nameFile, date));

        List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService
                .getCanvasjsChartData(dataset.collectAsList(), "protocolNumber", "applicationName", 7, 5);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);

        return "top-used-protocols";
    }

    @RequestMapping("/home/topUsedApplicationsForm")
    public String topUsedApplicationsForm(){
        return "top-used-applications-form";
    }

    @RequestMapping(value = "/home/topUsedApplications", method = RequestMethod.GET)
    public String topUsedApplications(@RequestParam("date") String date, Model model, ModelMap modelMap) throws FileNotFoundException{
        String nameFile = "top_used_applications";

        Dataset<Row> dataset = service.getTopUsedApplications(date);
        service.saveToJson(dataset, nameFile);

        model.addAttribute("message", getPathFile(nameFile, date));

        List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService
                .getCanvasjsChartData(dataset.collectAsList(), "count", "applicationName", 1, 0);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);

        return "top-used-applications";
    }

    private String getPathFile(String nameFile, String date) throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:static").getPath()
                + File.separator + "result" + File.separator + nameFile + "_from_"+ date + "_at_"
                + LocalDateTime.now().toString() + ".json";
    }
}
