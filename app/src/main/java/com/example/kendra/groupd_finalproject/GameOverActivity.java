package com.example.kendra.groupd_finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {

    Intent fromGame, sendToGame;

    String username;
    int score;
    int level;
    Button saveBtn, playAgainBtn;
    Statement statement;
    String insertQuery = "";
    String selectQuerry = "SELECT * FROM highScores ORDER BY score DESC LIMIT 5;";
    TextView confirmed, finalscoreTV;
    ListView myList, myList2;

    CountDownTimer invisibleTimer;

    DBHelper myDBHelper;
    SQLiteDatabase db;

    ArrayList<Integer> idList;
    ArrayList<String > usernameList;
    ArrayList<Integer> scoreList;
    ArrayAdapter myListAdapter, myListAdapter2;
    ArrayList<String> comboList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        fromGame = getIntent();
        username = fromGame.getStringExtra("username");
        score = fromGame.getIntExtra("score",0);
        level = fromGame.getIntExtra("level",0);
        saveBtn = findViewById(R.id.saveBtn);
        playAgainBtn = findViewById(R.id.againBtn);
        confirmed = findViewById(R.id.confirmed);
        myList = findViewById(R.id.myList);
        myList2 = findViewById(R.id.myList2);
        finalscoreTV = findViewById((R.id.finalscoreTv));

        final String scoreData = username + "         " + String.valueOf(score);

        finalscoreTV.setText(scoreData);

        sendToGame = new Intent(this, GameActivity.class);

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

        getResult(selectQuerry);




        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    insertQuery = "INSERT INTO highScores (username, score) " +
                            "VALUES(" + "'" + username + "'" + "," + score + ");";
                    db.execSQL(insertQuery);

                    confirmed.setText("SCORE SAVED!");
                    confirmed.setVisibility(View.VISIBLE);
                    makeInvisible();
                }
                catch (SQLException e)
                {
                    confirmed.setText("SCORE NOT SAVED!");
                    confirmed.setVisibility(View.VISIBLE);
                    makeInvisible();
                }
            }
        });

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToGame.putExtra("username", username);

                GameOverActivity.this.startActivity(sendToGame);
            }
        });

    }

    public void getResult(String q) {
        Cursor result = db.rawQuery(q, null);
        result.moveToFirst();
        int count = result.getCount();
        Log.i("count=", String.valueOf(count));

        idList = new ArrayList<>();
        usernameList = new ArrayList<>();
        scoreList = new ArrayList<>();

        if (count >= 1){
            //I have results

            do{
                idList.add(result.getInt(0));
                usernameList.add(result.getString(1));
                scoreList.add(result.getInt(2));

            }while (result.moveToNext());

        }
        myListAdapter = new ArrayAdapter<String>(this, R.layout.list_item, usernameList);
        myList.setAdapter(myListAdapter);

        myListAdapter2 = new ArrayAdapter<Integer>(this, R.layout.list_item, scoreList);
        myList2.setAdapter(myListAdapter2);
    }

    public void makeInvisible()
    {
        invisibleTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                confirmed.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
    }

}
