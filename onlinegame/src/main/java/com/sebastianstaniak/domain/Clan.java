package com.sebastianstaniak.domain;

import java.util.Objects;

public class Clan implements Comparable<Clan> {
    private int numberOfPlayers;
    private int points;

    public Clan(int numberOfPlayers, int points) {
        this.numberOfPlayers = numberOfPlayers;
        this.points = points;
    }

    public Clan() {
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(Clan other) {
        if (this.points != other.points) {
            return Integer.compare(other.points, this.points);
        } else {
            return Integer.compare(this.numberOfPlayers, other.numberOfPlayers);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clan clan = (Clan) o;
        return numberOfPlayers == clan.numberOfPlayers && points == clan.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfPlayers, points);
    }
}
