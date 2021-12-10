package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
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
     // three life as the number of hearts
     private int life=3;
     // represent where the battleship is    -1 - left    0 - middle    1-right
     private int battleship_position=0;
     private Timer timer = new Timer();
     // gaps between columns in rock image array
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
     private MediaPlayer mediaPlayer;








     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         if (endGame==0) {
             findViews();
             InitGame();
             lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
             locationListener = new LocationListener() {
                 @Override
                 public void onLocationChanged(@NonNull Location location) {
                     lat = location.getLatitude();
                     lon = location.getLatitude();
                     Log.i("@@@@@@@@gps@@@@@@@@@", "lat:  (in func) "+ location.getLatitude() +" lon: (in func)" + location.getLatitude());
                 }
             };
             Log.i("@@@@@@@@gps@@@@@@@@@", "lat:    "+ lat +" lon:" + lon);
             if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
             } else {
                 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
             }
             moveBattleship();
             startGameThread();
         }
    }



     private void findViews() {
         button_RIGHT = findViewById(R.id.button_Right);
         button_LEFT = findViewById(R.id.button_Left);
         textCoins = findViewById(R.id.main_coins);
         textDistance = findViewById(R.id.main_distance);
         mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.spaceship_crush);
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
         //disappear all coins before the game starts
         for (ImageView coin:
                 coins_Images) {
             coin.setVisibility(View.INVISIBLE);
         }
         //show all hearts when the game starts
         for (ImageView heart:
                 heart_Images) {
             heart.setVisibility(View.VISIBLE);
         }
         //start battleship from the middle
         battleship_Images[0].setVisibility(View.INVISIBLE);
         battleship_Images[1].setVisibility(View.INVISIBLE);
         battleship_Images[2].setVisibility(View.VISIBLE);
         battleship_position=0;
         battleship_Images[3].setVisibility(View.INVISIBLE);
         battleship_Images[4].setVisibility(View.INVISIBLE);
         // init Upper bar (coins counter,distance counter and hearts)
         textCoins.setTextSize(12);
         textDistance.setTextSize(12);
         collectedCoins = 0;
         distance =0;
         life=3;
         textDistance.setText("Distance: "+distance);
         textCoins.setText("Coins: "+collectedCoins);
     }









    // handle battleship movement
     private void moveBattleship() {
         button_RIGHT.setOnClickListener(v -> {
             if(battleship_position==0){
                 battleship_Images[2].setVisibility(View.INVISIBLE);
                 battleship_Images[3].setVisibility(View.VISIBLE);
                 battleship_position++;
                 //Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==-1){
                 battleship_Images[1].setVisibility(View.INVISIBLE);
                 battleship_Images[2].setVisibility(View.VISIBLE);
                 battleship_position++;
                 //Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==1){
                 battleship_Images[3].setVisibility(View.INVISIBLE);
                 battleship_Images[4].setVisibility(View.VISIBLE);
                 battleship_position++;
                 //Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==-2){
                 battleship_Images[0].setVisibility(View.INVISIBLE);
                 battleship_Images[1].setVisibility(View.VISIBLE);
                 battleship_position++;
                 //Log.d("battleship position changed","the position is "+ battleship_position);
             }
         });
         button_LEFT.setOnClickListener(v -> {
             if(battleship_position==1){
                 battleship_Images[3].setVisibility(View.INVISIBLE);
                 battleship_Images[2].setVisibility(View.VISIBLE);
                 battleship_position--;
                 //Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==0){
                 battleship_Images[2].setVisibility(View.INVISIBLE);
                 battleship_Images[1].setVisibility(View.VISIBLE);
                 battleship_position--;
                 //Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==-1){
                 battleship_Images[1].setVisibility(View.INVISIBLE);
                 battleship_Images[0].setVisibility(View.VISIBLE);
                 battleship_position--;
               //  Log.d("battleship position changed","the position is "+ battleship_position);
             }
             else if(battleship_position==2){
                 battleship_Images[4].setVisibility(View.INVISIBLE);
                 battleship_Images[3].setVisibility(View.VISIBLE);
                 battleship_position--;
             //    Log.d("battleship position changed","the position is "+ battleship_position);
             }
         });

     }








    // start a thread that handle the game process
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
                 handler.postDelayed(this, 700);
             }
         };
         handler.post(runnable);
     }




     // start the game - falling rocks and coin on  defined matrix
     private void startGame() throws InterruptedException {
         // handle cases - coins collect, distance counter and rocks crush
         handleDistance();
         handleCoins();
         handleRocks();
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


    //count distance
     private void handleDistance() {
         distance++;
         textDistance.setText("Distance: "+distance);

     }

    // delete heart in case of crushing a rock
     private void handleRocks() {
         if (    (rocks_Images[8].getVisibility()== View.VISIBLE && battleship_position==-2) ||
                 (rocks_Images[17].getVisibility()== View.VISIBLE && battleship_position==-1) ||
                 (rocks_Images[26].getVisibility()== View.VISIBLE && battleship_position==0) ||
                 (rocks_Images[35].getVisibility()== View.VISIBLE && battleship_position==1) ||
                 (rocks_Images[44].getVisibility()== View.VISIBLE && battleship_position==2)){

             try {
                 mediaPlayer.start();
                 deleteHeart();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

         }

     }

    // add coins
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

    // delete heart and create vibrate - finish the game when 1 heart left
     private void deleteHeart() throws InterruptedException {
         boolean bool = rocks_Images[8].getVisibility()== View.VISIBLE;
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
    // handle when game is over and produce new game automaticly
     private void gameOver() throws InterruptedException {
         Toast.makeText(this, "Restart in 2 sec", Toast.LENGTH_SHORT).show();
         Thread.sleep(2000);
         Score score = new Score(collectedCoins,distance,lat,lon); // create score
         ScoreList.getInstance().addScore(getApplicationContext(), score); // add score to list and show it in ListFragment
         InitGame(); // init game after - game over
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
     // support function to find GPS
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