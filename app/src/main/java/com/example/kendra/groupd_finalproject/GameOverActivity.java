package com.example.kendra.groupd_finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

    Intent fromGame;

    String username;
    int score;
    int level;
    Button saveBtn;
    Statement statement;
    String insertQuery = "";
    String selectQuerry = "SELECT TOP 5 FROM highSCores";
    TextView confirmed;
    ListView myList;

    DBHelper myDBHelper;
    SQLiteDatabase db;

    ArrayList<Integer> idList;
    ArrayList<String > usernameList;
    ArrayList<Integer> scoreList;
    ArrayAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        fromGame = getIntent();
        username = fromGame.getStringExtra("username");
        score = fromGame.getIntExtra("score",0);
        level = fromGame.getIntExtra("level",0);
        saveBtn = findViewById(R.id.saveBtn);
        confirmed = findViewById(R.id.confirmed);
        myList = findViewById(R.id.myList);

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

        } else {
            //no results

        }
        myListAdapter = new ArrayAdapter<String>(this, R.layout.list_item, usernameList);
        myList.setAdapter(myListAdapter);
    }

}
