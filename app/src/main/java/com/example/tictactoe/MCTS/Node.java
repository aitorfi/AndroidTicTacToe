package com.example.tictactoe.MCTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Node {
    private Node parentNode;
    private State state;
    private List<Node> childNodes;

    public Node() {
        state = new State();
        childNodes = new ArrayList<>();
    }

    public Node(Node parentNode, State state) {
        this.parentNode = parentNode;
        this.state = state;
        childNodes = new ArrayList<>();
    }

    public Node(Node node) {
        parentNode = node.getParentNode();
        state = new State(node.getState());
        childNodes = new ArrayList<>();

        node.getChildNodes().forEach(n -> {
            childNodes.add(new Node(n));
        });
    }

    public Node getRandomChildNode() {
        Random rand = new Random();

        return childNodes.get(rand.nextInt(childNodes.size()));
    }

    public Node getChildWithMaxScore() {
        return Collections.max(childNodes, Comparator.comparing(c -> c.getState().getWinScore()));
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<Node> childNodes) {
        this.childNodes = childNodes;
    }
}
