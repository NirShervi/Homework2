package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    private Button playButton;
    private Button scoreButton;
    private ArrayList<Integer> leaderboardScores;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        playButton = findViewById(R.id.menu_button_start);
        scoreButton = findViewById(R.id.menu_button_scores);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openScoreActivity();};
        });

        if (ScoreList.getInstance().getScoreArr() == null) {
            ScoreList.getInstance().loadScoreFromMSP(getApplicationContext());
        }

    }
    public void openScoreActivity() {
        Intent intent = new Intent(this,ScoresPage.class);
        startActivity(intent);
    }


    public void openMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}