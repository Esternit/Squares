package web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Класс DTO, описывающий ход игрока")
public class SimpleMoveDto {
    @Schema(description = "Координата x", example = "2")
    private int x;

    @Schema(description = "Координата y", example = "3")
    private int y;

    @Schema(description = "Цвет фишки", example = "b")
    private String color;

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}