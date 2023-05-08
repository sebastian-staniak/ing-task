package com.sebastianstaniak.atmservice.domain;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AtmServicePlanner {

    public List<ATM> plan(List<Task> tasks) {
        return tasks
                .stream()
                .collect(Collectors.groupingBy(Task::getRegion, TreeMap::new, Collectors.toList()))
                .values()
                .parallelStream()
                .map(list -> list.stream().distinct().sorted())
                .flatMap(item -> item.map(task -> new ATM(task.getRegion(), task.getAtmId())))
                .toList();
    }
}
