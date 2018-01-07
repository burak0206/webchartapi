package com.datapine.webapi.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class StatisticRequestModel {
    @NotNull
    @Min(value = 1)
    private Integer last;

    @NotNull
    @Pattern(regexp = "seconds|minutes", flags = Pattern.Flag.CASE_INSENSITIVE, message = "timeUnit should be minutes or seconds")
    private String timeUnit;

    @NotNull
    @Min(value = 1)
    private Integer mavgPoints;

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getMavgPoints() {
        return mavgPoints;
    }

    public void setMavgPoints(Integer mavgPoints) {
        this.mavgPoints = mavgPoints;
    }
}
