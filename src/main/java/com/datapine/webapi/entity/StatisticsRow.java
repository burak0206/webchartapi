package com.datapine.webapi.entity;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticsRow {
    private LocalDateTime dateTime;
    private AtomicInteger totalRequests = new AtomicInteger(0);
    private AtomicInteger totalQueries = new AtomicInteger(0);

    public StatisticsRow(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void incrementTotalQueries() {
        totalQueries.getAndIncrement();
    }

    public void incrementTotalRequests() {
        totalRequests.getAndIncrement();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AtomicInteger getTotalRequests() {
        return totalRequests;
    }

    public AtomicInteger getTotalQueries() {
        return totalQueries;
    }
}
