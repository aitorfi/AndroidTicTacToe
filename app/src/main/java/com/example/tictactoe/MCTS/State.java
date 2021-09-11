package com.example.tictactoe.MCTS;

import com.example.tictactoe.game.TicTacToeGame;
import com.example.tictactoe.game.UltimateTicTacToeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {
    private UltimateBoard board;
    private boolean isTurnX;
    private int visitCount;
    private int status;
    private double winScore;

    public State() {
        visitCount = 0;
        winScore = 0;
        status = TicTacToeGame.GAME_NOT_FINISHED;
    }

    public State(boolean isTurnX) {
        board = new UltimateBoard();
        this.isTurnX = isTurnX;
        visitCount = 0;
        winScore = 0;
        status = TicTacToeGame.GAME_NOT_FINISHED;
    }

    public State(State state) {
        board = new UltimateBoard(state.getBoard());
        isTurnX = state.isTurnX();
        visitCount = state.getVisitCount();
        status = state.getStatus();
        winScore = state.getWinScore();
    }

    public List<State> getAllPossibleStates() {
        ArrayList<State> allPossibleStates = new ArrayList<>();

        for (int i = 0; i < board.getBoard().length; i++) {
            if (board.getBoard()[i] == 'E' && (board.getNextMiniBoard() == i || board.getNextMiniBoard() == -1)) {
                for (int j = 0; j < board.getMiniBoards().get(i).length; j++) {
                    if (board.getMiniBoards().get(i)[j] == 'E') {
                        State tempState = new State(!isTurnX);
                        UltimateBoard tempBoard = new UltimateBoard(board);

                        tempBoard.play(i, j, !isTurnX);
                        tempState.setBoard(tempBoard);
                        tempState.setStatus(UltimateTicTacToeGame.getGameStatus(tempBoard.getBoard()));

                        allPossibleStates.add(tempState);
                    }
                }
            }
        }

        return allPossibleStates;
    }

    public void randomPlay() {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Random rand = new Random();
        int randomIndex, boardIndex, miniBoardIndex;

        // Make a list of all possible moves.
        for (int i = 0; i < board.getBoard().length; i++) {
            if (board.getBoard()[i] == 'E' && (board.getNextMiniBoard() == i || board.getNextMiniBoard() == -1)) {
                for (int j = 0; j < board.getMiniBoards().get(i).length; j++) {
                    if (board.getMiniBoards().get(i)[j] == 'E') {
                        possibleMoves.add(new int[]{i, j});
                    }
                }
            }
        }

        // Pick a random move from possible moves.
        randomIndex = rand.nextInt(possibleMoves.size());
        boardIndex = possibleMoves.get(randomIndex)[0];
        miniBoardIndex = possibleMoves.get(randomIndex)[1];

        // Make a random move.
        board.play(boardIndex, miniBoardIndex, isTurnX);

        status = UltimateTicTacToeGame.getGameStatus(board.getBoard());
    }

    public void alternatePlayer() {
        isTurnX = !isTurnX;
    }

    public void incrementVisits() {
        visitCount++;
    }

    public void addScore(int score) {
        winScore += score;
    }

    public UltimateBoard getBoard() {
        return board;
    }

    public void setBoard(UltimateBoard board) {
        this.board = board;
    }

    public boolean isTurnX() {
        return isTurnX;
    }

    public void setTurnX(boolean turnX) {
        isTurnX = turnX;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getWinScore() {
        return winScore;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }
}
