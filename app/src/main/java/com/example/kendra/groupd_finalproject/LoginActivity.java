package com.example.kendra.groupd_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    TextView error;
    EditText unTV;
    Button playBtn;
    String errorStr = "Enter a username to continue";
    String username;
    Intent myIntent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        error = findViewById(R.id.error);
        unTV = findViewById(R.id.unTV);
        playBtn = findViewById(R.id.playBtn);

        error.setVisibility(View.GONE);

        myIntent1 = new Intent(this, GameActivity.class);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = unTV.getText().toString();
                if (username.equals(""))
                {
                    error.setText(errorStr);
                    error.setVisibility(View.VISIBLE);
                }
                else {
                    error.setVisibility(View.GONE);
                    myIntent1.putExtra("username", username);
                    LoginActivity.this.startActivity(myIntent1);
                }
            }
        });


    }
}
