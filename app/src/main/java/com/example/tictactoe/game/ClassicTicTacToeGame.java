package com.example.tictactoe.game;

import java.util.ArrayList;
import java.util.OptionalInt;

/**
 * Controller for the Classic TicTacToe Game.
 *
 * @author Aitor Fidalgo (aitorfi)
 */
public class ClassicTicTacToeGame extends TicTacToeGame {

    /**
     * Initializes the game with an empty board and being Xs' turn.
     */
    public ClassicTicTacToeGame() {
        //The board uses the character 'E' to specify that a square is empty.
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
    }

    /**
     * A play is made with the users' input.
     *
     * @param isTurnX True if it is Xs' turn, false if it is Os' turn.
     * @param square Square of the first move that was made.
     * @return The status of the game after the play is made.
     */
    public int play(boolean isTurnX, int square) {
        if(isTurnX)
            board[square] = 'X';
        else
            board[square] = 'O';

        return getGameStatus();
    }

    /**
     * Makes a counter play with the current layout of the board.
     *
     * @return Index of the counter play move on the board.
     */
    public int counterPlay() {
        ArrayList<int[]> results = new ArrayList<>();
        int bestPlay = -1, bestResult = -2000000000;

        //Starting the recursion.
        for(int i = 0; i < 9; i++) {
            if(board[i] == 'E') {
                board[i] = 'O';
                results.addAll(counterPlayAlgorithm(true, i, 10));
                board[i] = 'E';
            }
        }

        //Choosing the best play from the results.
        for(int i = 0; i < 9; i++) {
            if(board[i] == 'E') {
                //Making an effectively final copy of i.
                int finalI = i;
                //Getting the sum of the results of the games that started with the same play.
                int result = results.stream()
                        .filter(game -> game[1] == finalI)
                        .mapToInt(game -> game[0])
                        .sum();
                if(result > bestResult) {
                    bestPlay = i;
                    bestResult = result;
                }
            }
        }

        board[bestPlay] = 'O';
        return bestPlay;
    }

    /**
     * Recursive function that plays all te possible combinations of the game and saves the results.
     *
     * @param isTurnX True if it is Xs' turn, false if it is Os' turn.
     * @param square Square of the first move that was made.
     * @param resultMultiplier Keeps track of the number of movements in the game to multipli the results.
     * @return The results of the recursion.
     */
    private ArrayList<int[]> counterPlayAlgorithm(boolean isTurnX, int square, int resultMultiplier) {
        int gameStatus;
        ArrayList<int[]> results = new ArrayList<>();
        ArrayList<int[]> auxResults;
        boolean branchInvalidatorLose = false, branchInvalidatorWin = false;

        gameStatus = getGameStatus();
        if(gameStatus == GAME_NOT_FINISHED) {
            for(int i = 0; i < 9; i++) {
                if(board[i] == 'E') {
                    if(isTurnX) {
                        board[i] = 'X';
                    } else {
                        board[i] = 'O';
                    }
                    //Making the recursion with the new tile movement.
                    auxResults = counterPlayAlgorithm(!isTurnX, square, resultMultiplier - 1);
                    //Restoring the old value of the board.
                    board[i] = 'E';
                    //Checking if there is a lost game that invalidates the whole result branch.
                    if(auxResults.size() == 1 && auxResults.get(0)[0] <= X_WON) {
                        branchInvalidatorLose = true;
                    }
                    if(auxResults.size() == 1 && auxResults.get(0)[0] >= O_WON) {
                        branchInvalidatorWin = true;
                    }
                    results.addAll(auxResults);
                }
            }

            // In case there is a win or a lose all the other results from a later recursion are removed.
            if(branchInvalidatorLose) {
                OptionalInt minValue = results.stream().mapToInt(result -> result[0]).min();
                if(minValue.isPresent()) {
                    results.clear();
                    results.add(new int[]{minValue.getAsInt(), square});
                    results.add(new int[]{0, square});
                }
            } else if(branchInvalidatorWin) {
                OptionalInt maxValue = results.stream().mapToInt(result -> result[0]).max();
                if(maxValue.isPresent()) {
                    results.clear();
                    results.add(new int[]{maxValue.getAsInt(), square});
                    results.add(new int[]{0, square});
                }
            }
        } else { //Game is over.
            // Adding result of the game to results list.
            // The following operation is made so that a single win has a better result than
            // two wins in the next recursion.
            int result = (int) Math.pow(gameStatus * resultMultiplier, resultMultiplier);
            results.add(new int[]{result, square});
        }

        return results;
    }

    /**
     * Checks whether the game is over or not and who won if it is finished.
     *
     * The status of the game is expressed using the following constant values:
     * {@link #X_WON} (-1), {@link #O_WON} (1),
     * {@link #DRAW} (0) and {@link #GAME_NOT_FINISHED} (2).
     *
     * @return The status of the game with a constant int value.
     */
    public int getGameStatus() {
        int gameStatus = DRAW;

        //Checking the rows of the board.
        for(int i = 0; i < 9; i+=3) {
            if(board[i] == board[i+1] && board[i] == board[i+2] && board[i] != 'E') {
                if(board[i] == 'X') gameStatus = X_WON;
                else if(board[i] == 'O') gameStatus = O_WON;
                break;
            }
        }

        if(gameStatus == DRAW) {
            //Checking the columns of the board.
            for(int i = 0; i < 3; i++) {
                if(board[i] == board[i+3] && board[i] == board[i+6] && board[i] != 'E') {
                    if(board[i] == 'X') gameStatus = X_WON;
                    else if(board[i] == 'O') gameStatus = O_WON;
                    break;
                }
            }
        }

        if(gameStatus == DRAW) {
            //Checking diagonal starting at the top left corner.
            if (board[0] == board[4] && board[0] == board[8] && board[0] != 'E') {
                if (board[0] == 'X') gameStatus = X_WON;
                else if (board[0] == 'O') gameStatus = O_WON;
            }
        }

        if(gameStatus == DRAW) {
            //Checking diagonal starting at the top right corner.
            if (board[2] == board[4] && board[2] == board[6] && board[2] != 'E') {
                if (board[2] == 'X') gameStatus = X_WON;
                else if (board[2] == 'O') gameStatus = O_WON;
            }
        }

        //Checking if the board is full of tiles.
        if(gameStatus == DRAW) {
            for(int i = 0; i < 9; i++) {
                if(board[i] == 'E') {
                    gameStatus = GAME_NOT_FINISHED;
                    break;
                }
            }
        }

        return gameStatus;
    }
}
