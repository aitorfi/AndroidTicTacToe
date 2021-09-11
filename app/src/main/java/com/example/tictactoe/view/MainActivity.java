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
    private Button buttonSinglePlayer, buttonMultiPlayer;
    private TextView textViewGameName;
    private static final int[] IMAGES = {R.drawable.tic_tac_toe_board, R.drawable.ultimate_tictactoe_board, R.drawable.gobblet_tic_tac_toe_board};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_1));

        //Initializing layout background animation.
        constraintLayout = findViewById(R.id.classicTicTacToeConstraintLayout);
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
                switch (position) {
                    case 0:
                        textViewGameName.setText(getString(R.string.classic_tictactoe));
                        break;
                    case 1:
                        textViewGameName.setText(getString(R.string.ultimate_tictactoe));
                        break;
                    case 2:
                        textViewGameName.setText(getString(R.string.gobble_tictactoe));
                        break;
                }
            }
        });

        buttonSinglePlayer = findViewById(R.id.buttonSinglePlayer);
        buttonSinglePlayer.setOnClickListener((View v) -> {
            Intent intentClassicSinglePlayer = null;
            switch (viewPager.getCurrentItem()) {
                case 0: // Classic Tic Tac Toe
                    intentClassicSinglePlayer = new Intent(MainActivity.this, ClassicTicTacToeActivity.class);
                    break;
                case 1: // Ultimate Tic Tac Toe
                    intentClassicSinglePlayer = new Intent(MainActivity.this, UltimateTicTacToeActivity.class);
                    break;
                case 2: // Gobblet Tic Tac Toe
                    Toast.makeText(getApplicationContext(), getString(R.string.not_implemented_yet), Toast.LENGTH_SHORT).show();
                    break;
            }

            if (intentClassicSinglePlayer != null){
                intentClassicSinglePlayer.putExtra("GAME_MODE", SINGLE_PLAYER);
                startActivity(intentClassicSinglePlayer);
            }
        });

        buttonMultiPlayer = findViewById(R.id.buttonMultiPlayer);
        buttonMultiPlayer.setOnClickListener((View v) -> {
            Intent intentClassicSinglePlayer = null;

            switch (viewPager.getCurrentItem()) {
                case 0: // Classic Tic Tac Toe
                    intentClassicSinglePlayer = new Intent(MainActivity.this, ClassicTicTacToeActivity.class);
                    break;
                case 1: // Ultimate Tic Tac Toe
                    intentClassicSinglePlayer = new Intent(MainActivity.this, UltimateTicTacToeActivity.class);
                    break;
                case 2: // Gobblet Tic Tac Toe
                    intentClassicSinglePlayer = new Intent(MainActivity.this, GobbletTicTacToeActivity.class);
                    break;
            }

            if (intentClassicSinglePlayer != null) {
                intentClassicSinglePlayer.putExtra("GAME_MODE", MULTI_PLAYER);
                startActivity(intentClassicSinglePlayer);
            }
        });
    }
}