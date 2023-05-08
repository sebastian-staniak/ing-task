package com.sebastianstaniak.atmservice.domain;

public class ATM {
    private final int region;
    private final int atmId;

    public ATM(int region, int atmId) {
        this.region = region;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public int getAtmId() {
        return atmId;
    }
}
