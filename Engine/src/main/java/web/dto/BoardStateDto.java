package web.dto;

import game.Game;

public class BoardStateDto {
    private int size;
    private String[] data;
    private boolean isGameOver;
    private String winner;

    public BoardStateDto(Game game) {
        this.size = game.getBoard().getSize();
        char[][] grid = game.getBoard().getGrid();
        this.data = new String[grid.length];
        for (int i = 0; i < grid.length; i++) {
            this.data[i] = new String(grid[i]);
        }

        this.isGameOver = game.isGameOver();
        if (this.isGameOver) {
            Character win = game.getWinner();
            if (win == null) {
                this.winner = "draw";
            } else {
                this.winner = String.valueOf(win).toUpperCase();
            }
        } else {
            this.winner = null;
        }
    }

    public int getSize() { return size; }
    public String[] getData() { return data; }
    public boolean isGameOver() { return isGameOver; }
    public String getWinner() { return winner; }
}