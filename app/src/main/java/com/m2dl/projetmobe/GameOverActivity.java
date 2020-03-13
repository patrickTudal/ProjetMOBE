package com.m2dl.projetmobe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        TextView tv = findViewById(R.id.gameOverText);
        tv.setText(tv.getText() + " " + getIntent().getStringExtra("score"));
    }

}
