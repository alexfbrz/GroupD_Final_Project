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

    int lives =3;
    int score =0;
    int level =1;
    int xPos =1;
    int imgInd =0;
    int imgID = 100;

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

        livesTV.setText(Integer.toString(lives));

        newImage(imgArr[imgInd], xPos, imgID);

        timer = new CountDownTimer(3000,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                image.setVisibility(View.VISIBLE);
                image.setY(image.getY() +50);
            }

            @Override
            public void onFinish() {
                changeImg();
                timer.start();

            }
        }.start();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                image.setVisibility(View.GONE);
                score += 1;
                scoreTV.setText("Score: " + Integer.toString(score));
                changeImg();
                timer = new CountDownTimer(3000,50) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        image.setVisibility(View.VISIBLE);
                        image.setY(image.getY() +50);
                    }

                    @Override
                    public void onFinish() {
                        changeImg();
                        timer.start();

                    }
                }.start();

            }
        });

        if (lives ==0) {
            myIntent3 = new Intent(this, GameOverActivity.class);
            myIntent3.putExtra("username", username);
            myIntent3.putExtra("score", score);
            myIntent3.putExtra("level", level);

            GameActivity.this.startActivity(myIntent3);

        }

    }

    public void newImage(int img, int pos, int id) {
        image = new ImageView(this);
        image.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
        image.setId(id);
        image.setY(0);
        image.setX(pos * 100);
        image.setImageResource(img);
        gameSpace.addView(image);
    }

    public void changeImg() {
        int totalX= (gameSpace.getWidth())-200;
        imgInd = randNum.nextInt(2);
        xPos = randNum.nextInt(totalX);
        image.setImageResource(imgArr[imgInd]);
        image.setY(0);
        image.setX(xPos);
    }



    @Override
    public void onBackPressed() {
    }


}
