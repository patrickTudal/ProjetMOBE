package com.m2dl.projetmobe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.m2dl.projetmobe.Firebase.FirebaseService;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button playButton;
    static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.playButton);
        textView = findViewById(R.id.scores);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        FirebaseService.getAllScores();
    }

    public static void callbackImagesFirebase(ArrayList<HashMap<String,String>> value) {
        String message = "";
        for(int i =0; i<value.size();i++){
             HashMap<String, String> highScoreDAO = value.get(i);
             if(highScoreDAO!=null)
                 message+=highScoreDAO.get("score")+" "+highScoreDAO.get("pseudo")+"\n";
        }
        textView.setText(message);
    }
}
