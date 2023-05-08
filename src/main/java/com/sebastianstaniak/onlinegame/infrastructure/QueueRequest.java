package com.sebastianstaniak.onlinegame.infrastructure;

import com.sebastianstaniak.onlinegame.domain.Clan;

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
