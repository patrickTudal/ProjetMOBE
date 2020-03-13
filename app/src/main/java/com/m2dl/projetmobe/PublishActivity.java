package com.m2dl.projetmobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.m2dl.projetmobe.Board.BoardView;
import com.m2dl.projetmobe.Firebase.FirebaseService;

public class PublishActivity extends AppCompatActivity {

    TextInputEditText inputPseudo ;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        final String score = getIntent().getStringExtra("score");
        sendButton = findViewById(R.id.sendScore);
        inputPseudo = findViewById(R.id.inputIdentificationLogin);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseService.publishScore(score,inputPseudo.getText().toString());
                startActivity(new Intent(PublishActivity.this,MainActivity.class));
            }
        });
    }
}
