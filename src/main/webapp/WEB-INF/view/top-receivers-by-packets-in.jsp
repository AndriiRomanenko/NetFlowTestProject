<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 2019-04-19
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script type="text/javascript">
            window.onload = function() {

                var dps = [[]];
                var chart = new CanvasJS.Chart("chartContainer", {
                    animationEnabled: true,
                    theme: "light2",
                    title: {
                        text: "Top receivers"
                    },
                    subtitles: [{
                        text: "By packets in"
                    }],
                    axisY: {
                        title: "Packets in",
                        includeZero: false
                    },
                    axisX: {
                        title: "Application name"
                    },
                    data: [{
                        type: "column",
                        yValueFormatString: "#,##0 ",
                        dataPoints: dps[0]
                    }]
                });

                var yValue;
                var label;

                <c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
                <c:forEach items="${dataPoints}" var="dataPoint">
                yValue = parseFloat("${dataPoint.packetsIn}");
                label = "${dataPoint.applicationName}";
                dps[parseInt("${loop.index}")].push({
                    label : label,
                    y : yValue,
                });
                </c:forEach>
                </c:forEach>

                chart.render();

            }
        </script>
</head>
<body>
Your json file is in a folder:  ${message}
<br><br>
<a href="/home/topReceiversByPacketsInForm">Back</a>
<a href="/">Home</a>
<br><br>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

</body>
</html>
