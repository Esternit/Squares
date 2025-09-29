package web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import game.Game;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Класс DTO, описывающий состояние игры")
public class BoardDto {
    @Schema(description = "Размер игровой доски", example = "5")
    private int size;

    @Schema(description = "Состояние доски в виде строки", example = "       b   w w   b       ")
    private String data;

    @Schema(description = "Цвет очередного игрока", example = "b")
    @JsonProperty("nextPlayerColor")
    private String nextPlayerColor;

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getNextPlayerColor() { return nextPlayerColor; }
    public void setNextPlayerColor(String nextPlayerColor) { this.nextPlayerColor = nextPlayerColor; }
}
