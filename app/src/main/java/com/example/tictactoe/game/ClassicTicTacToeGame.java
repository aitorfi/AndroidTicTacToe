package com.example.tictactoe.game;

import java.util.ArrayList;
import java.util.OptionalInt;

/**
 * Controller for the Classic TicTacToe Game.
 *
 * @author Aitor Fidalgo (aitorfi on GitHub)
 */
public class ClassicTicTacToeGame extends TicTacToeGame {

    /**
     * Board to keep track of the game.
     */
    private char[] board;
    private boolean isTurnX;


    /**
     * Initializes the game with an empty board and being Xs' turn.
     */
    public ClassicTicTacToeGame() {
        // The board uses the character 'E' to specify that a square is empty.
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        // board = new char[]{'X', 'X', 'E', 'O', 'E', 'E', 'E', 'E', 'O'};
        isTurnX = true;
    }

    /**
     * A play is made with the users' input.
     *
     * @param square Square of the first move that was made.
     * @return The status of the game after the play is made.
     */
    public int play(int square) {
        board[square] = isTurnX ? 'X' : 'O';
        isTurnX = !isTurnX;

        return getGameStatus(board);
    }

    /**
     * Makes a counter play on the board.
     *
     * @return Index of the counter play move on the board.
     */
    public int counterPlay() {
        int bestPlaySquare = -1;
        int bestPossibleScore = getDepth(board);
        int bestScore = bestPossibleScore * (-1);
        int score;

        for (int square = 0; square < board.length; square++) {
            if (board[square] == 'E') {
                board[square] = 'O';

                if (getGameStatus() != TicTacToeGame.GAME_NOT_FINISHED) {
                    bestPlaySquare = square;
                    break;
                }

                isTurnX = !isTurnX;
                score = counterPlayAlgorithm(bestPossibleScore - 1, bestScore);
                board[square] = 'E';

                if (score > bestScore) {
                    bestScore = score;
                    bestPlaySquare = square;

                    if (bestScore == bestPossibleScore) {
                        break;
                    }
                }
            }
        }

        isTurnX = !isTurnX;
        board[bestPlaySquare] = 'O';
        return getGameStatus(board);
    }

    /**
     * Recursive function that finds the best move in a tic tac toe game (minimax algorithm implementation).
     *
     * This method is meant to be called from another method that handles the scores and chooses
     * the best move.
     *
     * @return The score of the recursion.
     */
    private int counterPlayAlgorithm(int depth, int bestScoreParent) {
        int bestPossibleScore = isTurnX ? depth * (-1) : depth;
        int bestScore = bestPossibleScore * (-1);
        int score;

        for (int square = 0; square < board.length; square++) {
            if (board[square] == 'E') {
                board[square] = isTurnX ? 'X' : 'O'; // Player makes a move.

                int gameStatus = getGameStatus(board);

                // Check if the game is finished after making a move.
                if (gameStatus != TicTacToeGame.GAME_NOT_FINISHED) {
                    if (gameStatus == TicTacToeGame.DRAW) {
                        bestScore = 0;
                    } else if ((isTurnX && gameStatus == TicTacToeGame.X_WON) ||
                            (!isTurnX && gameStatus == TicTacToeGame.O_WON)) {
                        bestScore = bestPossibleScore;
                    } else {
                        bestScore = bestPossibleScore * (-1);
                    }

                    board[square] = 'E'; // Set the initial state of the board.
                    break;
                }

                isTurnX = !isTurnX; // Change player turn for next recursion.
                score = counterPlayAlgorithm(depth - 1, bestScore);
                board[square] = 'E'; // Set the initial state of the board.

                // On X players' turn bestScoreParent will only increase and bestScore will decrease
                // so if bestScoreParent is greater than bestScore recursion can be broken.
                // In O players turn the opposite happens.
                if (isTurnX) {
                    if (score < bestScore) {
                        bestScore = score;

                        if (bestScoreParent > bestScore) {
                            break;
                        }
                    }
                } else {
                    if (score > bestScore) {
                        bestScore = score;

                        if (bestScoreParent < bestScore) {
                            break;
                        }
                    }
                }
            }
        }

        isTurnX = !isTurnX; // Set the turn of the previous recursion.
        return bestScore;
    }

    /**
     * Returns the depth of a tic tac toe game by counting the free tiles of the board.
     *
     * @param board The board where the game is being played.
     * @return The depth of the game (number of free tiles in the board).
     */
    private int getDepth(char[] board) {
        int depth = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'E') {
                depth++;
            }
        }

        return depth;
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

    public char[] getBoard() {
        return board;
    }
}
