package com.example.tictactoe.MCTS;

import com.example.tictactoe.game.TicTacToeGame;

import java.util.Collections;
import java.util.Comparator;

public class MonteCarloTreeSearch {
    static final int WIN_SCORE = 10;
    static final long TIME_LIMIT = 2000; // 2 seconds in millis.

    public UltimateBoard findNextMove(UltimateBoard board) {
        Tree tree = new Tree();
        Node rootNode = tree.getRootNode();
        Node winnerNode;
        double endTime;

        rootNode.getState().setBoard(board);
        rootNode.getState().setTurnX(true);

        endTime = System.currentTimeMillis() + TIME_LIMIT;

        while (System.currentTimeMillis() < endTime) {
            Node nodeToExplore;
            Node promisingNode;

            promisingNode = selectPromisingNode(rootNode); // Never gets a Node that has already been expanded.

            if (promisingNode.getState().getStatus() == TicTacToeGame.GAME_NOT_FINISHED) {
                expandNode(promisingNode); // Sets all the child Nodes of the promising Node.
            }

            if (promisingNode.getChildNodes().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            } else {
                nodeToExplore = promisingNode;
            }

            int boardStatus = simulateRandomPlayout(nodeToExplore);
            backPropagation(nodeToExplore, boardStatus);
        }

        winnerNode = rootNode.getChildWithMaxScore();

        return winnerNode.getState().getBoard();
    }

    private Node selectPromisingNode(Node rootNode) {
        Node promisingNode = rootNode;

        while (promisingNode.getChildNodes().size() != 0) {
            promisingNode = findBestNodeWithUCT(promisingNode);
        }

        return promisingNode;
    }

    private Node findBestNodeWithUCT(Node node) {
        int parentVisit = node.getState().getVisitCount();

        return Collections.max(node.getChildNodes(),
                Comparator.comparing(c -> utcValue(parentVisit,
                        c.getState().getWinScore(), c.getState().getVisitCount())));
    }

    private static double utcValue(int totalVisit, double nodeWinScore, double nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }

        return ((double) nodeWinScore / nodeVisit)
                + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    private void expandNode(Node node) {
        node.getState().getAllPossibleStates().forEach(s -> {
            Node newNode = new Node(node, s);
            node.getChildNodes().add(newNode);
        });
    }

    private int simulateRandomPlayout(Node nodeToExplore) {
        Node tempNode = new Node(nodeToExplore);
        State tempState = tempNode.getState();

        int boardStatus = tempState.getStatus();

        /*if (boardStatus == TicTacToeGame.X_WON) {
            tempNode.getParentNode().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }*/

        // Simulate a full random game from given Node.
        while (boardStatus == TicTacToeGame.GAME_NOT_FINISHED) {
            tempState.alternatePlayer();
            tempState.randomPlay();
            boardStatus = tempState.getStatus();
        }

        return boardStatus;
    }

    public void backPropagation(Node node, int boardStatus) {
        Node tempNode = node;

        while (tempNode != null) {
            tempNode.getState().incrementVisits();

            /*if (tempNode.getState().getStatus() == TicTacToeGame.O_WON) {
                tempNode.getState().addScore(WIN_SCORE);
            }*/

            if ((tempNode.getState().isTurnX() && boardStatus == TicTacToeGame.X_WON) ||
                (!tempNode.getState().isTurnX() && boardStatus == TicTacToeGame.O_WON)) {
                tempNode.getState().addScore(WIN_SCORE);
            }

            tempNode = tempNode.getParentNode();
        }
    }
}
