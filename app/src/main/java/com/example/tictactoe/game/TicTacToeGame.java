package com.example.tictactoe.game;

/**
 * Superclass for a TicTacToe Game Controller.
 *
 * @author Aitor Fidalgo (aitorfi)
 */
public abstract class TicTacToeGame {

    /**
     * This value specifies that the algorithm lost a game.
     */
    public static final int X_WON = -1;
    /**
     * This value specifies that the algorithm won a game.
     */
    public static final int O_WON = 1;
    /**
     * This value specifies that the game finished with a draw.
     */
    public static final int DRAW = 0;
    /**
     * This value specifies that the game is not finished yet.
     */
    public static final int GAME_NOT_FINISHED = 2;

    /**
     * Representation of the board to keep track of the game.
     */
    protected char[] board;
}
