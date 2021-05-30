package com.example.tictactoe.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe.R;
import com.make.dots.dotsindicator.DotsIndicator;

/**
 * Controller of the main menu of the application.
 *
 * @author Aitor Fidalgo (aitorfi on GitHub)
 */
public class MainActivity extends AppCompatActivity {

    public static final int SINGLE_PLAYER = 1;
    public static final int MULTI_PLAYER = 2;

    private ConstraintLayout constraintLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private DotsIndicator dotsIndicator;
    private Button buttonClassicSinglePlayer, buttonClassicMultiPlayer;
    private TextView textViewGameName;
    private static final int[] IMAGES = {R.drawable.tic_tac_toe_board, R.drawable.ultimate_tictactoe_board};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));

        //Initializing layout background animation.
        constraintLayout = findViewById(R.id.classicTictactoeConstraintLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        viewPager = findViewById(R.id.viewPager);
        //Setting custom adapter to the ViewPager.
        pagerAdapter = new ViewPagerAdapter(MainActivity.this, IMAGES);
        viewPager.setAdapter(pagerAdapter);
        //Setting DotsIndicator to the ViewPager.
        dotsIndicator = findViewById(R.id.dotsIndicator);
        dotsIndicator.setViewPager(viewPager);
        viewPager.getAdapter().registerDataSetObserver(dotsIndicator.getDataSetObserver());
        //Changing game name display on scroll change.
        textViewGameName = findViewById(R.id.textViewGameName);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(viewPager.getCurrentItem() == 0) {
                    textViewGameName.setText(getString(R.string.classic_tictactoe));
                } else {
                    textViewGameName.setText(getString(R.string.ultimate_tictactoe));
                }
            }
        });

        buttonClassicSinglePlayer = findViewById(R.id.buttonSinglePlayer);
        buttonClassicSinglePlayer.setOnClickListener((View v) -> {
            if(viewPager.getCurrentItem() == 0) { //The item is a Classic TicTacToe board.
                Intent intentClassicSinglePlayer = new Intent(
                        MainActivity.this, ClassicTictactoeActivity.class);
                intentClassicSinglePlayer.putExtra("GAME_MODE", SINGLE_PLAYER);
                startActivity(intentClassicSinglePlayer);
            } else { //The item is an Ultimate TicTacToe board.
                Toast.makeText(getApplicationContext(), "Not Implemented Yet.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonClassicMultiPlayer = findViewById(R.id.buttonMultiPlayer);
        buttonClassicMultiPlayer.setOnClickListener((View v) -> {
            if(viewPager.getCurrentItem() == 0) { //The item is a Classic TicTacToe board.
                Intent intentClassicSinglePlayer = new Intent(
                        MainActivity.this, ClassicTictactoeActivity.class);
                intentClassicSinglePlayer.putExtra("GAME_MODE", MULTI_PLAYER);
                startActivity(intentClassicSinglePlayer);
            } else { //The item is an Ultimate TicTacToe board.
                Intent intentClassicSinglePlayer = new Intent(
                        MainActivity.this, UltimateTictactoeActivity.class);
                startActivity(intentClassicSinglePlayer);
            }
        });
    }
}