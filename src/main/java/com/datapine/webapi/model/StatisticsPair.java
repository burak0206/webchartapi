package com.datapine.webapi.model;

public class StatisticsPair {

    private Integer totalRequests = 0;
    private Integer totalQueries = 0;

    public StatisticsPair(Integer totalRequests, Integer totalQueries) {
        this.totalRequests = totalRequests;
        this.totalQueries = totalQueries;
    }

    public Integer getTotalRequests() {
        return totalRequests;
    }

    public Integer getTotalQueries() {
        return totalQueries;
    }

    public void setTotalRequests(Integer totalRequests) {
        this.totalRequests = totalRequests;
    }

    public void setTotalQueries(Integer totalQueries) {
        this.totalQueries = totalQueries;
    }
}
