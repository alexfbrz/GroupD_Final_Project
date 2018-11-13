package com.example.kendra.groupd_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends Activity {

    TextView usernameTV;
    TextView livesTV;
    TextView scoreTV;
    TextView lvlTV;
    RelativeLayout gameSpace;

    CountDownTimer timer;

    Random randNum;

    ImageView image;

    Intent myIntent2;
    Intent myIntent3;

    int lives;
    int score;
    int xPos =1;
    int imgInd =0;

    int[] imgArr ={R.drawable.hearticon, R.drawable.dino};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        randNum = new Random();

        usernameTV = findViewById(R.id.userNameTV);
        livesTV = findViewById(R.id.livesTV);
        scoreTV = findViewById(R.id.scoreTV);
        lvlTV = findViewById(R.id.lvlTV);
        gameSpace = findViewById(R.id.gameSpace);

        myIntent2 = getIntent();
        String username = myIntent2.getStringExtra("username");
        usernameTV.setText(username);
        
    }


    @Override
    public void onBackPressed() {
    }


}
