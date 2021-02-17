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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tictactoe.R;
import com.example.tictactoe.game.UltimateTicTacToeGame;

import java.util.ArrayList;

public class UltimateTictactoeActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private GridLayout gridLayoutMiniBoard;
    private ArrayList<ArrayList<ImageView>> miniBoardImages = new ArrayList<>();
    private ImageButton imageButton0_0, imageButton0_1, imageButton0_2,
            imageButton1_0, imageButton1_1, imageButton1_2,
            imageButton2_0, imageButton2_1, imageButton2_2;
    private ArrayList<ImageButton> boardButtons = new ArrayList<>();
    private ImageView imageViewUltimateBoard, imageViewClassicBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_tictactoe);

        ActionBar actionBar = getSupportActionBar();
        //Setting ActionBar background.
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));
        //Display Back button on ActionBar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Hide application title.
        actionBar.setDisplayShowTitleEnabled(false);

        //Initializing background animation.
        constraintLayout = findViewById(R.id.ultimateTictactoeConstraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        for(int i = 0; i < 9; i++)
            miniBoardImages.add(new ArrayList<ImageView>());

        gridLayoutMiniBoard = findViewById(R.id.gridLayoutUltimateMiniBoard);
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
        //Hiding all mini X and O images.
        miniBoardImages.stream().forEach(image -> {
            image.stream().forEach(img -> img.setImageAlpha(0));
        });

        imageViewUltimateBoard = findViewById(R.id.imageViewUltimateBoard);
        imageViewClassicBoard = findViewById(R.id.imageViewUltimateMiniBoard);
        imageViewClassicBoard.setVisibility(View.INVISIBLE);

        UltimateTicTacToeGame game = new UltimateTicTacToeGame();

        imageButton0_0 = findViewById(R.id.imageButtonUltimate0_0);
        boardButtons.add(imageButton0_0);
        imageButton0_1 = findViewById(R.id.imageButtonUltimate0_1);
        boardButtons.add(imageButton0_1);
        imageButton0_2 = findViewById(R.id.imageButtonUltimate0_2);
        boardButtons.add(imageButton0_2);
        imageButton1_0 = findViewById(R.id.imageButtonUltimate1_0);
        boardButtons.add(imageButton1_0);
        imageButton1_1 = findViewById(R.id.imageButtonUltimate1_1);
        boardButtons.add(imageButton1_1);
        imageButton1_2 = findViewById(R.id.imageButtonUltimate1_2);
        boardButtons.add(imageButton1_2);
        imageButton2_0 = findViewById(R.id.imageButtonUltimate2_0);
        boardButtons.add(imageButton2_0);
        imageButton2_1 = findViewById(R.id.imageButtonUltimate2_1);
        boardButtons.add(imageButton2_1);
        imageButton2_2 = findViewById(R.id.imageButtonUltimate2_2);
        boardButtons.add(imageButton2_2);

        boardButtons.stream().forEach(button -> {
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setImageAlpha(0);

            button.setOnClickListener((View v) -> {
                if(imageViewUltimateBoard.getVisibility() == View.VISIBLE) {
                    //Switching from ultimate board to classic board.
                    imageViewUltimateBoard.setVisibility(View.INVISIBLE);
                    imageViewClassicBoard.setVisibility(View.VISIBLE);
                    //Hiding all mini board X and O images.
                    miniBoardImages.stream().forEach(image -> {
                        image.stream().forEach(img -> img.setImageAlpha(0));
                    });
                    //TODO: Get the actual state of the mini game to show the appropriate images.
                    char[] miniBoard = game.getMiniBoard(boardButtons.indexOf(button));
                    for(int i = 0; i < 9; i++) {
                        if(miniBoard[i] == 'X') {
                            boardButtons.get(i).setImageResource(R.drawable.x_player);
                            boardButtons.get(i).setImageAlpha(255);
                        } else if(miniBoard[i] == 'O') {
                            boardButtons.get(i).setImageResource(R.drawable.o_player);
                            boardButtons.get(i).setImageAlpha(255);
                        } else {
                            boardButtons.get(i).setImageAlpha(0);
                        }
                    }
                } else {
                    //Switching from classic board to ultimate board.
                    //TODO: Play classic game.
                    imageViewUltimateBoard.setVisibility(View.VISIBLE);
                    imageViewClassicBoard.setVisibility(View.INVISIBLE);
                    //TODO: Get the actual state of all the mini games to show the appropriate images.
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
                    //TODO: Get the actual state of the ultimate game to show the appropriate images.
                    char[] ultimateBoard = game.getUltimateBoard();
                    for(int i = 0; i < 9; i++) {
                        if(ultimateBoard[i] == 'X') {
                            boardButtons.get(i).setImageResource(R.drawable.x_player);
                            boardButtons.get(i).setImageAlpha(255);
                        } else if(ultimateBoard[i] == 'O') {
                            boardButtons.get(i).setImageResource(R.drawable.o_player);
                            boardButtons.get(i).setImageAlpha(255);
                        } else {
                            boardButtons.get(i).setImageAlpha(0);
                        }
                    }
                }
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
}