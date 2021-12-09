package com.example.myapplication;

import java.io.Serializable;

public class Score implements Serializable {
    private int coins;
    private int distance;
    private double lat;
    private double lon;

    public Score(int coins,int distance,double lat,double lon){
        this.coins = coins;
        this.distance= distance;
        this.lat= lat;
        this.lon = lon;
    }

    public int getCoins() {
        return coins;
    }

    public int getDistance() {
        return distance;
    }

    public double getLat() { return lat; }

    public double getLon() { return lon; }
}
