package web.dto;

import game.Game;

public class BoardStateDto {
    private int size;
    private char[][] data;

    public BoardStateDto(Game game) {
        this.size = game.getBoard().getSize();
        this.data = game.getBoard().getGrid();
    }

    public int getSize() {
        return size;
    }

    public char[][] getData() {
        return data;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setData(char[][] data) {
        this.data = data;
    }
}
