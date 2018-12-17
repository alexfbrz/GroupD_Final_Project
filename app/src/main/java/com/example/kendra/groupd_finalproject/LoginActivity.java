package com.example.kendra.groupd_finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    //Variable declaration
    TextView error;
    EditText unTV;
    Button playBtn;
    String errorStr = "Enter a username to continue";
    String username;
    Intent toGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //variable assignments
        error = findViewById(R.id.error);
        unTV = findViewById(R.id.unTV);
        playBtn = findViewById(R.id.playBtn);

        error.setVisibility(View.GONE);

        //intenet transition call
        toGame = new Intent(this, GameActivity.class);

        //Listener to move to the main game when the play button is clicked
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = unTV.getText().toString();

                //checks to see if a username was entered
                //allows you to proceed if you entered one
                if (username.equals(""))
                {
                    error.setText(errorStr);
                    error.setVisibility(View.VISIBLE);
                }
                //displays an error if no username was entered
                else {
                    error.setVisibility(View.GONE);
                    toGame.putExtra("username", username);
                    LoginActivity.this.startActivity(toGame);
                }
            }
        });
    }
}
