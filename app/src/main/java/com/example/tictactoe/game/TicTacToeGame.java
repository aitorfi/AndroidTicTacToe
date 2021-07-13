package com.example.tictactoe.game;

/**
 * Superclass for a TicTacToe Game Controller.
 *
 * Contains 4 constant variables to keep track of the game status ({@link #X_WON}, {@link #DRAW},
 * {@link #O_WON} and {@link #GAME_NOT_FINISHED}) and useful methods for the game controllers.
 *
 * @author Aitor Fidalgo (aitorfi on GitHub)
 */
public abstract class TicTacToeGame {

    /**
     * This value specifies that the player using 'X' won.
     */
    public static final int X_WON = -1;
    /**
     * This value specifies that the player using 'O' won.
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
     * Checks whether the game is over or not and who won if it is finished.
     *
     * The status of the game is expressed using the following constant values:
     * {@link #X_WON} (-1), {@link #O_WON} (1),
     * {@link #DRAW} (0) and {@link #GAME_NOT_FINISHED} (2).
     *
     * @return The status of the game.
     */
    protected int getGameStatus(char[] board) {
        int gameStatus = DRAW;

        //Checking the rows of the board.
        for(int i = 0; i < 9; i+=3) {
            if(board[i] == board[i+1] && board[i] == board[i+2] && board[i] != 'E') {
                if(board[i] == 'X')
                    gameStatus = X_WON;
                else if(board[i] == 'O')
                    gameStatus = O_WON;

                return gameStatus;
            }
        }

        //Checking the columns of the board.
        for(int i = 0; i < 3; i++) {
            if(board[i] == board[i+3] && board[i] == board[i+6] && board[i] != 'E') {
                if(board[i] == 'X')
                    gameStatus = X_WON;
                else if(board[i] == 'O')
                    gameStatus = O_WON;

                return gameStatus;
            }
        }

        //Checking diagonal starting at the top left corner.
        if (board[0] == board[4] && board[0] == board[8] && board[0] != 'E') {
            if (board[0] == 'X')
                gameStatus = X_WON;
            else if (board[0] == 'O')
                gameStatus = O_WON;

            return gameStatus;
        }

        //Checking diagonal starting at the top right corner.
        if (board[2] == board[4] && board[2] == board[6] && board[2] != 'E') {
            if (board[2] == 'X')
                gameStatus = X_WON;
            else if (board[2] == 'O')
                gameStatus = O_WON;

            return gameStatus;
        }

        //Checking if the board is full of tiles.
        for(int i = 0; i < 9; i++) {
            if(board[i] == 'E') {
                gameStatus = GAME_NOT_FINISHED;
                break;
            }
        }

        return gameStatus;
    }
}
