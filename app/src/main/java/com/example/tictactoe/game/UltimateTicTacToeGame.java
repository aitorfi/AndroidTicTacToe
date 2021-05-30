package com.example.tictactoe.game;

import java.util.ArrayList;

/**
 * Controller for the Ultimate Tic Tac Toe game.
 *
 * @author Aitor Fidalgo (aitorfi on GitHub)
 */
public class UltimateTicTacToeGame extends TicTacToeGame {

    /**
     * Representation of the board to keep track of the game.
     */
    private char[] board;
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
