package com.datapine.webapi.model;

import java.util.List;

public class SeriesModel {
    private String name;
    private List<Double> data;

    public SeriesModel(String name, List<Double> data) {
        this.name = name;
        this.data = data;
    }

    public SeriesModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{name:" + name + ",data:" + data + "}";
    }
}
