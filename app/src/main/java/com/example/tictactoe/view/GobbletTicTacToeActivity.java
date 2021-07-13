package com.example.tictactoe.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tictactoe.R;
import com.example.tictactoe.game.GobbletTicTacToe;

import java.util.ArrayList;

public class GobbletTicTacToeActivity extends AppCompatActivity {

    private final ColorStateList UNSELECTED_GOBBLET = ColorStateList.valueOf(0x00FFFFFF);
    private final ColorStateList SELECTED_GOBBLET   = ColorStateList.valueOf(0xA6FFFFFF);

    private ActionBar actionBar;
    private ConstraintLayout constraintLayout;
    private ImageButton imageButton0_0, imageButton0_1, imageButton0_2,
            imageButton1_0, imageButton1_1, imageButton1_2,
            imageButton2_0, imageButton2_1, imageButton2_2;
    private ImageButton imageButtonGobbletLevel1, imageButtonGobbletLevel2, imageButtonGobbletLevel3;
    private Button buttonRestartGame;

    private ArrayList<ImageButton> boardButtons;
    private ArrayList<ImageButton> gobbletButtons;
    private GobbletTicTacToe game;

    private int[] availableGobblets;
    private int gameMode;
    private int selectedGobbletLevel, selectedGobbletIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobblet_tic_tac_toe);

        actionBar                   = getSupportActionBar();
        constraintLayout            = findViewById(R.id.gobbletTicTacToeConstraintLayout);
        imageButton0_0              = findViewById(R.id.imageButtonGobblet0_0);
        imageButton0_1              = findViewById(R.id.imageButtonGobblet0_1);
        imageButton0_2              = findViewById(R.id.imageButtonGobblet0_2);
        imageButton1_0              = findViewById(R.id.imageButtonGobblet1_0);
        imageButton1_1              = findViewById(R.id.imageButtonGobblet1_1);
        imageButton1_2              = findViewById(R.id.imageButtonGobblet1_2);
        imageButton2_0              = findViewById(R.id.imageButtonGobblet2_0);
        imageButton2_1              = findViewById(R.id.imageButtonGobblet2_1);
        imageButton2_2              = findViewById(R.id.imageButtonGobblet2_2);
        imageButtonGobbletLevel1    = findViewById(R.id.imageButtonGobbletLevel1);
        imageButtonGobbletLevel2    = findViewById(R.id.imageButtonGobbletLevel2);
        imageButtonGobbletLevel3    = findViewById(R.id.imageButtonGobbletLevel3);
        buttonRestartGame           = findViewById(R.id.buttonRestartGameGobblet);

        game = new GobbletTicTacToe();
        selectedGobbletIndex = -1;
        selectedGobbletLevel = -1;


        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));
        actionBar.setDisplayHomeAsUpEnabled(true); // Display Back button on ActionBar.
        actionBar.setDisplayShowTitleEnabled(false); // Hide application name on ActionBar.

        //Initializing layout background animation.
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        boardButtons = new ArrayList();
        boardButtons.add(imageButton0_0);
        boardButtons.add(imageButton0_1);
        boardButtons.add(imageButton0_2);
        boardButtons.add(imageButton1_0);
        boardButtons.add(imageButton1_1);
        boardButtons.add(imageButton1_2);
        boardButtons.add(imageButton2_0);
        boardButtons.add(imageButton2_1);
        boardButtons.add(imageButton2_2);

        gobbletButtons = new ArrayList();
        gobbletButtons.add(imageButtonGobbletLevel1);
        gobbletButtons.add(imageButtonGobbletLevel2);
        gobbletButtons.add(imageButtonGobbletLevel3);
        availableGobblets = new int[]{2, 2, 2}; // Players start with 2 gobblets of each kind.

        gameMode = (int) getIntent().getExtras().get("GAME_MODE");


        boardButtons.stream().forEach(button -> {
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setImageAlpha(0);

            if (gameMode == MainActivity.MULTI_PLAYER)
                button.setOnClickListener((View v) -> handleBoardButton((ImageButton) v));
        });

        gobbletButtons.stream().forEach(button -> {
            button.setOnClickListener((View v) -> handleGobbletButton((ImageButton) v));
        });

        buttonRestartGame.setVisibility(View.INVISIBLE);
        buttonRestartGame.setOnClickListener((View v) -> restartGame());
    }

    private void handleBoardButton(ImageButton button) {
        int buttonIndex = boardButtons.indexOf(button);

        if (game.isSquareEmpty(buttonIndex)) {
            if (selectedGobbletLevel == -1) {
                Toast.makeText(this, "You have to pick a gobblet first.", Toast.LENGTH_SHORT).show();
            } else {
                if (selectedGobbletIndex == -1) {
                    game.play(buttonIndex, selectedGobbletLevel);
                } else {
                    game.playFromBoard(selectedGobbletIndex, buttonIndex, selectedGobbletLevel);
                    boardButtons.get(selectedGobbletIndex).setBackgroundTintList(UNSELECTED_GOBBLET);
                }

                button.setTag(String.valueOf(selectedGobbletLevel));
                deleteSelection(selectedGobbletIndex);
                mirrorGameBoard();
            }
        } else {
            if (button.getTag() == "") {
                Toast.makeText(this, "You have to pick a gobblet first.", Toast.LENGTH_SHORT).show();
            } else {
                selectedGobbletIndex = buttonIndex;
                selectedGobbletLevel = Integer.parseInt((String) button.getTag());

                gobbletButtons.stream().forEach(b ->
                        b.setBackgroundTintList(UNSELECTED_GOBBLET));
                boardButtons.stream().forEach(b ->
                        b.setBackgroundTintList(UNSELECTED_GOBBLET));
                button.setBackgroundTintList(SELECTED_GOBBLET);
            }
        }
    }

    private void deleteSelection(int selectedBoardButton) {
        if (selectedBoardButton != -1) {
            boardButtons.get(selectedGobbletIndex).setBackgroundTintList(UNSELECTED_GOBBLET);
        } else {
            gobbletButtons.stream().forEach(button -> button.setBackgroundTintList(UNSELECTED_GOBBLET));
        }

        selectedGobbletLevel = -1;
        selectedGobbletIndex = -1;
    }

    private void mirrorGameBoard() {
        char[][] boardLevels = game.getBoardLevels();

        boardButtons.stream().forEach(button -> {
            button.setImageAlpha(0); // Make image transparent.
            button.setTag("");

            for (int i = boardLevels.length - 1; i >= 0; i--) {
                char square = boardLevels[i][boardButtons.indexOf(button)];

                if (square != 'E') {
                    int imageId;
                    int gobbletLevel = i + 1;

                    if (gobbletLevel == GobbletTicTacToe.GOBBLET_LEVEL_THREE) {
                        imageId = (square == 'X') ? R.drawable.red_gobblet_level3 : R.drawable.blue_gobblet_level3;
                        button.setPadding(0, 0, 0, 0);
                    } else if (gobbletLevel == GobbletTicTacToe.GOBBLET_LEVEL_TWO) {
                        imageId = (square == 'X') ? R.drawable.red_gobblet_level2 : R.drawable.blue_gobblet_level2;
                        button.setPadding(26, 26, 26, 26);
                    } else { // gobbletLevel == GobbletTicTacToe.GOBBLET_LEVEL_ONE
                        imageId = (square == 'X') ? R.drawable.red_gobblet_level1 : R.drawable.blue_gobblet_level1;
                        button.setPadding(36, 36, 36, 36);
                    }

                    button.setImageResource(imageId); // Set appropriate image.
                    button.setImageAlpha(255); // Make image visible.
                    break;
                }
            }
        });
    }

    private void handleGobbletButton(ImageButton button) {
        // Show all gobbleButtons as unselected.
        gobbletButtons.stream().forEach(gobblet ->
                gobblet.setBackgroundTintList(UNSELECTED_GOBBLET));
        // Show all boardButtons as unselected.
        boardButtons.stream().forEach(b ->
                b.setBackgroundTintList(UNSELECTED_GOBBLET));
        // Show the clicked button as selected.
        button.setBackgroundTintList(SELECTED_GOBBLET);

        selectedGobbletLevel = Integer.parseInt((String) button.getTag());
        selectedGobbletIndex = -1;
    }

    private void restartGame() {
        // TODO: Implement restart game button event.
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