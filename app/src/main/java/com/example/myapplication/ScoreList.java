package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import com.google.gson.reflect.TypeToken;

public class ScoreList  extends Application {
    private ArrayList<Score> scoreArr;
    private static ScoreList scorelist;

    public ScoreList(){
        scorelist=this;
    }
    public static ScoreList getInstance(){
        if(scorelist == null){
            scorelist = new ScoreList();
        }
        return  scorelist;
    }
    public void addScore(Context context,Score score){
        if (scoreArr.size()<10){
            scoreArr.add(score);
        } else{
            for (Score scoreUnit : scoreArr){
                if (score.getCoins()> scoreUnit.getCoins() ){
                    scoreArr.remove(scoreArr.size()-1);
                    scoreArr.add(score);
                    break;
                }
            }
        }
        scoreArr.sort(new coinSort());
        saveScoreToMSP(context);
    }

    private void saveScoreToMSP(Context context){
        String json = new Gson().toJson(this.scoreArr);
        MySharedPreferences.getInstance(context).putStringSP("Scores",json);
    }

    public ArrayList<Score> getScoreArr(){return scoreArr;}

    public void loadScoreFromMSP(Context context){
        String scoreArrString = MySharedPreferences.getInstance(context).getStringSP("SCORE_ARR","NULL");
        if (scoreArrString.equals("NULL")){
            this.scoreArr = new ArrayList<Score>();
        } else {
            this.scoreArr = new Gson().fromJson(scoreArrString, new TypeToken<ArrayList<Score>>(){}.getType());
        }
    }



    public class coinSort implements Comparator<Score> {

        @Override
        public int compare(Score s1, Score s2) {
            if (s1.getCoins() == s2.getCoins())
                return 0;
            return s1.getCoins() < s2.getCoins() ? 1 : -1;
        }
    }
}
