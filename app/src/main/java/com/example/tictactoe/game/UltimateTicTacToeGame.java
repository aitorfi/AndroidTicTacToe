package com.example.tictactoe.game;

import com.example.tictactoe.MCTS.MonteCarloTreeSearch;
import com.example.tictactoe.MCTS.UltimateBoard;

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
    // private char[] board;
    // private ArrayList<char[]> miniBoards = new ArrayList<>();
    private UltimateBoard board;
    private boolean isTurnX;

    public UltimateTicTacToeGame() {
        /*board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};

        for (int i = 0; i < 9; i++) {
            miniBoards.add(new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'});
        }*/

        board = new UltimateBoard();
        isTurnX = true;
    }

    public int play(int boardIndex, int miniBoardIndex) {
        board.play(boardIndex, miniBoardIndex, isTurnX);
        isTurnX = !isTurnX;

        return TicTacToeGame.getGameStatus(board.getBoard());
    }

    /*public int play(int boardIndex, int miniBoardIndex) {
        int gameStatus;

        miniBoards.get(boardIndex)[square] = isTurnX ? 'X' : 'O';
        gameStatus = getGameStatus(miniBoards.get(boardIndex));

        if (gameStatus != GAME_NOT_FINISHED) {
            if(gameStatus == X_WON) {
                board[boardIndex] = 'X';
            } else if(gameStatus == O_WON) {
                board[boardIndex] = 'O';
            } else { // gameStatus == DRAW
                board[boardIndex] = 'D';
            }
        }

        isTurnX = !isTurnX;
        return getGameStatus(board);
    }*/

    public int counterPlay(int miniBoardIndex) {
        MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();

        board = mcts.findNextMove(board);

        isTurnX = !isTurnX;

        return board.getNextMiniBoard();
    }

    /*public int counterPlay(int miniBoardIndex) {
        int[] bestPlaySquare = {-1, -1};
        int bestPossibleScore = getDepth();
        int bestScore = bestPossibleScore * (-1);
        int score;
        int miniBoardStatus, boardStatus;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'E' && (i == miniBoardIndex || miniBoardIndex == -1)) {
                for (int square = 0; square < miniBoards.get(i).length; square++) {
                    if (miniBoards.get(i)[square] == 'E') {
                        miniBoards.get(i)[square] = isTurnX ? 'X' : 'O';

                        miniBoardStatus = getGameStatus(miniBoards.get(i));

                        if (miniBoardStatus != GAME_NOT_FINISHED) {
                            // If mini board game is finished make a move on ultimate board.
                            if (miniBoardStatus == O_WON) {
                                board[i] = 'O';
                            } else if (miniBoardStatus == X_WON) {
                                board[i] = 'X';
                            } else { // gameStatus == DRAW
                                board[i] = 'D';
                            }

                            boardStatus = getGameStatus(board);
                            // If ultimate board is finished you get the best possible score.
                            if (boardStatus != GAME_NOT_FINISHED) {
                                isTurnX = !isTurnX;
                                return square;
                            }
                        }

                        isTurnX = !isTurnX;
                        score = counterPlayAlgorithm(bestPossibleScore - 1, bestScore,
                                (getGameStatus(miniBoards.get(square)) == GAME_NOT_FINISHED) ? square : -1);

                        board[i] = 'E';
                        miniBoards.get(i)[square] = 'E';

                        if (score > bestScore) {
                            bestScore = score;
                            bestPlaySquare[0] = i;
                            bestPlaySquare[1] = square;
                        }
                    }
                }
            }
        }

        play(bestPlaySquare[0], bestPlaySquare[1]);
        return bestPlaySquare[1]; // Return next miniBoard index.
    }*/

    /*private int counterPlayAlgorithm(int depth, int bestScoreParent, int miniBoardIndex) {
        int bestPossibleScore = isTurnX ? depth * (-1) : depth;
        int bestScore = bestPossibleScore * (-1);
        int score = 0;
        int miniBoardStatus, boardStatus;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'E' && (i == miniBoardIndex || miniBoardIndex == -1)) {
                for (int square = 0; square < miniBoards.get(i).length; square++) {
                    if (miniBoards.get(i)[square] == 'E') {
                        miniBoards.get(i)[square] = isTurnX ? 'X' : 'O';

                        miniBoardStatus = getGameStatus(miniBoards.get(i));

                        if (miniBoardStatus != GAME_NOT_FINISHED) {
                            // If mini board game is finished make a move on ultimate board.
                            if (miniBoardStatus == O_WON) {
                                board[i] = 'O';
                            } else if (miniBoardStatus == X_WON) {
                                board[i] = 'X';
                            } else { // miniBoardStatus == DRAW
                                board[i] = 'D';
                            }

                            boardStatus = getGameStatus(board);

                            if (boardStatus != GAME_NOT_FINISHED) {
                                board[i] = 'E';
                                miniBoards.get(i)[square] = 'E';
                                isTurnX = !isTurnX;

                                return (boardStatus == DRAW) ? 0 : bestPossibleScore;
                            }
                        }

                        isTurnX = !isTurnX;
                        score = counterPlayAlgorithm(depth - 1, bestScore,
                                (getGameStatus(miniBoards.get(square)) == GAME_NOT_FINISHED) ? square : -1);

                        board[i] = 'E';
                        miniBoards.get(i)[square] = 'E';

                        if (isTurnX) {
                            if (score < bestScore) {
                                bestScore = score;

                                if (bestScoreParent > bestScore) {
                                    isTurnX = false;
                                    return bestScore;
                                }
                            }
                        } else {
                            if (score > bestScore) {
                                bestScore = score;

                                if (bestScoreParent < bestScore) {
                                    isTurnX = true;
                                    return bestScore;
                                }
                            }
                        }
                    }
                }
            }
        }

        isTurnX = !isTurnX;
        return score;
    }*/

    /*private int getDepth() {
        int depth = 0;

        for (int i = 0; i < miniBoards.size(); i++) {
            for (int j = 0; j < miniBoards.get(i).length; j++) {
                if (miniBoards.get(i)[j] == 'E') {
                    depth++;
                }
            }
        }

        return depth;
    }*/

    public boolean isTurnX() {
        return isTurnX;
    }

    /*public int getMiniBoardStatus(int miniBoardIndex) {
        return getGameStatus(miniBoards.get(miniBoardIndex));
    }*/
    public int getMiniBoardStatus(int miniBoardIndex) {
        return getGameStatus(board.getMiniBoards().get(miniBoardIndex));
    }

    /*public int getUltimateBoardStatus() {
        return getGameStatus(board);
    }*/
    public int getUltimateBoardStatus() {
        return getGameStatus(board.getBoard());
    }

    /*public char[] getMiniBoard(int miniBoardIndex) {
        return miniBoards.get(miniBoardIndex);
    }*/
    public char[] getMiniBoard(int miniBoardIndex) {
        return board.getMiniBoards().get(miniBoardIndex);
    }

    /*public ArrayList<char[]> getAllMiniBoards() {
        return miniBoards;
    }*/
    public ArrayList<char[]> getAllMiniBoards() {
        return board.getMiniBoards();
    }

    /*public char[] getUltimateBoard() {
        return board;
    }*/
    public char[] getUltimateBoard() {
        return board.getBoard();
    }
}
