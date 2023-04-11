package com.sebastianstaniak.domain;

import java.util.Objects;

public class Task implements Comparable<Task> {
    private Integer region;
    private TaskType requestType;
    private Integer atmId;


    public Task() {
    }

    public Task(int region, TaskType requestType, int atmId) {
        this.region = region;
        this.requestType = requestType;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public TaskType getRequestType() {
        return requestType;
    }

    public int getAtmId() {
        return atmId;
    }

    @Override
    public int compareTo(Task other) {
        return this.requestType.compareTo(other.requestType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(region, task.region) && Objects.equals(atmId, task.atmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, atmId);
    }

    public enum TaskType {
        FAILURE_RESTART, PRIORITY, SIGNAL_LOW, STANDARD
    }
}
