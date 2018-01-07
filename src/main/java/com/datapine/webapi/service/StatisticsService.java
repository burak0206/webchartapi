package com.datapine.webapi.service;

import com.datapine.webapi.model.*;
import com.datapine.webapi.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public Optional<StatisticResponseModel> getStatistics2(StatisticRequestModel statisticRequestModel) {
        StatisticResponseModel statisticResponseModel = new StatisticResponseModel();
        statisticResponseModel.setTotalQueries(4500);
        statisticResponseModel.setTotalRequests(2500);

        ChartResponseModel chartResponseModel = new ChartResponseModel();
        chartResponseModel.setCategories(Arrays.asList("2017-05-05 13:00","2017-05-05 13:01","2017-05-05 13:02","2017-05-05 13:03","2017-05-05 13:04"));

        List<SeriesModel> seriesModelList = new ArrayList<SeriesModel>();

        SeriesModel seriesModel = new SeriesModel();
        seriesModel.setName("requests");
        seriesModel.setData(Arrays.asList(12.0, 5.0, 5.0, 5.0, 7.0));

        SeriesModel seriesModel2 = new SeriesModel();
        seriesModel2.setName("queries");
        seriesModel2.setData(Arrays.asList(33.0, 24.0, 26.0, 28.0, 28.0));


        SeriesModel seriesModel3 = new SeriesModel();
        seriesModel3.setName("mavg");
        seriesModel3.setData(Arrays.asList(1225.5, 750.0, 625.75, 830.5 ,750.0));

        seriesModelList.add(seriesModel);
        seriesModelList.add(seriesModel2);
        seriesModelList.add(seriesModel3);

        chartResponseModel.setSeries(seriesModelList);
        statisticResponseModel.setChart(chartResponseModel);


        return Optional.ofNullable(statisticResponseModel);
    }

    public Optional<StatisticResponseModel> getStatistics(StatisticRequestModel statisticRequestModel) {
        StatisticResponseModel statisticResponseModel = new StatisticResponseModel();

        Map<String, StatisticsPair> statisticsMap = statisticsRepository.findStatisticsByLastTimeAndTimeUnit(statisticRequestModel);

        int totalRequests = statisticsMap.keySet().stream().sorted().mapToInt(dateTime -> statisticsMap.get(dateTime).getTotalRequests()).sum();
        int totalQueries = statisticsMap.keySet().stream().sorted().mapToInt(dateTime -> statisticsMap.get(dateTime).getTotalQueries()).sum();

        statisticResponseModel.setTotalQueries(totalQueries);
        statisticResponseModel.setTotalRequests(totalRequests);

        List<String> categories = statisticsMap.keySet().stream().sorted().collect(Collectors.toList());

        ChartResponseModel chartResponseModel = new ChartResponseModel();
        chartResponseModel.setCategories(categories);

        List<SeriesModel> seriesModelList = new ArrayList<SeriesModel>();

        List<Double> seriesRequests = statisticsMap.keySet().stream().sorted().map(dateTime ->
                (double) statisticsMap.get(dateTime).getTotalRequests()
        ).collect(Collectors.toList());

        SeriesModel requestsSeries = new SeriesModel();
        requestsSeries.setName("requests");
        requestsSeries.setData(seriesRequests);

        List<Double> seriesQueries = statisticsMap.keySet().stream().sorted().map(dateTime ->
                (double) statisticsMap.get(dateTime).getTotalQueries()
        ).collect(Collectors.toList());

        SeriesModel queriesSeries = new SeriesModel();
        queriesSeries.setName("queries");
        queriesSeries.setData(seriesQueries);

        SeriesModel averageSeries = new SeriesModel();
        averageSeries.setName("mavg");
        averageSeries.setData(calculateSMA(seriesQueries,statisticRequestModel.getMavgPoints()));

        seriesModelList.add(requestsSeries);
        seriesModelList.add(queriesSeries);
        seriesModelList.add(averageSeries);

        chartResponseModel.setSeries(seriesModelList);
        statisticResponseModel.setChart(chartResponseModel);


        return Optional.ofNullable(statisticResponseModel);
    }


    @Async
    public void addRequest() {
        statisticsRepository.addRequests(LocalDateTime.now().withNano(0));
    }

    @Async
    public void addQuery() {
        statisticsRepository.addQueries(LocalDateTime.now().withNano(0));
    }

    private List<Double> calculateSMA(List<Double> data, int mavg) {
        List<Double> result = new ArrayList<Double>();
        List<Double> tmp = new ArrayList<>(data);
        if (!data.isEmpty() && data.size() > mavg) {
            while (tmp.size() >= mavg) {
                List<Double> subset = tmp.subList(0, mavg);
                double mean = 0;
                for (int i = 0; i < subset.size(); i++) {
                    mean = mean + subset.get(i);
                }
                mean = mean / mavg;
                result.add(mean);
                tmp.remove(0);
            }
        }
        return result;
    }



}
