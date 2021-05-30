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
import com.example.tictactoe.game.ClassicTicTacToeGame;

import java.util.ArrayList;

/**
 * Activity that controls the flow of the classic Tic Tac Toe game.
 *
 * @author Aitor Fidalgo (aitorfi on GitHub)
 */
public class ClassicTictactoeActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ConstraintLayout constraintLayout;
    private ImageButton imageButton0_0, imageButton0_1, imageButton0_2,
                        imageButton1_0, imageButton1_1, imageButton1_2,
                        imageButton2_0, imageButton2_1, imageButton2_2;
    private Button buttonRestartGame;
    private TextView textViewScorePlayerX, textViewScorePlayerO;
    private ImageView imageViewScoreboardX, imageViewScoreboardO;

    private ArrayList<ImageButton> boardButtons = new ArrayList<>();
    private int scorePlayerX, scorePlayerO;
    private int gameMode;

    private ClassicTicTacToeGame game;
    private YoYo.YoYoString scoreboardXAnimation, scoreboardOAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_tictactoe);

        actionBar               = getSupportActionBar();
        constraintLayout        = findViewById(R.id.classicTictactoeConstraintLayout);
        imageButton0_0          = findViewById(R.id.imageButton0_0);
        imageButton0_1          = findViewById(R.id.imageButton0_1);
        imageButton0_2          = findViewById(R.id.imageButton0_2);
        imageButton1_0          = findViewById(R.id.imageButton1_0);
        imageButton1_1          = findViewById(R.id.imageButton1_1);
        imageButton1_2          = findViewById(R.id.imageButton1_2);
        imageButton2_0          = findViewById(R.id.imageButton2_0);
        imageButton2_1          = findViewById(R.id.imageButton2_1);
        imageButton2_2          = findViewById(R.id.imageButton2_2);
        buttonRestartGame       = findViewById(R.id.buttonRestartGameClassic);
        textViewScorePlayerX    = findViewById(R.id.textViewScorePlayerX);
        textViewScorePlayerO    = findViewById(R.id.textViewScorePlayerO);
        imageViewScoreboardX    = findViewById(R.id.imageViewScoreboardX);
        imageViewScoreboardO    = findViewById(R.id.imageViewScoreboardO);

        boardButtons.add(imageButton0_0);
        boardButtons.add(imageButton0_1);
        boardButtons.add(imageButton0_2);
        boardButtons.add(imageButton1_0);
        boardButtons.add(imageButton1_1);
        boardButtons.add(imageButton1_2);
        boardButtons.add(imageButton2_0);
        boardButtons.add(imageButton2_1);
        boardButtons.add(imageButton2_2);
        scorePlayerX = 0;
        scorePlayerO = 0;
        gameMode = (int) getIntent().getExtras().get("GAME_MODE");

        game = new ClassicTicTacToeGame();
        startScoreboardAnimation(game.isTurnX());


        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));
        //Display Back button on ActionBar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //Initializing layout background animation.
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        boardButtons.stream().forEach(button -> {
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setImageAlpha(0);

            button.setOnClickListener((View v) -> {
                int gameStatus;

                if(gameMode == MainActivity.SINGLE_PLAYER)
                    gameStatus = playSinglePlayerMode(button);
                else
                    gameStatus = playMultiPlayerMode(button);

                if(gameStatus != ClassicTicTacToeGame.GAME_NOT_FINISHED)
                    handleGameOver(gameStatus);
            });
        });

        buttonRestartGame.setVisibility(View.INVISIBLE);
        buttonRestartGame.setOnClickListener((View v) -> {
            handleButtonRestartGame();
        });
    }

    /**
     * Makes a play in the single player mode and lets the AI play.
     *
     * @param button The button chosen by the user to make the play.
     */
    private int playSinglePlayerMode(ImageButton button) {
        int gameStatus;

        // gameStatus = game.play(true, boardButtons.indexOf(button));
        gameStatus = game.play(boardButtons.indexOf(button));
        button.setImageResource(R.drawable.x_player);
        button.setImageAlpha(255);
        button.setEnabled(false);
        if(gameStatus == ClassicTicTacToeGame.GAME_NOT_FINISHED) {
            int counterPlay = game.counterPlay();
            boardButtons.get(counterPlay).setImageResource(R.drawable.o_player);
            boardButtons.get(counterPlay).setImageAlpha(255);
            boardButtons.get(counterPlay).setEnabled(false);
            gameStatus = game.getGameStatus();
        }

        return gameStatus;
    }

    /**
     * Makes a play in the multi player mode.
     *
     * @param button The button chosen to make the play.
     */
    private int playMultiPlayerMode(ImageButton button) {
        int gameStatus;
        boolean isTurnX;

        // It is important to get the isTurnX variable before calling game.play()
        // because it changes its value after making a move in the board.
        isTurnX = game.isTurnX();
        gameStatus = game.play(boardButtons.indexOf(button));

        if(isTurnX)
            button.setImageResource(R.drawable.x_player);
        else
            button.setImageResource(R.drawable.o_player);

        stopScoreboardAnimation(isTurnX);
        startScoreboardAnimation(!isTurnX);
        button.setImageAlpha(255);
        button.setEnabled(false);

        return gameStatus;
    }

    /**
     * Handles game over by making the needed changes on the board and scoreboard.
     */
    private void handleGameOver(int gameStatus) {
        //Disable all board buttons.
        boardButtons.stream().forEach(b -> b.setEnabled(false));
        buttonRestartGame.setVisibility(View.VISIBLE);

        //Updating scoreboard.
        if(gameStatus == ClassicTicTacToeGame.X_WON) {
            scorePlayerX++;
            if(scorePlayerX >= 10) textViewScorePlayerX.setText(String.valueOf(scorePlayerX));
            else textViewScorePlayerX.setText("0" + String.valueOf(scorePlayerX));
        } else if(gameStatus == ClassicTicTacToeGame.O_WON) {
            scorePlayerO++;
            if(scorePlayerO >= 10) textViewScorePlayerO.setText(String.valueOf(scorePlayerO));
            else textViewScorePlayerO.setText("0" + String.valueOf(scorePlayerO));
        }
        //Else it is a draw and nothing happens on the scoreboard.
    }

    /**
     * Resets the game keeping the score.
     */
    private void handleButtonRestartGame() {
        game = new ClassicTicTacToeGame();
        buttonRestartGame.setVisibility(View.INVISIBLE);
        boardButtons.stream().forEach(button -> {
            button.setImageAlpha(0);
            button.setEnabled(true);
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
     * Stops the scoreboard animation on the specified image.
     *
     * @param stopX True for stopping X shape image animation, false for the O shape image.
     */
    private void stopScoreboardAnimation(boolean stopX) {
        if(stopX)
            scoreboardXAnimation.stop();
        else
            scoreboardOAnimation.stop();
    }

    /**
     * Starts the scoreboard animation on the specified image.
     *
     * @param startX True for starting the X shape image animation, false for the O shape image.
     */
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