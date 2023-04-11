package com.sebastianstaniak.domain;

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
}
