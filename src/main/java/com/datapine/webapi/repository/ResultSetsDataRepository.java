package com.datapine.webapi.repository;

import com.datapine.webapi.entity.ChartRow;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ResultSetsDataRepository {
    private ConcurrentHashMap<String,List<ChartRow>> dataSets = new ConcurrentHashMap<String,List<ChartRow>>();

    private List<ChartRow> revenueList = new ArrayList<>();
    private List<ChartRow> championsList = new ArrayList<>();
    private List<ChartRow> leaguesList = new ArrayList<>();

    @PostConstruct
    public void init(){
        revenueList.add(new ChartRow("Real Madrid", 625));
        revenueList.add(new ChartRow("Barcelona", 620));
        revenueList.add(new ChartRow("Bayern Munich", 600));
        revenueList.add(new ChartRow("Liverpool", 400));
        revenueList.add(new ChartRow("Milan", 250));

        championsList.add(new ChartRow("Real Madrid", 12));
        championsList.add(new ChartRow("Barcelona", 5));
        championsList.add(new ChartRow("Bayern Munich", 5));
        championsList.add(new ChartRow("Liverpool", 5));
        championsList.add(new ChartRow("Milan", 7));

        leaguesList.add(new ChartRow("Real Madrid", 33));
        leaguesList.add(new ChartRow("Barcelona", 24));
        leaguesList.add(new ChartRow("Bayern Munich", 26));
        leaguesList.add(new ChartRow("Liverpool", 18));
        leaguesList.add(new ChartRow("Milan", 18));

        dataSets.put("revenue",revenueList);
        dataSets.put("champions",championsList);
        dataSets.put("leagues",leaguesList);
    }

    public Optional<List<ChartRow>> getDataSetsByKey(String key){
        return Optional.ofNullable(dataSets.getOrDefault(key,null));
    }

}
