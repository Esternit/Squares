package web.dto;

public class GameStateDto {
    public boolean isOver;
    public String winner;

    public GameStateDto(boolean isOver, String winner) {
        this.isOver = isOver;
    }

    public boolean isOver() {
        return isOver;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }
}
