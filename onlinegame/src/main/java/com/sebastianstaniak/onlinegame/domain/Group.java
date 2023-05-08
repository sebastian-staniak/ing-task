package com.sebastianstaniak.onlinegame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Group {
    private AtomicInteger currentSize = new AtomicInteger(0);
    private int maxSize = 0;
    private List<Clan> clans = new ArrayList<>();

    public Group(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean canFit(Clan clan) {
        return this.currentSize.get() + clan.getNumberOfPlayers() <= this.maxSize;
    }

    public void add(Clan clan) {
        this.clans.add(clan);
        currentSize.addAndGet(clan.getNumberOfPlayers());
    }

    public List<Clan> getClans() {
        return clans;
    }

    public int size() {
        return this.currentSize.get();
    }

    public boolean isFull() {
        return this.size() == this.maxSize;
    }
}
