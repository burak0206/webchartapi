package com.datapine.webapi.entity;

public class ChartRow {
    private String team;
    private Integer number;

    public ChartRow(String team, Integer number) {
        this.team = team;
        this.number = number;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

