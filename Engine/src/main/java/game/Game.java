package game;

import ai.AI;

public class Game {
    private final Board board;
    private final Player[] players = new Player[2];
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;
    private Character winner = null;

    public Game(int size, Player p1, Player p2) {
        this.board = new Board(size);
        this.players[0] = p1;
        this.players[1] = p2;
    }

    public boolean makeMove(int x, int y) {
        if (!this.gameOver && this.board.isValidMove(x, y)) {
            char color = this.getCurrentPlayerColor();
            this.board.placePiece(x, y, color);
            if (this.board.checkWin(x, y, color)) {
                this.gameOver = true;
                this.winner = color;
                System.out.printf("%c (%d, %d)\n", color, x, y);
                this.board.print();
                if (this.winner != null) {
                    System.out.println("Game finished. " + this.winner + " wins!");
                } else {
                    System.out.println("Game finished. Draw");
                }
            } else if (this.board.isFull()) {
                this.gameOver = true;
                System.out.printf("%c (%d, %d)\n", color, x, y);
                this.board.print();
                System.out.println("Game finished. Draw");
            } else {
                System.out.printf("%c (%d, %d)\n", color, x, y);
                this.board.print();
                this.currentPlayerIndex = 1 - this.currentPlayerIndex;
            }

            return true;
        } else {
            return false;
        }
    }

    public void playAIFullTurn() {
        while(true) {
            if (!this.gameOver && this.getCurrentPlayer().isAI()) {
                int[] move = AI.getBestMove(this);
                if (move != null) {
                    this.makeMove(move[0], move[1]);
                    continue;
                }
            }

            return;
        }
    }

    public int[] getAIMove() {
        return AI.getBestMove(this);
    }

    public char getCurrentPlayerColor() {
        return this.players[this.currentPlayerIndex].color();
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
    }

    public boolean isCurrentPlayerAI() {
        return this.getCurrentPlayer().isAI();
    }

    public boolean isGameOver() {
        return this.gameOver;
    }
}
