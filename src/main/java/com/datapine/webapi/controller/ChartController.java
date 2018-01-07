package com.datapine.webapi.controller;

import com.datapine.webapi.model.ChartRequestModel;
import com.datapine.webapi.model.ChartResponseModel;
import com.datapine.webapi.service.ChartRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ChartController {

    @Autowired
    private ChartRetrieveService chartRetrieveService;

    @RequestMapping(value = "/chart", method = RequestMethod.POST)
    public ChartResponseModel chart(@RequestBody @Valid ChartRequestModel chartRequestModel){
        Optional<ChartResponseModel> chartResponseModelOptional = chartRetrieveService.getChartByRequestModel(chartRequestModel);
        if (chartResponseModelOptional.isPresent()){
            return chartResponseModelOptional.get();
        }
        return new ChartResponseModel();
    }

}
