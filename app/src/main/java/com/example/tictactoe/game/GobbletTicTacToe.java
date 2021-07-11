package com.example.tictactoe.game;

public class GobbletTicTacToe extends TicTacToeGame {

    public static final int GOBBLET_LEVEL_ONE = 1;
    public static final int GOBBLET_LEVEL_TWO = 2;
    public static final int GOBBLET_LEVEL_THREE = 3;

    /** Board to keep track of the game.*/
    private char[] board;
    /** Auxiliary board that holds level 1 gobblets.*/
    private char[] boardLevel1;
    /** Auxiliary board that holds level 2 gobblets.*/
    private char[] boardLevel2;
    /** Auxiliary board that holds level 3 gobblets.*/
    private char[] boardLevel3;
    /**
     * Array that holds the auxiliary boards {@link #boardLevel1}, {@link #boardLevel2} and
     * {@link #boardLevel3} (index = level held in the board + 1).
     */
    private char[][] boardLevels;
    /** Keeps track of whose turn it is: True if it is X players' turn otherwise false.*/
    private boolean isTurnX;


    /**
     * Initializes the game with empty boards and being Xs' turn.
     */
    public GobbletTicTacToe() {
        board = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        boardLevel1 = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        boardLevel2 = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        boardLevel3 = new char[]{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'};
        boardLevels = new char[][]{boardLevel1, boardLevel2, boardLevel3};
        isTurnX = true;
    }


    /**
     * Makes a play on the board by placing a new gobblet of the specified level.
     *
     * @param square Square of the board where the new gobblet will be placed.
     * @param gobbletLevel Level of the new gobblet (possible values: {@link #GOBBLET_LEVEL_ONE}, {@link #GOBBLET_LEVEL_TWO} and {@link #GOBBLET_LEVEL_THREE}).
     * @return The status of the game after the new play (possible values: {@link #X_WON}, {@link #O_WON} and {@link #GAME_NOT_FINISHED}).
     */
    public int play(int square, int gobbletLevel) {
        boardLevels[gobbletLevel - 1][square] = (isTurnX) ? 'X' : 'O'; // Place selected gobblet.
        isTurnX = !isTurnX; // Change the turn after making a move.
        updateBoard(); // Update the actual board after the new move.

        return getGameStatus(board);
    }

    /**
     * Makes a play on the board by moving one of the gobblets in it to another square.
     *
     * @param fromSquare Square of the gobblet that will be moved.
     * @param toSquare Square where the gobblet will e placed.
     * @param gobbletLevel Level of the selected gobblet (possible values: {@link #GOBBLET_LEVEL_ONE}, {@link #GOBBLET_LEVEL_TWO} and {@link #GOBBLET_LEVEL_THREE})..
     * @return The status of the game after the new play (possible values: {@link #X_WON}, {@link #O_WON} and {@link #GAME_NOT_FINISHED}).
     */
    public int playFromBoard(int fromSquare, int toSquare, int gobbletLevel) {
        if (fromSquare != -1) // Remove selected gobblet from the board.
            boardLevels[gobbletLevel - 1][fromSquare] = 'E';

        return play(toSquare, gobbletLevel);
    }

    /**
     * Updates the board getting gobblets from {@link #boardLevels} (greater levels have priority).
     */
    private void updateBoard() {
        for (int i = 0; i < 9; i++) { // Copy the highest level
            board[i] = boardLevel3[i];
        }

        for (int i = boardLevels.length - 2; i >= 0 ; i--) { // Skip the highest level.
            for (int j = 0; j < 9; j++) {
                // Fill the empty squares with the lower levels.
                if (boardLevels[i][j] != 'E' && board[j] == 'E') {
                    board[j] = boardLevels[i][j];
                }
            }
        }
    }

    /**
     * Returns true if the specified square of the board is empty, otherwise false.
     *
     * @param square The specified square of the board.
     * @return True if the square is empty, otherwise false.
     */
    public boolean isSquareEmpty(int square) {
        return (board[square] == 'E');
    }

    /**
     * @return The board ({@link #board}) of the current game.
     */
    public char[] getBoard() {
        return board;
    }

    /**
     * @return Array that holds a board for each gobblet level ({@link #boardLevels}).
     */
    public char[][] getBoardLevels() {
        return boardLevels;
    }
}
