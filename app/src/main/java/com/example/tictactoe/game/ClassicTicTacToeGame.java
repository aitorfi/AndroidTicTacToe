package com.example.tictactoe.game;

import java.util.ArrayList;
import java.util.OptionalInt;

/**
 * Controller for the Classic TicTacToe Game.
 *
 * @author Aitor Fidalgo (aitorfi on GitHub)
 */
public class ClassicTicTacToeGame extends TicTacToeGame {

    /** Board to keep track of the game.*/
    private char[] board;
    private long auxResult;
    private boolean isTurnX, branchInvalidatorResult;


    /**
     * Initializes the game with an empty board and being Xs' turn.
     */
    public ClassicTicTacToeGame() {
        //The board uses the character 'E' to specify that a square is empty.
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        isTurnX = true;
        branchInvalidatorResult = false;
    }

    /**
     * A play is made with the users' input.
     *
     * @param square Square of the first move that was made.
     * @return The status of the game after the play is made.
     */
    public int play(int square) {
        if(isTurnX)
            board[square] = 'X';
        else
            board[square] = 'O';

        isTurnX = !isTurnX;
        return getGameStatus(board);
    }

    /**
     * Makes a counter play with the current layout of the board.
     *
     * @return Index of the counter play move on the board.
     */
    public int counterPlay() {
        ArrayList<long[]> results = new ArrayList<>();
        int bestPlay = -1;
        long bestResult = Long.MIN_VALUE;

        // Loop over the board and call recursive method.
        // isTurnX should be false at this point.
        for(int square = 0; square < 9; square++) {
            if(board[square] == 'E') {
                board[square] = 'O';
                isTurnX = !isTurnX;
                results.add(new long[]{counterPlayAlgorithm(), square});
                board[square] = 'E';
            }
        }
        isTurnX = !isTurnX; // Method play will change the turn too

        //Choosing the best play from the results.
        for (long[] r : results) {
            if (r[0] > bestResult) {
                bestResult = r[0];
                bestPlay = (int) r[1];
            }
        }

        board[bestPlay] = 'O';
        return bestPlay;
    }

    /**
     * Recursive function that plays all te possible combinations for the game and saves the results.
     *
     * @return The results of the recursion.
     */
    private long counterPlayAlgorithm() {
        long gameStatus;
        long result = DRAW;

        gameStatus = getGameStatus(board);

        if(gameStatus == GAME_NOT_FINISHED) {
            for(int i = 0; i < 9; i++) {
                if(board[i] == 'E') {
                    if(isTurnX)
                        board[i] = 'X';
                    else
                        board[i] = 'O';

                    isTurnX = !isTurnX;

                    //Making the recursion with the new tile movement.
                    auxResult = counterPlayAlgorithm();
                    //Restoring the old value of the board for next recursion.
                    board[i] = 'E';

                    //Checking if there is a lost game that invalidates the whole result branch.
                    if (branchInvalidatorResult) {
                        result = auxResult;
                        branchInvalidatorResult = false;

                        break;
                    }

                    result += auxResult;
                }
            }

        } else if (gameStatus != DRAW) {
            // The following operation is made so that a single win
            // has a better result than two wins in the next recursion.
            int resultMultiplier = 1;

            for (int i = 0; i < board.length; i++) {
                if (board[i] == 'E') {
                    resultMultiplier++;
                }
            }

            result = (long) Math.pow(gameStatus * resultMultiplier, resultMultiplier);

            if (gameStatus == X_WON && result > 0) {
                // The power operation might make a negative result into a positive one.
                result = result * -1;
            }
            branchInvalidatorResult = true;
        }

        isTurnX = !isTurnX;
        return result;
    }

    /**
     * @return The status of the current game.
     */
    public int getGameStatus() {
        return getGameStatus(board);
    }

    /**
     * @return True if it is X players turn; false if it is O players.
     */
    public boolean isTurnX() {
        return isTurnX;
    }
}
