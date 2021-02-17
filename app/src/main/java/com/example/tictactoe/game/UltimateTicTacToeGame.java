package com.example.tictactoe.game;

import java.util.ArrayList;

public class UltimateTicTacToeGame {

    private ArrayList<char[]> miniBoards = new ArrayList<>();
    private char[] board;

    public UltimateTicTacToeGame() {
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        for(int i = 0; i < 9; i++) {
            miniBoards.add(new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'});
        }
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
