package com.example.tictactoe.game;

import java.util.ArrayList;

public class UltimateTicTacToeGame extends TicTacToeGame {

    private ArrayList<char[]> miniBoards = new ArrayList<>();

    public UltimateTicTacToeGame() {
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        for(int i = 0; i < 9; i++) {
            miniBoards.add(new char[]{'E', 'E', 'E',
                    'E', 'E', 'E',
                    'E', 'E', 'E'});
        }
    }

    public int play(boolean isTurnX, int miniBoardIndex, int square) {
        int gameStatus;

        if(isTurnX)
            miniBoards.get(miniBoardIndex)[square] = 'X';
        else
            miniBoards.get(miniBoardIndex)[square] = 'O';

        gameStatus = getGameStatus(miniBoards.get(miniBoardIndex));
        if(gameStatus == X_WON)
            board[miniBoardIndex] = 'X';
        else if(gameStatus == O_WON)
            board[miniBoardIndex] = 'O';

        return gameStatus;
    }

    public int getMiniBoardStatus(int miniBoardIndex) {
        return getGameStatus(miniBoards.get(miniBoardIndex));
    }

    public int getUltimateBoardStatus() {
        return getGameStatus(board);
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
    private int getGameStatus(char[] board) {
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

    public char[] getMiniBoard(int miniBoardIndex) {
        return miniBoards.get(miniBoardIndex);
    }

    public ArrayList<char[]> getAllMiniBoards() {
        return miniBoards;
    }

    public char[] getUltimateBoard() {
        return board;
    }
}
