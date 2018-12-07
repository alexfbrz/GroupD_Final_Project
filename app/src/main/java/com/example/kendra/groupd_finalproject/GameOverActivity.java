package com.example.kendra.groupd_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameOverActivity extends AppCompatActivity {

    Intent myIntent4;

    String username;
    int score;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        myIntent4 = getIntent();
        username = myIntent4.getStringExtra("username");
        score = myIntent4.getIntExtra("score",0);
        level = myIntent4.getIntExtra("level",0);

    }
}
