package com.datapine.webapi.model;

public class StatisticResponseModel {
    private Integer totalRequests;
    private Integer totalQueries;
    private ChartResponseModel chart;

    public Integer getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Integer totalRequests) {
        this.totalRequests = totalRequests;
    }

    public Integer getTotalQueries() {
        return totalQueries;
    }

    public void setTotalQueries(Integer totalQueries) {
        this.totalQueries = totalQueries;
    }

    public ChartResponseModel getChart() {
        return chart;
    }

    public void setChart(ChartResponseModel chart) {
        this.chart = chart;
    }


}
