package com.example.tictactoe.MCTS;

import com.example.tictactoe.game.TicTacToeGame;
import com.example.tictactoe.game.UltimateTicTacToeGame;

import java.util.ArrayList;

public class UltimateBoard {
    private char[] board;
    private ArrayList<char[]> miniBoards = new ArrayList<>();
    private int nextMiniBoard;

    public UltimateBoard() {
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};

        for (int i = 0; i < 9; i++) {
            miniBoards.add(new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'});
        }

        nextMiniBoard = -1;
    }
    public UltimateBoard(UltimateBoard ultimateBoard) {
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};

        for (int i = 0; i < 9; i++) {
            miniBoards.add(new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'});
        }

        for (int i = 0; i < ultimateBoard.getBoard().length; i++) {
            board[i] = ultimateBoard.getBoard()[i];

            for (int j = 0; j < ultimateBoard.getMiniBoards().get(i).length; j++) {
                miniBoards.get(i)[j] = ultimateBoard.getMiniBoards().get(i)[j];
            }
        }

        nextMiniBoard = ultimateBoard.getNextMiniBoard();
    }

    public void play(int boardIndex, int miniBoardIndex, boolean isTurnX) {
        nextMiniBoard = -1;

        miniBoards.get(boardIndex)[miniBoardIndex] = (isTurnX) ? 'X' : 'O';

        switch (UltimateTicTacToeGame.getGameStatus(miniBoards.get(boardIndex))) {
            case TicTacToeGame.X_WON:
                board[boardIndex] = 'X';
                break;
            case TicTacToeGame.O_WON:
                board[boardIndex] = 'O';
                break;
            case TicTacToeGame.DRAW:
                board[boardIndex] = 'D';
                break;
            default:
                break;
        }

        if (UltimateTicTacToeGame.getGameStatus(miniBoards.get(miniBoardIndex)) == TicTacToeGame.GAME_NOT_FINISHED) {
            nextMiniBoard = miniBoardIndex;
        }
    }

    public char[] getBoard() {
        return board;
    }

    public void setBoard(char[] board) {
        this.board = board;
    }

    public ArrayList<char[]> getMiniBoards() {
        return miniBoards;
    }

    public void setMiniBoards(ArrayList<char[]> miniBoards) {
        this.miniBoards = miniBoards;
    }

    public int getNextMiniBoard() {
        return nextMiniBoard;
    }

    public void setNextMiniBoard(int nextMiniBoard) {
        this.nextMiniBoard = nextMiniBoard;
    }
}
