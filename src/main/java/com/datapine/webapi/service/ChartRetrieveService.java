package com.datapine.webapi.service;

import com.datapine.webapi.entity.ChartRow;
import com.datapine.webapi.model.ChartRequestModel;
import com.datapine.webapi.model.ChartResponseModel;
import com.datapine.webapi.model.SeriesModel;
import com.datapine.webapi.repository.ResultSetsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ChartRetrieveService {
    @Autowired
    private ResultSetsDataRepository resultSetsDataRepository;

    @Autowired
    private StatisticsService statisticsService;

    public Optional<ChartResponseModel> getChartByRequestModel(ChartRequestModel chartRequestModel) {

        CompletableFuture.runAsync(() -> {
            writeRequestAndQueryIntoStatisticsRepository(chartRequestModel.getMeasures());

        });
        if(!chartRequestModel.getDimensions().contains("team")){
            return Optional.empty();
        }

        ChartResponseModel chartResponseModel = new ChartResponseModel();
        String key = chartRequestModel.getMeasures().get(0);
        if(resultSetsDataRepository.getDataSetsByKey(key).isPresent()){
            List<ChartRow> chartRows = resultSetsDataRepository.getDataSetsByKey(key).get();
            List<String> teams = chartRows.stream().map(chartRow -> chartRow.getTeam()).collect(Collectors.toList());
            chartResponseModel.setCategories(teams);
        }
        List<SeriesModel> seriesModelList = new ArrayList<SeriesModel>();
        chartRequestModel.getMeasures().stream().forEach(m ->
                resultSetsDataRepository.getDataSetsByKey(m).ifPresent( list ->{
                            List<Double> numbers =list.stream().map(l -> ((double) l.getNumber())).collect(Collectors.toList());
                            seriesModelList.add(new SeriesModel(m,numbers));
                        }
                )
        );
        chartResponseModel.setSeries(seriesModelList);
        return Optional.ofNullable(chartResponseModel);
    }

    private void writeRequestAndQueryIntoStatisticsRepository(List<String> measures) {
        statisticsService.addRequest();
        measures.forEach(m -> statisticsService.addQuery());

    }
}
