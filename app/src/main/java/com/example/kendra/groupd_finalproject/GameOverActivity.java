package com.example.kendra.groupd_finalproject;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kendra.groupd_finalproject.DBHelper;
import java.io.IOException;
import java.sql.Statement;

public class GameOverActivity extends AppCompatActivity {

    Intent myIntent4;

    String username;
    int score;
    int level;
    Button saveBtn;
    Statement statement;
    String insertQuery = "";
    TextView confirmed;

    DBHelper myDBHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        myIntent4 = getIntent();
        username = myIntent4.getStringExtra("username");
        score = myIntent4.getIntExtra("score",0);
        level = myIntent4.getIntExtra("level",0);
        saveBtn = findViewById(R.id.saveBtn);
        confirmed = findViewById(R.id.confirmed);

        myDBHelper = new DBHelper(this);

        try {
            myDBHelper.createDataBase();
        }   catch (IOException e){
            throw new Error("Unable to Connect");
        }
        try {
            myDBHelper.openDataBase();
        }   catch (SQLException sql){

        }
        db = myDBHelper.getReadableDatabase();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    insertQuery = "INSERT  INTO highScores (username, scores) " +
                            "VALUES(" + "'" + username + "'" + "," + score + "," + ");";

                    confirmed.setText("SCORE SAVED!");
                    confirmed.setVisibility(View.VISIBLE);
                }
                catch (SQLException e)
                {
                    confirmed.setText("SCORE NOT SAVED!");
                    confirmed.setVisibility(View.VISIBLE);
                }

            }
        });
    }


}
