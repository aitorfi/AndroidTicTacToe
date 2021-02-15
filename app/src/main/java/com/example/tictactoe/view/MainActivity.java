package com.example.tictactoe.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.R;

public class MainActivity extends AppCompatActivity {

    public static final int SINGLE_PLAYER = 1;
    public static final int MULTI_PLAYER = 2;

    private ConstraintLayout constraintLayout;
    private Button buttonClassicSinglePlayer, buttonClassicMultiPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.constraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        buttonClassicSinglePlayer = findViewById(R.id.buttonSinglePlayerClassicGame);
        buttonClassicSinglePlayer.setOnClickListener((View v) -> {
            Intent intentClassicSinglePlayer = new Intent(
                    MainActivity.this, ClassicTicTacToeActivity.class);
            intentClassicSinglePlayer.putExtra("GAME_MODE", SINGLE_PLAYER);
            startActivity(intentClassicSinglePlayer);
        });

        buttonClassicMultiPlayer = findViewById(R.id.buttonMultiPlayerClassicGame);
        buttonClassicMultiPlayer.setOnClickListener((View v) -> {
            Intent intentClassicSinglePlayer = new Intent(
                    MainActivity.this, ClassicTicTacToeActivity.class);
            intentClassicSinglePlayer.putExtra("GAME_MODE", MULTI_PLAYER);
            startActivity(intentClassicSinglePlayer);
        });
    }
}