package game;

public record Player(PlayerType type, char color) {
    public boolean isAI() {
        return this.type == PlayerType.COMP;
    }
}
