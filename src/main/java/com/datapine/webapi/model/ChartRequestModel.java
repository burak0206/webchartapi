package com.datapine.webapi.model;

import javax.validation.constraints.Size;
import java.util.List;

public class ChartRequestModel {
    @Size(min=1, max=1)
    private List<String> dimensions;

    @Size(min=1, max=3)
    private List<String> measures;

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }
}
