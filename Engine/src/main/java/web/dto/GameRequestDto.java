package web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на создание новой игры")
public class GameRequestDto {
    @Schema(description = "Размер доски", example = "5")
    private int size;

    @Schema(description = "Тип первого игрока: 'user' или 'comp'", example = "comp")
    private String playerType;

    @Schema(description = "Цвет первого игрока: 'W' или 'B'", example = "W")
    private String playerColor;

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getPlayerType() { return playerType; }
    public void setPlayerType(String player1Type) { this.playerType = player1Type; }

    public String getPlayerColor() { return playerColor; }
    public void setPlayerColor(String player1Color) { this.playerColor = player1Color; }
}