package com.example.kendra.groupd_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends Activity {

    //variable declaration
    ImageView dino1;
    ImageView dino2;
    ImageView dino3;
    ImageView dino4;
    ImageView dino5;
    TextView usernameTV;
    TextView livesTV;
    TextView scoreTV;
    TextView lvlTV;
    TextView levelChange;
    RelativeLayout gameSpace;

    CountDownTimer timer;

    Random randNum;

    ImageView image;

    Intent fromLogin;
    Intent toGameOver;
    Intent fromGameOver;

    int lives =3;
    int score =0;
    int level =1;
    int xPos =1;
    int imgInd =0;
    int imgID = 100;

    int timerMill;
    int timerTick;
    int moveDist;
    String username;

    SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        randNum = new Random();

        //set all layout variables and intent
        usernameTV = findViewById(R.id.userNameTV);
        livesTV = findViewById(R.id.livesTV);
        scoreTV = findViewById(R.id.scoreTV);
        lvlTV = findViewById(R.id.lvlTV);
        gameSpace = findViewById(R.id.gameSpace);
        toGameOver = new Intent(this, GameOverActivity.class);

        levelChange = findViewById(R.id.levelchange);
        dino1 = findViewById(R.id.dino1);
        dino2 = findViewById(R.id.dino2);
        dino3 = findViewById(R.id.dino3);
        dino4 = findViewById(R.id.dino4);
        dino5 = findViewById(R.id.dino5);

        dino2.setVisibility(View.GONE);
        dino4.setVisibility(View.GONE);

        //calls Intents from Login and GameOver
        //sets username in user banner
        fromLogin = getIntent();
        fromGameOver = getIntent();
        if (fromLogin.getStringExtra("username") != null) {
            username = fromLogin.getStringExtra("username");
            usernameTV.setText(username);
        } else {
            username = fromGameOver.getStringExtra("username");
            usernameTV.setText(username);
        }


        //sets Lives in user banner
        livesTV.setText(Integer.toString(lives));

        //initialize timer and move variables
        timerMill = 50000;
        timerTick = 100;
        moveDist = 20;

        //start the meteors and initial timer
        newImage(xPos, imgID);
        timerMethod();

        //onclick listener for clicking images
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success();
                }

        });

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    //creates the first image
    public void newImage(int pos, int id) {
        image = new ImageView(this);
        image.setLayoutParams(new RelativeLayout.LayoutParams(300,300));
        image.setId(id);
        image.setY(0);
        image.setX(pos * 100);
        image.setImageResource(R.drawable.meteor);
        gameSpace.addView(image);
    }

    //sets new meteors to drop
    public void changeImg() {
        randNum = new Random();
        int totalX= (gameSpace.getWidth()-200);
        imgInd = randNum.nextInt(2);
        xPos = randNum.nextInt(totalX);
        image.setImageResource(R.drawable.meteor);
        image.setY(0);
        image.setX(xPos);
    }

    //timer method to move images down the screen per our timer variables
    public void timerMethod() {
        timer = new CountDownTimer(timerMill,timerTick) {
            @Override
            public void onTick(long millisUntilFinished) {
                //move images on each tick
                image.setVisibility(View.VISIBLE);
                image.setY(image.getY() + moveDist);
                //check if image is still on screen and call failureCheck
                if (image.getY() >= gameSpace.getHeight())
                {
                    timer.cancel();
                    image.setVisibility(View.GONE);
                    failureCheck();
                }

            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    //success() is called in image onClick
    //update score, call new image and check level on successful click
    public void success() {
        timer.cancel();
        image.setVisibility(View.GONE);
        score += 1;
        scoreTV.setText("Score: " + Integer.toString(score));
        changeImg();
        timerMethod();
        levelChange();
    }

    //levelChange() is called in success()
    //check score and update level every 15 points
    //show hidden TextView with new level
    public void levelChange() {
        if (score%15 ==0) {
            level += 1;
            lvlTV.setText("Level: " +String.valueOf(level));
            levelChange.setText("LEVEL " + String.valueOf(level));
            levelChange.setVisibility(View.VISIBLE);
            CountDownTimer lvlTimer = new CountDownTimer(2000, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    levelChange.setVisibility(View.GONE);
                }
            }.start();
            addLives();
            speedUp();

        }
    }

    //addLives() is called in levelChange()
    //add lives every 4 levels
    public void addLives() {
        if (lives < 5) {
            if (level % 4 == 0) {
                lives += 1;
                livesTV.setText(String.valueOf(lives));
                showDinos();
            }
        }
    }

    //speedUp() is called in levelChange()
    //changes timer variables to speed up timer and increase distance image moves
    public void speedUp() {
        if (timerTick > 20) {
            if (level%2 ==0) {
                timerTick -= 20;
                moveDist += 5;
            }
        }
    }

    //failureCheck() is called in TimerMethod()
    //reduced lives and dinos on fail
    //intent to send to GameOver when lives ==0
    public void failureCheck() {
        lives -=1;
        livesTV.setText(String.valueOf(lives));
        changeImg();
        timerMethod();
        showDinos();
        if (lives == 0) {

            toGameOver.putExtra("username", username);
            toGameOver.putExtra("score", score);
            toGameOver.putExtra("level", level);
            timer.cancel();

            GameActivity.this.startActivity(toGameOver);

        }
    }

    //showDinos() is called in failureCheck()
    //changes visibility of the 5 dino images on screen
    public void showDinos() {
        switch (lives) {
            case 5:
                dino1.setVisibility(View.VISIBLE);
                dino2.setVisibility(View.VISIBLE);
                dino3.setVisibility(View.VISIBLE);
                dino4.setVisibility(View.VISIBLE);
                dino5.setVisibility(View.VISIBLE);
                break;
            case 4:
                dino1.setVisibility(View.VISIBLE);
                dino2.setVisibility(View.GONE);
                dino3.setVisibility(View.VISIBLE);
                dino4.setVisibility(View.VISIBLE);
                dino5.setVisibility(View.VISIBLE);
                break;
            case 3:
                dino1.setVisibility(View.VISIBLE);
                dino2.setVisibility(View.GONE);
                dino3.setVisibility(View.VISIBLE);
                dino4.setVisibility(View.GONE);
                dino5.setVisibility(View.VISIBLE);
                break;
            case 2:
                dino1.setVisibility(View.VISIBLE);
                dino2.setVisibility(View.GONE);
                dino3.setVisibility(View.GONE);
                dino4.setVisibility(View.GONE);
                dino5.setVisibility(View.VISIBLE);
                break;
            case 1:
                dino1.setVisibility(View.VISIBLE);
                dino2.setVisibility(View.GONE);
                dino3.setVisibility(View.GONE);
                dino4.setVisibility(View.GONE);
                dino5.setVisibility(View.GONE);
                break;
        }
    }



    //disable back button
    @Override
    public void onBackPressed() {
    }

}
