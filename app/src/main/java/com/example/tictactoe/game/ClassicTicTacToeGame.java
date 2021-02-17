package com.example.tictactoe.game;

import java.util.ArrayList;

public class ClassicTicTacToeGame {

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
     * Classic Tic Tac Toe board to keep track of the game.
     */
    private char[] board;


    /**
     * Initializes the game with an empty board and being Xs' turn.
     */
    public ClassicTicTacToeGame() {
        //The board uses the character 'E' to specify that a square is empty.
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
    }

    public void play(boolean isTurnX, int square) {
        if(isTurnX)
            board[square] = 'X';
        else
            board[square] = 'O';
    }

    public int counterPlay() {
        ArrayList<int[]> results = new ArrayList<>();
        int bestPlay = -1, bestResult = -9999;

        //Starting the recursion.
        for(int i = 0; i < 9; i++) {
            if(board[i] == 'E') {
                board[i] = 'O';
                results.addAll(counterPlayAlgorithm(true, i));
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

    public ArrayList<int[]> counterPlayAlgorithm(boolean isTurnX, int square) {
        int gameStatus;
        ArrayList<int[]> results = new ArrayList<>();
        ArrayList<int[]> auxResults;
        boolean branchInvalidatorLose = false;

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
                    auxResults = counterPlayAlgorithm(!isTurnX, square);
                    //Restoring the old value of the board.
                    board[i] = 'E';
                    //Checking if there is a lost game that invalidates the whole result branch.
                    if(auxResults.size() == 1 && auxResults.get(0)[0] == X_WON) {
                        branchInvalidatorLose = true;
                    }
                    results.addAll(auxResults);
                }
            }

            if(branchInvalidatorLose) {
                //Making all the wins that descend from loses into loses.
                results.stream()
                        .filter(result -> result[0] == O_WON)
                        .forEach(wonGame -> wonGame[0] = X_WON);
                //Making sure a single branch invalidator lose does not happen twice
                //by adding a draw to the results so that the size is not 0.
                if(results.size() <= 1)
                    results.add(new int[]{0, square});
            }
        } else { //Game is over.
            //Adding result of the game to results list.
            results.add(new int[]{gameStatus, square});
        }

        return results;
    }

    /**
     * Checks whether the game is over or not and who won if it is finished.
     *
     * The status of the game is expresed using the following constant values:
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
