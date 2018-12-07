package com.example.kendra.groupd_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends Activity {

    ImageView dino1;
    ImageView dino2;
    ImageView dino3;
    ImageView dino4;
    ImageView dino5;

    TextView usernameTV;
    TextView livesTV;
    TextView scoreTV;
    TextView lvlTV;
    RelativeLayout gameSpace;

    CountDownTimer timer;

    Random randNum;

    ImageView image;

    Intent fromLogin;
    Intent toGameOver;

    int lives =3;
    int score =0;
    int level =1;
    int xPos =1;
    int imgInd =0;
    int imgID = 100;
    String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        randNum = new Random();

        usernameTV = findViewById(R.id.userNameTV);
        livesTV = findViewById(R.id.livesTV);
        scoreTV = findViewById(R.id.scoreTV);
        lvlTV = findViewById(R.id.lvlTV);
        gameSpace = findViewById(R.id.gameSpace);

        dino1 = findViewById(R.id.dino1);
        dino2 = findViewById(R.id.dino2);
        dino3 = findViewById(R.id.dino3);
        dino4 = findViewById(R.id.dino4);
        dino5 = findViewById(R.id.dino5);

        dino2.setVisibility(View.GONE);
        dino4.setVisibility(View.GONE);

        fromLogin = getIntent();
        username = fromLogin.getStringExtra("username");
        usernameTV.setText(username);

        livesTV.setText(Integer.toString(lives));


        newImage(xPos, imgID);
        timerMethod();


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                image.setVisibility(View.GONE);
                score += 1;
                scoreTV.setText("Score: " + Integer.toString(score));
                changeImg();
                timerMethod();
                }

        });





    }

    public void newImage(int pos, int id) {
        image = new ImageView(this);
        image.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
        image.setId(id);
        image.setY(0);
        image.setX(pos * 100);
        image.setImageResource(R.drawable.meteor);
        gameSpace.addView(image);
    }

    public void changeImg() {
        randNum = new Random();
        int totalX= (gameSpace.getWidth()-200);
        imgInd = randNum.nextInt(2);
        xPos = randNum.nextInt(totalX);
        image.setImageResource(R.drawable.meteor);
        image.setY(0);
        image.setX(xPos);
    }

    public void timerMethod() {
        timer = new CountDownTimer(3000,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                image.setVisibility(View.VISIBLE);
                image.setY(image.getY() + 50);
                if (image.getY() >= gameSpace.getHeight())
                {
                    timer.cancel();
                    image.setVisibility(View.GONE);
                    failure();
                }

            }

            @Override
            public void onFinish() {
                changeImg();
                timer.start();

            }
        }.start();
    }

    public void failure() {
        lives -=1;
        livesTV.setText(String.valueOf(lives));
        changeImg();
        timerMethod();
        if (lives == 0) {
            toGameOver = new Intent(this, GameOverActivity.class);
            toGameOver.putExtra("username", username);
            toGameOver.putExtra("score", score);
            toGameOver.putExtra("level", level);

            GameActivity.this.startActivity(toGameOver);

        }
    }


    @Override
    public void onBackPressed() {
    }


}
