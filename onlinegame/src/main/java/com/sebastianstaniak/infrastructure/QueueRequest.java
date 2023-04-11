package com.sebastianstaniak.infrastructure;

import com.sebastianstaniak.domain.Clan;

import java.util.List;

public class QueueRequest {
    private int groupCount;
    private List<Clan> clans;

    public QueueRequest() {
    }

    public int getGroupCount() {
        return groupCount;
    }

    public List<Clan> getClans() {
        return clans;
    }
}
