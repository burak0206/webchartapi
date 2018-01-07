package com.datapine.webapi.repository;

import com.datapine.webapi.entity.StatisticsRow;
import com.datapine.webapi.model.StatisticRequestModel;
import com.datapine.webapi.model.StatisticsPair;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class StatisticsRepository {

    private ConcurrentHashMap<LocalDateTime, StatisticsRow> data = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        System.out.println("calisti");
    }


    public void addRequests(LocalDateTime dateTime) {
        StatisticsRow statisticsRow = data.get(dateTime);
        if (statisticsRow == null){
            data.put(dateTime, new StatisticsRow(dateTime));
        }
        data.get(dateTime).incrementTotalRequests();
    }

    public void addQueries(LocalDateTime dateTime) {
        StatisticsRow statisticsRow = data.get(dateTime);
        if (statisticsRow == null){
            data.put(dateTime, new StatisticsRow(dateTime));
        }
        data.get(dateTime).incrementTotalQueries();
    }


    public Map<String, StatisticsPair> findStatisticsByLastTimeAndTimeUnit(StatisticRequestModel statisticRequestModel) {
        int last = statisticRequestModel.getLast() - 1;

        TemporalUnit unit = statisticRequestModel.getTimeUnit().equals("seconds") ? ChronoUnit.SECONDS:ChronoUnit.MINUTES;
        final DateTimeFormatter formatter = statisticRequestModel.getTimeUnit().equals("seconds") ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        final LocalDateTime fromDate = LocalDateTime.now().minus(last, unit);
        List<String> dateTimes = calculateDateTimes(unit, fromDate);

        Map<String, List<Map.Entry<LocalDateTime, StatisticsRow>>> filteredData = data.entrySet().stream()
                .filter(x -> x.getKey().isEqual(fromDate) || x.getKey().isAfter(fromDate))
                .collect(Collectors.groupingBy(
                        x -> ((Map.Entry<LocalDateTime, StatisticsRow>) x).getKey().format(formatter)));

        Map<String, StatisticsPair> result = new HashMap<>();
        for (String dateTime : filteredData.keySet()) {
            List<Map.Entry<LocalDateTime, StatisticsRow>> value = filteredData.get(dateTime);
            int totalRequests = 0;
            int totalQueries = 0;
            for (Map.Entry<LocalDateTime, StatisticsRow> e : value) {
                totalRequests = totalRequests + e.getValue().getTotalRequests().get();
                totalQueries = totalQueries + e.getValue().getTotalQueries().get();
            }
            result.put(dateTime, new StatisticsPair(totalRequests, totalQueries));
        }

        for (String dateTime : dateTimes) {
            if (!result.containsKey(dateTime)) {
                result.put(dateTime, new StatisticsPair(0, 0));
            }
        }

        return result;
    }


    private List<String> calculateDateTimes(TemporalUnit unit,  LocalDateTime fromDate){
        LocalDateTime now = LocalDateTime.now();
        List<String> dateTimes = new ArrayList<String>();
        LocalDateTime tmp = fromDate;
        while (!tmp.isAfter(now)) {
            if (unit==ChronoUnit.MINUTES){
                dateTimes.add(tmp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }else if (unit==ChronoUnit.SECONDS){
                dateTimes.add(tmp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            tmp = tmp.plus(1, unit);
        }
        return dateTimes;
    }
}
