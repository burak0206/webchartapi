package com.datapine.webapi.controller;

import com.datapine.webapi.service.StatisticsService;
import com.datapine.webapi.validator.StatisticRequestValidator;
import com.datapine.webapi.model.StatisticRequestModel;
import com.datapine.webapi.model.StatisticResponseModel;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class StatisticController {

    @Autowired
    private StatisticRequestValidator statisticRequestValidator;

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    public StatisticResponseModel statistics(@RequestBody @Valid StatisticRequestModel statisticRequestModel, BindingResult bindingResult){

        Optional<StatisticResponseModel> statisticResponseModelOptional = statisticsService.getStatistics(statisticRequestModel);
        if(statisticResponseModelOptional.isPresent()){
            return statisticResponseModelOptional.get();
        }
        return new StatisticResponseModel();
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getstatistics(@Valid  StatisticRequestModel statisticRequestModel, BindingResult bindingResult){
        JsonObject status = new JsonObject();
        statisticRequestValidator.validate(statisticRequestModel,bindingResult);
        if (bindingResult.hasErrors() ) {
            status.addProperty("error","timeUnit should be minutes or seconds");
            status.addProperty("exception.message",bindingResult.toString());
            return status.toString();
        }
        Optional<StatisticResponseModel> statisticResponseModelOptional = statisticsService.getStatistics(statisticRequestModel);
        if(statisticResponseModelOptional.isPresent()){
            StatisticResponseModel statisticResponseModel = statisticResponseModelOptional.get();
            status.addProperty("totalRequests", statisticResponseModel.getTotalRequests().toString());
            status.addProperty("totalQueries", statisticResponseModel.getTotalQueries().toString());
            status.addProperty("chart", statisticResponseModel.getChart().toString());

        }
        return status.toString();
    }




}
