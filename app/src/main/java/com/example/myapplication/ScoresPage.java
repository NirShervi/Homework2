package com.example.myapplication;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScoresPage extends AppCompatActivity {
    private ArrayList <Integer> distances;
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private CallBack_list callbacklist;

    public ScoresPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores_page);
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        callbacklist = new CallBack_list() {
            @Override
            public void setMapLocation(double lat, double lon) {
                mapFragment.changeMap(lat,lon);
            }
        };

        getSupportFragmentManager().beginTransaction().add(R.id.ScoreFrame,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.MapFrame,mapFragment).commit();
        listFragment.setActivity(this);
        listFragment.setCallbackList(callbacklist);
    }
}