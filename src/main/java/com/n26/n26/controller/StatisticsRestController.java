package com.n26.n26.controller;

import com.n26.n26.component.StatisticsComponent;
import com.n26.n26.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsRestController {

    private StatisticsComponent statisticsComponent;


    public StatisticsRestController(@Autowired StatisticsComponent statisticsComponent) {
        this.statisticsComponent = statisticsComponent;

    }

    @GetMapping
    public ResponseEntity<Statistics> getStatistics(){
        return ResponseEntity.ok(statisticsComponent.getAllStatisticComponents().getStatistic());
    }
}
