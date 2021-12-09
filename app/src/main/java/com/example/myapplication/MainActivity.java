package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

 public class MainActivity extends AppCompatActivity {

     private static final int DELAY = 3000;
     // buttons left and right to move the battleship
     private ImageButton button_RIGHT;
     private ImageButton button_LEFT;
     // rocks images that will be manipulate accourding to random choice
     private ImageView[] rocks_Images;
     // heart images that will be decreased by the battleship touching the rocks
     private ImageView[] heart_Images;
     private ImageView[] coins_Images;
     // battleship images changed by positions
     private ImageView[] battleship_Images;
     // on the top of the screen will be text messages that will show the game situation
     private TextView textCoins,textDistance;
     // clock starting from zero
     private int clock = 0;
     // three life as the number of hearts
     private int life=3;
     // represent  every column of rocks - set activation that would not active other column of rocks
     private int [] rock_Active = new int[5];
     // represent where the battleship is    -1 - left    0 - middle    1-right
     private int battleship_position=0;
     private Timer timer = new Timer();
     // gaps between columns in rock image array
     private int row=9;
     //
     private int endGame=0;
     //
     private int collectedCoins = 0;
     //
     private int distance =0;
     //
     private int sizeOfArray =45;
     //
     private double lon=0,lat=0;
     private LocationManager lm;
     private LocationListener locationListener;








     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         if (endGame==0) {
             findViews();
             InitGame();
             moveBattleship();
             startGameThread();
         }
    }



     private void findViews() {
         button_RIGHT = findViewById(R.id.button_Right);
         button_LEFT = findViewById(R.id.button_Left);
         textCoins = findViewById(R.id.main_coins);
         textDistance = findViewById(R.id.main_distance);

         rocks_Images = new ImageView[]
                 {
                         findViewById(R.id.main_Rock_1x1),
                         findViewById(R.id.main_Rock_1x2),
                         findViewById(R.id.main_Rock_1x3),
                         findViewById(R.id.main_Rock_1x4),
                         findViewById(R.id.main_Rock_1x5),
                         findViewById(R.id.main_Rock_1x6),
                         findViewById(R.id.main_Rock_1x7),
                         findViewById(R.id.main_Rock_1x8),
                         findViewById(R.id.main_Rock_1x9),
                         findViewById(R.id.main_Rock_2x1),
                         findViewById(R.id.main_Rock_2x2),
                         findViewById(R.id.main_Rock_2x3),
                         findViewById(R.id.main_Rock_2x4),
                         findViewById(R.id.main_Rock_2x5),
                         findViewById(R.id.main_Rock_2x6),
                         findViewById(R.id.main_Rock_2x7),
                         findViewById(R.id.main_Rock_2x8),
                         findViewById(R.id.main_Rock_2x9),
                         findViewById(R.id.main_Rock_3x1),
                         findViewById(R.id.main_Rock_3x2),
                         findViewById(R.id.main_Rock_3x3),
                         findViewById(R.id.main_Rock_3x4),
                         findViewById(R.id.main_Rock_3x5),
                         findViewById(R.id.main_Rock_3x6),
                         findViewById(R.id.main_Rock_3x7),
                         findViewById(R.id.main_Rock_3x8),
                         findViewById(R.id.main_Rock_3x9),
                         findViewById(R.id.main_Rock_4x1),
                         findViewById(R.id.main_Rock_4x2),
                         findViewById(R.id.main_Rock_4x3),
                         findViewById(R.id.main_Rock_4x4),
                         findViewById(R.id.main_Rock_4x5),
                         findViewById(R.id.main_Rock_4x6),
                         findViewById(R.id.main_Rock_4x7),
                         findViewById(R.id.main_Rock_4x8),
                         findViewById(R.id.main_Rock_4x9),
                         findViewById(R.id.main_Rock_5x1),
                         findViewById(R.id.main_Rock_5x2),
                         findViewById(R.id.main_Rock_5x3),
                         findViewById(R.id.main_Rock_5x4),
                         findViewById(R.id.main_Rock_5x5),
                         findViewById(R.id.main_Rock_5x6),
                         findViewById(R.id.main_Rock_5x7),
                         findViewById(R.id.main_Rock_5x8),
                         findViewById(R.id.main_Rock_5x9)
                 };
         coins_Images = new ImageView[]
                 {
                         findViewById(R.id.main_Coins_1x1),
                         findViewById(R.id.main_Coins_1x2),
                         findViewById(R.id.main_Coins_1x3),
                         findViewById(R.id.main_Coins_1x4),
                         findViewById(R.id.main_Coins_1x5),
                         findViewById(R.id.main_Coins_1x6),
                         findViewById(R.id.main_Coins_1x7),
                         findViewById(R.id.main_Coins_1x8),
                         findViewById(R.id.main_Coins_1x9),
                         findViewById(R.id.main_Coins_2x1),
                         findViewById(R.id.main_Coins_2x2),
                         findViewById(R.id.main_Coins_2x3),
                         findViewById(R.id.main_Coins_2x4),
                         findViewById(R.id.main_Coins_2x5),
                         findViewById(R.id.main_Coins_2x6),
                         findViewById(R.id.main_Coins_2x7),
                         findViewById(R.id.main_Coins_2x8),
                         findViewById(R.id.main_Coins_2x9),
                         findViewById(R.id.main_Coins_3x1),
                         findViewById(R.id.main_Coins_3x2),
                         findViewById(R.id.main_Coins_3x3),
                         findViewById(R.id.main_Coins_3x4),
                         findViewById(R.id.main_Coins_3x5),
                         findViewById(R.id.main_Coins_3x6),
                         findViewById(R.id.main_Coins_3x7),
                         findViewById(R.id.main_Coins_3x8),
                         findViewById(R.id.main_Coins_3x9),
                         findViewById(R.id.main_Coins_4x1),
                         findViewById(R.id.main_Coins_4x2),
                         findViewById(R.id.main_Coins_4x3),
                         findViewById(R.id.main_Coins_4x4),
                         findViewById(R.id.main_Coins_4x5),
                         findViewById(R.id.main_Coins_4x6),
                         findViewById(R.id.main_Coins_4x7),
                         findViewById(R.id.main_Coins_4x8),
                         findViewById(R.id.main_Coins_4x9),
                         findViewById(R.id.main_Coins_5x1),
                         findViewById(R.id.main_Coins_5x2),
                         findViewById(R.id.main_Coins_5x3),
                         findViewById(R.id.main_Coins_5x4),
                         findViewById(R.id.main_Coins_5x5),
                         findViewById(R.id.main_Coins_5x6),
                         findViewById(R.id.main_Coins_5x7),
                         findViewById(R.id.main_Coins_5x8),
                         findViewById(R.id.main_Coins_5x9)
                 };

         heart_Images = new ImageView []
                 {
                         findViewById(R.id.main_heart_1),
                         findViewById(R.id.main_heart_2),
                         findViewById(R.id.main_heart_3)
                 };
         battleship_Images = new ImageView []
                 {
                         findViewById(R.id.main_Battleship_1),
                         findViewById(R.id.main_Battleship_2),
                         findViewById(R.id.main_Battleship_3),
                         findViewById(R.id.main_Battleship_4),
                         findViewById(R.id.main_Battleship_5)
                 };
     }









     private void InitGame() {
         //disappear all rocks before the game starts
         for (ImageView rock:
                 rocks_Images) {
             rock.setVisibility(View.INVISIBLE);
         }
         for (ImageView coin:
                 coins_Images) {
             coin.setVisibility(View.INVISIBLE);
         }
         for (ImageView heart:
                 heart_Images) {
             heart.setVisibility(View.VISIBLE);
         }
         //start battleship from the middle
         battleship_Images[0].setVisibility(View.INVISIBLE);
         battleship_Images[1].setVisibility(View.INVISIBLE);
         battleship_Images[3].setVisibility(View.INVISIBLE);
         battleship_Images[4].setVisibility(View.INVISIBLE);
         textCoins.setTextSize(12);
         textCoins.setText("Coins: "+collectedCoins);
         textDistance.setTextSize(12);
         textDistance.setText("Distance: "+distance);
         if (rocks_Images[8].getVisibility() == View.VISIBLE) {
             Log.d("Init", "Init:");
         }
         collectedCoins = 0;
         //
         distance =0;
         life=3;
     }










     private void moveBattleship() {
         button_RIGHT.setOnClickListener(v -> {
             if(battleship_position==0){
                 battleship_Images[2].setVisibility(View.INVISIBLE);
                 battleship_Images[3].setVisibility(View.VISIBLE);
                 battleship_position++;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==-1){
                 battleship_Images[1].setVisibility(View.INVISIBLE);
                 battleship_Images[2].setVisibility(View.VISIBLE);
                 battleship_position++;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==1){
                 battleship_Images[3].setVisibility(View.INVISIBLE);
                 battleship_Images[4].setVisibility(View.VISIBLE);
                 battleship_position++;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==-2){
                 battleship_Images[0].setVisibility(View.INVISIBLE);
                 battleship_Images[1].setVisibility(View.VISIBLE);
                 battleship_position++;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
         });
         button_LEFT.setOnClickListener(v -> {
             if(battleship_position==1){
                 battleship_Images[3].setVisibility(View.INVISIBLE);
                 battleship_Images[2].setVisibility(View.VISIBLE);
                 battleship_position--;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==0){
                 battleship_Images[2].setVisibility(View.INVISIBLE);
                 battleship_Images[1].setVisibility(View.VISIBLE);
                 battleship_position--;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==-1){
                 battleship_Images[1].setVisibility(View.INVISIBLE);
                 battleship_Images[0].setVisibility(View.VISIBLE);
                 battleship_position--;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==2){
                 battleship_Images[4].setVisibility(View.INVISIBLE);
                 battleship_Images[3].setVisibility(View.VISIBLE);
                 battleship_position--;
                 Log.d("battleship position changed","the position is "+ battleship_position);
             }
         });

     }









     private void startGameThread() {
         Handler handler = new Handler();
         Runnable runnable = new Runnable() {
             @Override
             public void run() {
                 try {
                     startGame();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 //Game_MTV_distance.setText(++distance + "M");
                 handler.postDelayed(this, 700);
             }
         };
         handler.post(runnable);
     }




     // start generating a random number to decide witch column will activate with falling rocks
     private void startGame() throws InterruptedException {

         handleDistance();
         handleCoins();
         handleRocks();
         if (rocks_Images[8].getVisibility() == View.VISIBLE) {
             Log.d("StartGame loop", "startGame: " );
         }
         for (int i=sizeOfArray-1;i>=0 ; i--){
             if ((i+1)%9==0 || i==8)
             {
                 coins_Images[i].setVisibility(View.INVISIBLE);
                 rocks_Images[i].setVisibility(View.INVISIBLE);
             }
             else{
                 if (coins_Images[i].getVisibility() == View.VISIBLE){
                     coins_Images[i].setVisibility(View.INVISIBLE);
                     coins_Images[i+1].setVisibility(View.VISIBLE);
                 }
                 if (rocks_Images[i].getVisibility() == View.VISIBLE){
                     rocks_Images[i].setVisibility(View.INVISIBLE);
                     rocks_Images[i+1].setVisibility(View.VISIBLE);
                 }
             }
         }
         int rnd1 = new Random().nextInt(5);
         rocks_Images[rnd1*9].setVisibility(View.VISIBLE);
         int rnd2 = new Random().nextInt(5);
         if (rnd1 != rnd2)
            coins_Images[rnd2*9].setVisibility(View.VISIBLE);


     }

     private void handleDistance() {
         distance++;
         textDistance.setText("Distance: "+distance);

     }

     private void handleRocks() {
         if (    (rocks_Images[8].getVisibility()== View.VISIBLE && battleship_position==-2) ||
                 (rocks_Images[17].getVisibility()== View.VISIBLE && battleship_position==-1) ||
                 (rocks_Images[26].getVisibility()== View.VISIBLE && battleship_position==0) ||
                 (rocks_Images[35].getVisibility()== View.VISIBLE && battleship_position==1) ||
                 (rocks_Images[44].getVisibility()== View.VISIBLE && battleship_position==2)){

             try {
                 deleteHeart();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

         }

     }

     private void handleCoins() {
         if (    coins_Images[8].getVisibility()== View.VISIBLE && battleship_position==-2 ||
                 coins_Images[17].getVisibility()== View.VISIBLE && battleship_position==-1||
                 coins_Images[26].getVisibility()== View.VISIBLE && battleship_position==0 ||
                 coins_Images[35].getVisibility()== View.VISIBLE && battleship_position==1 ||
                 coins_Images[44].getVisibility()== View.VISIBLE && battleship_position==2){
             collectedCoins++;
             textCoins.setText("Coins: "+collectedCoins);

         }
     }


     private void deleteHeart() throws InterruptedException {
         boolean bool = rocks_Images[8].getVisibility()== View.VISIBLE;
         Log.d("heart", "delete heart"+ bool +" thread" + Thread.currentThread().getName());
         if (life==3){
             heart_Images[2].setVisibility(View.INVISIBLE);
             vibration();
             life--;
         }
         else if (life==2){
             heart_Images[1].setVisibility(View.INVISIBLE);
             vibration();
             life--;
         }
         else if (life==1){
             heart_Images[0].setVisibility(View.INVISIBLE);
             vibration();
             life--;

         }
         else if(life ==0){
             vibration();
             Thread.sleep(500);
             gameOver();
         }
     }

     private void gameOver() throws InterruptedException {
         Toast.makeText(this, "Restart in 3 sec", Toast.LENGTH_SHORT).show();
         Thread.sleep(3000);
         lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         locationListener = new LocationListener() {
             @Override
             public void onLocationChanged(@NonNull Location location) {
                 lat = location.getLatitude();
                 lon = location.getLongitude();
             }
         };
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
         } else {
             lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
         }

         Score score = new Score(collectedCoins,distance,lat,lon);
         ScoreList.getInstance().addScore(getApplicationContext(), score);

         InitGame();
     }


     @Override
     protected void onStop() {
         super.onStop();
     }

     private void vibration(){
         Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
         // Vibrate for 500 milliseconds
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
         } else {
             //deprecated in API 26
             v.vibrate(500);
         }

     }
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);

         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
             }
         }

     }

}