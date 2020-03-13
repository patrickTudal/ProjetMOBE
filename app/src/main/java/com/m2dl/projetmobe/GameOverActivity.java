package com.m2dl.projetmobe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class GameOverActivity extends AppCompatActivity {

    private Button retryButton;
    private Button menuButton;
    private Button publishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        TextView pointText = findViewById(R.id.pointText);
        pointText.setText(getIntent().getStringExtra("score"));
        manageButtonsActivity();
    }

    private void manageButtonsActivity() {
        publishButton = findViewById(R.id.publishButton);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, PublishActivity.class);
                intent.putExtra("score",getIntent().getStringExtra("score"));
                startActivity(intent);
            }
        });
        retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, MainActivity.class));
            }
        });
        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, MainActivity.class));
            }
        });
    }

}
