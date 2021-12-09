package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class ListFragment extends Fragment {

    private ListView listViewScores;
    private AppCompatActivity activity;
    private CallBack_list callbacklist;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        listViewScores = view.findViewById(R.id.ScoresBoard);
        StringBuffer listViewScoreString = new StringBuffer();
        for (Score score : ScoreList.getInstance().getScoreArr()){
            listViewScoreString.append(score.getCoins()+"\n");
        }
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (Score score : ScoreList.getInstance().getScoreArr()){
            stringArrayList.add("Coins: "+score.getCoins()+"Distance: "+score.getDistance()+"\n");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1,stringArrayList );
        listViewScores.setAdapter(arrayAdapter);

        listViewScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callbacklist != null) {
                    ArrayList<Score> records = ScoreList.getInstance().getScoreArr();
                    double latitude = records.get(position).getLat();
                    double longitude = records.get(position).getLon();
                    callbacklist.setMapLocation(latitude, longitude);
                }
            }
        });

        return view;
    }
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
    public void setCallbackList(CallBack_list callbacklist) {
        this.callbacklist = callbacklist;
    }
}