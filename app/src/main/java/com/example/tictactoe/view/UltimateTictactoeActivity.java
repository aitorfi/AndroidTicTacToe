package com.example.tictactoe.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

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
import com.example.tictactoe.game.UltimateTicTacToeGame;

import java.util.ArrayList;

/**
 * Activity that controls the flow of the ultimate Tic Tac Toe game.
 *
 * @author Aitor Fidalgo (aitofi on GitHub)
 */
public class UltimateTictactoeActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ConstraintLayout constraintLayout;
    private GridLayout gridLayoutMiniBoard;
    private ImageButton imageButton0_0, imageButton0_1, imageButton0_2,
            imageButton1_0, imageButton1_1, imageButton1_2,
            imageButton2_0, imageButton2_1, imageButton2_2;
    private Button buttonRestartGame, buttonBack;
    private ImageView imageViewUltimateBoard, imageViewClassicBoard, imageViewScoreboardX, imageViewScoreboardO;
    private TextView textViewScorePlayerX, textViewScorePlayerO;

    private ArrayList<ImageButton> boardButtons = new ArrayList<>();
    private ArrayList<ArrayList<ImageView>> miniBoardImages = new ArrayList<>();
    private boolean isTurnX;
    private int currentMiniBoardIndex, nextBoardStatus, scorePlayerX, scorePlayerO;

    private UltimateTicTacToeGame game;
    private YoYo.YoYoString scoreboardXAnimation, scoreboardOAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_tictactoe);

        actionBar               = getSupportActionBar();
        constraintLayout        = findViewById(R.id.ultimateTictactoeConstraintLayout);
        gridLayoutMiniBoard     = findViewById(R.id.gridLayoutUltimateMiniBoard);
        imageButton0_0          = findViewById(R.id.imageButtonUltimate0_0);
        imageButton0_1          = findViewById(R.id.imageButtonUltimate0_1);
        imageButton0_2          = findViewById(R.id.imageButtonUltimate0_2);
        imageButton1_0          = findViewById(R.id.imageButtonUltimate1_0);
        imageButton1_1          = findViewById(R.id.imageButtonUltimate1_1);
        imageButton1_2          = findViewById(R.id.imageButtonUltimate1_2);
        imageButton2_0          = findViewById(R.id.imageButtonUltimate2_0);
        imageButton2_1          = findViewById(R.id.imageButtonUltimate2_1);
        imageButton2_2          = findViewById(R.id.imageButtonUltimate2_2);
        buttonRestartGame       = findViewById(R.id.buttonRestartGameUltimate);
        buttonBack              = findViewById(R.id.buttonBack);
        imageViewUltimateBoard  = findViewById(R.id.imageViewUltimateBoard);
        imageViewClassicBoard   = findViewById(R.id.imageViewUltimateMiniBoard);
        imageViewScoreboardX    = findViewById(R.id.imageViewScoreboardXUltimate);
        imageViewScoreboardO    = findViewById(R.id.imageViewScoreboardOUltimate);
        textViewScorePlayerX    = findViewById(R.id.textViewScorePlayerXUltimate);
        textViewScorePlayerO    = findViewById(R.id.textViewScorePlayerOUltimate);

        boardButtons.add(imageButton0_0);
        boardButtons.add(imageButton0_1);
        boardButtons.add(imageButton0_2);
        boardButtons.add(imageButton1_0);
        boardButtons.add(imageButton1_1);
        boardButtons.add(imageButton1_2);
        boardButtons.add(imageButton2_0);
        boardButtons.add(imageButton2_1);
        boardButtons.add(imageButton2_2);
        addMiniBoardImagesToLayout();
        scorePlayerX = 0;
        scorePlayerO = 0;
        isTurnX = true;
        nextBoardStatus = UltimateTicTacToeGame.GAME_NOT_FINISHED;

        game = new UltimateTicTacToeGame();
        startScoreboardAnimation(isTurnX);


        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));
        //Display Back button on ActionBar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //Initializing background animation.
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        //Hiding all mini X and O images.
        miniBoardImages.stream().forEach(image -> {
            image.stream().forEach(img -> img.setImageAlpha(0));
        });
        //Hiding classic board image.
        imageViewClassicBoard.setVisibility(View.INVISIBLE);

        boardButtons.stream().forEach(button -> {
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setImageAlpha(0);

            button.setOnClickListener((View v) -> {
                if(imageViewUltimateBoard.getVisibility() == View.VISIBLE) {
                    currentMiniBoardIndex = boardButtons.indexOf(button);
                    //Switching from ultimate board to classic board.
                    imageViewUltimateBoard.setVisibility(View.INVISIBLE);
                    imageViewClassicBoard.setVisibility(View.VISIBLE);
                    //Hiding all mini board X and O images.
                    miniBoardImages.stream().forEach(image -> {
                        image.stream().forEach(img -> img.setImageAlpha(0));
                    });
                    //Getting the actual layout of the mini game to show the appropriate images.
                    displayMiniBoardLayout(currentMiniBoardIndex);
                    buttonBack.setVisibility(View.VISIBLE);
                } else {
                    //Making the play on the mini board.
                    game.play(isTurnX, currentMiniBoardIndex, boardButtons.indexOf(button));
                    //Getting the status of the board where the next player has to play.
                    nextBoardStatus = game.getMiniBoardStatus(boardButtons.indexOf(button));

                    //Getting the actual state of all mini games to show the appropriate images.
                    displayAllMiniBoardsLayout();
                    //Getting the actual state of the ultimate game to show the appropriate images.
                    displayUltimateBoardLayout();
                    if(game.getUltimateBoardStatus() == UltimateTicTacToeGame.GAME_NOT_FINISHED) {
                        if(nextBoardStatus == UltimateTicTacToeGame.GAME_NOT_FINISHED) {
                            boardButtons.stream()
                                    .filter(View::isEnabled)
                                    .forEach(boardButton -> boardButton.setEnabled(false));
                            button.setEnabled(true);
                        }
                    } else {
                        handleGameOver();
                    }

                    //Switching from classic board to ultimate board.
                    imageViewUltimateBoard.setVisibility(View.VISIBLE);
                    imageViewClassicBoard.setVisibility(View.INVISIBLE);
                    //Changing scoreboard animations.
                    stopScoreboardAnimation(isTurnX);
                    isTurnX = !isTurnX;
                    startScoreboardAnimation(isTurnX);
                    buttonBack.setVisibility(View.INVISIBLE);
                }
            });
        });

        buttonBack.setVisibility(View.INVISIBLE);
        buttonBack.setOnClickListener((View v) -> {
            //Getting the actual state of all mini games to show the appropriate images.
            displayAllMiniBoardsLayout();
            //Getting the actual state of the ultimate game to show the appropriate images.
            displayUltimateBoardLayout();
            if(nextBoardStatus == UltimateTicTacToeGame.GAME_NOT_FINISHED) {
                boardButtons.stream()
                        .filter(View::isEnabled)
                        .forEach(boardButton -> boardButton.setEnabled(false));
                boardButtons.get(currentMiniBoardIndex).setEnabled(true);
            }
            //Switching from classic board to ultimate board.
            imageViewUltimateBoard.setVisibility(View.VISIBLE);
            imageViewClassicBoard.setVisibility(View.INVISIBLE);
            buttonBack.setVisibility(View.INVISIBLE);
        });

        buttonRestartGame.setVisibility(View.INVISIBLE);
        buttonRestartGame.setOnClickListener((View v) -> {
            game = new UltimateTicTacToeGame();
            //Hiding all X and O images.
            boardButtons.stream().forEach(button -> {
                button.setImageAlpha(0);
                button.setEnabled(true);
            });
            //Hiding all mini X and O images.
            miniBoardImages.stream().forEach(image -> {
                image.stream().forEach(img -> img.setImageAlpha(0));
            });
            buttonRestartGame.setVisibility(View.INVISIBLE);
        });
    }

    /**
     * Retrieves the layout of the specified mini board and shows it on screen.
     *
     * @param miniBoardIndex The specified mini board.
     */
    private void displayMiniBoardLayout(int miniBoardIndex) {
        char[] miniBoard = game.getMiniBoard(miniBoardIndex);
        for(int i = 0; i < 9; i++) {
            if(miniBoard[i] == 'X') {
                boardButtons.get(i).setImageResource(R.drawable.x_player);
                boardButtons.get(i).setImageAlpha(255);
                boardButtons.get(i).setEnabled(false);
            } else if(miniBoard[i] == 'O') {
                boardButtons.get(i).setImageResource(R.drawable.o_player);
                boardButtons.get(i).setImageAlpha(255);
                boardButtons.get(i).setEnabled(false);
            } else {
                boardButtons.get(i).setImageAlpha(0);
                boardButtons.get(i).setEnabled(true);
            }
        }
    }

    /**
     * Retrieves the layout of all mini boards and shows them on screen.
     */
    private void displayAllMiniBoardsLayout() {
        ArrayList<char[]> miniBoards = game.getAllMiniBoards();
        miniBoards.stream().forEach(board -> {
            int boardIndex = miniBoards.indexOf(board);
            for(int i = 0; i < 9; i++) {
                if(board[i] == 'X') {
                    miniBoardImages.get(boardIndex).get(i).setImageResource(R.drawable.x_player64x64);
                    miniBoardImages.get(boardIndex).get(i).setImageAlpha(255);
                } else if(board[i] == 'O') {
                    miniBoardImages.get(boardIndex).get(i).setImageResource(R.drawable.o_player64x64);
                    miniBoardImages.get(boardIndex).get(i).setImageAlpha(255);
                } else {
                    miniBoardImages.get(boardIndex).get(i).setImageAlpha(0);
                }
            }
        });
    }

    /**
     * Retrieves the Ultimate board layout and shows it on screen.
     */
    private void displayUltimateBoardLayout() {
        char[] ultimateBoard = game.getUltimateBoard();
        for(int i = 0; i < 9; i++) {
            if(ultimateBoard[i] == 'X') {
                boardButtons.get(i).setImageResource(R.drawable.x_player);
                boardButtons.get(i).setImageAlpha(255);
                boardButtons.get(i).setEnabled(false);
            } else if(ultimateBoard[i] == 'O') {
                boardButtons.get(i).setImageResource(R.drawable.o_player);
                boardButtons.get(i).setImageAlpha(255);
                boardButtons.get(i).setEnabled(false);
            } else {
                boardButtons.get(i).setImageAlpha(0);
                boardButtons.get(i).setEnabled(true);
            }
        }
    }

    /**
     * Handles game over by making the needed changes on the board and scoreboard.
     */
    private void handleGameOver() {
        int ultimateGameStatus = game.getUltimateBoardStatus();

        //Disable all buttons.
        boardButtons.stream()
                .filter(boardButton -> boardButton.isEnabled())
                .forEach(boardButton -> boardButton.setEnabled(false));
        //Showing restart button.
        buttonRestartGame.setVisibility(View.VISIBLE);
        //Updating scoreboard.
        if(ultimateGameStatus == UltimateTicTacToeGame.X_WON) {
            scorePlayerX++;
            if(scorePlayerX >= 10) textViewScorePlayerX.setText(String.valueOf(scorePlayerX));
            else textViewScorePlayerX.setText("0" + String.valueOf(scorePlayerX));
        } else if(ultimateGameStatus == UltimateTicTacToeGame.O_WON) {
            scorePlayerO++;
            if(scorePlayerO >= 10) textViewScorePlayerO.setText(String.valueOf(scorePlayerO));
            else textViewScorePlayerO.setText("0" + String.valueOf(scorePlayerO));
        }
    }

    /**
     * Creates all 81 mini board images and adds them to the gridLayout and miniBoardImages List.
     */
    private void addMiniBoardImagesToLayout() {
        for(int i = 0; i < 9; i++)
            miniBoardImages.add(new ArrayList<ImageView>());

        //Adding all 81 ImageViews to the mini board grid layout.
        for(int i = 0; i < 81; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.x_player64x64);
            imageView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, (float) 1.0), GridLayout.spec(GridLayout.UNDEFINED, (float) 1.0)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setId(View.generateViewId());
            gridLayoutMiniBoard.addView(imageView);

            if(i < 27) {
                if(miniBoardImages.get(0).size() % 3 != 0)
                    miniBoardImages.get(0).add(imageView);
                else if(miniBoardImages.get(1).size() < miniBoardImages.get(0).size())
                    miniBoardImages.get(1).add(imageView);
                else if(miniBoardImages.get(2).size() < miniBoardImages.get(0).size())
                    miniBoardImages.get(2).add(imageView);
                else
                    miniBoardImages.get(0).add(imageView);
            } else if (i < 27*2) {
                if(miniBoardImages.get(3).size() % 3 != 0)
                    miniBoardImages.get(3).add(imageView);
                else if(miniBoardImages.get(4).size() < miniBoardImages.get(3).size())
                    miniBoardImages.get(4).add(imageView);
                else if(miniBoardImages.get(5).size() < miniBoardImages.get(3).size())
                    miniBoardImages.get(5).add(imageView);
                else
                    miniBoardImages.get(3).add(imageView);
            } else {
                if(miniBoardImages.get(6).size() % 3 != 0)
                    miniBoardImages.get(6).add(imageView);
                else if(miniBoardImages.get(7).size() < miniBoardImages.get(6).size())
                    miniBoardImages.get(7).add(imageView);
                else if(miniBoardImages.get(8).size() < miniBoardImages.get(6).size())
                    miniBoardImages.get(8).add(imageView);
                else
                    miniBoardImages.get(6).add(imageView);
            }
        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}