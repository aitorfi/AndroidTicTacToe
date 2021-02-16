package com.example.tictactoe.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tictactoe.R;
import com.example.tictactoe.game.Game;

import java.util.ArrayList;

public class ClassicTicTacToeActivity extends AppCompatActivity {

    private Game game;
    private ConstraintLayout constraintLayout;
    private ImageButton imageButton0_0, imageButton0_1, imageButton0_2,
                        imageButton1_0, imageButton1_1, imageButton1_2,
                        imageButton2_0, imageButton2_1, imageButton2_2;
    private Button buttonRestartGame;
    private TextView textViewScorePlayerX, textViewScorePlayerO;
    private ImageView imageViewScoreboardX, imageViewScoreboardO;
    private YoYo.YoYoString scoreboardXAnimation, scoreboardOAnimation;
    private ArrayList<ImageButton> boardButtons = new ArrayList<>();
    private int scorePlayerX, scorePlayerO;
    private boolean isTurnX = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_tictactoe);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));
        //Display Back button on ActionBar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Hide application title.
        actionBar.setDisplayShowTitleEnabled(false);

        //Initializing layout background animation.
        constraintLayout = findViewById(R.id.classicTictactoeConstraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //Initializing game and scoreboard.
        game = new Game();
        int gameMode = (int) getIntent().getExtras().get("GAME_MODE");
        scorePlayerX = 0;
        scorePlayerO = 0;
        textViewScorePlayerX = findViewById(R.id.textViewScorePlayerX);
        textViewScorePlayerO = findViewById(R.id.textViewScorePlayerO);
        imageViewScoreboardX = findViewById(R.id.imageViewScoreboardX);
        imageViewScoreboardO = findViewById(R.id.imageViewScoreboardO);
        startScoreboardAnimation(true);

        //Initializing board buttons.
        imageButton0_0 = findViewById(R.id.imageButton0_0);
        boardButtons.add(imageButton0_0);
        imageButton0_1 = findViewById(R.id.imageButton0_1);
        boardButtons.add(imageButton0_1);
        imageButton0_2 = findViewById(R.id.imageButton0_2);
        boardButtons.add(imageButton0_2);
        imageButton1_0 = findViewById(R.id.imageButton1_0);
        boardButtons.add(imageButton1_0);
        imageButton1_1 = findViewById(R.id.imageButton1_1);
        boardButtons.add(imageButton1_1);
        imageButton1_2 = findViewById(R.id.imageButton1_2);
        boardButtons.add(imageButton1_2);
        imageButton2_0 = findViewById(R.id.imageButton2_0);
        boardButtons.add(imageButton2_0);
        imageButton2_1 = findViewById(R.id.imageButton2_1);
        boardButtons.add(imageButton2_1);
        imageButton2_2 = findViewById(R.id.imageButton2_2);
        boardButtons.add(imageButton2_2);

        boardButtons.stream().forEach(button -> {
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setImageAlpha(0);

            button.setOnClickListener((View v) -> {
                if(gameMode == MainActivity.SINGLE_PLAYER) {
                    //On click listener for single player game mode.
                    game.play(true, boardButtons.indexOf(button));
                    button.setImageResource(R.drawable.x_player);
                    button.setImageAlpha(255);
                    button.setEnabled(false);
                    if(game.getGameStatus() == Game.GAME_NOT_FINISHED) {
                        int counterPlay = game.counterPlay();
                        boardButtons.get(counterPlay).setImageResource(R.drawable.o_player);
                        boardButtons.get(counterPlay).setImageAlpha(255);
                        boardButtons.get(counterPlay).setEnabled(false);
                    }
                } else { //gameMode == MainActivity.MULTI_PLAYER
                    game.play(isTurnX, boardButtons.indexOf(button));
                    if(isTurnX)
                        button.setImageResource(R.drawable.x_player);
                    else
                        button.setImageResource(R.drawable.o_player);
                    stopScoreboardAnimation(isTurnX);
                    startScoreboardAnimation(!isTurnX);
                    button.setImageAlpha(255);
                    button.setEnabled(false);
                    isTurnX = !isTurnX;

                }
                if(game.getGameStatus() != Game.GAME_NOT_FINISHED)
                    handleGameOver();
            });
        });

        buttonRestartGame = findViewById(R.id.buttonRestartGame);
        buttonRestartGame.setVisibility(View.INVISIBLE);
        buttonRestartGame.setOnClickListener((View v) -> {
            game = new Game();
            buttonRestartGame.setVisibility(View.INVISIBLE);
            boardButtons.stream().forEach(button -> {
                button.setImageAlpha(0);
                button.setEnabled(true);
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles game over by making the needed changes on the board and scoreboard.
     */
    private void handleGameOver() {
        int gameStatus = game.getGameStatus();

        //Disable all board buttons.
        boardButtons.stream().forEach(b -> b.setEnabled(false));
        buttonRestartGame.setVisibility(View.VISIBLE);

        //Updating scoreboard.
        if(gameStatus == Game.X_WON) {
            scorePlayerX++;
            if(scorePlayerX >= 10) textViewScorePlayerX.setText(String.valueOf(scorePlayerX));
            else textViewScorePlayerX.setText("0" + String.valueOf(scorePlayerX));
        } else if(gameStatus == Game.O_WON) {
            scorePlayerO++;
            if(scorePlayerO >= 10) textViewScorePlayerO.setText(String.valueOf(scorePlayerO));
            else textViewScorePlayerO.setText("0" + String.valueOf(scorePlayerO));
        }
        //Else it is a draw and nothing happens on the scoreboard.
    }

    private void stopScoreboardAnimation(boolean stopX) {
        if(stopX)
            scoreboardXAnimation.stop();
        else
            scoreboardOAnimation.stop();
    }

    private void startScoreboardAnimation(boolean startX) {
        if(startX) {
            scoreboardXAnimation = YoYo.with(Techniques.Tada)
                    .repeat(YoYo.INFINITE).duration(2500).playOn(imageViewScoreboardX);
        } else {
            scoreboardOAnimation = YoYo.with(Techniques.Tada)
                    .repeat(YoYo.INFINITE).duration(2500).playOn(imageViewScoreboardO);
        }
    }
}