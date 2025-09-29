package util;

import game.Game;
import game.Player;
import game.PlayerType;

public class InputParser {
    public static Game parseGameCommand(String args) {
        String[] parts = args.split(",", 3);
        if (parts.length != 3) return null;

        try {
            int n = Integer.parseInt(parts[0].trim());
            if (n <= 2) return null;

            String p1Str = parts[1].trim();
            String p2Str = parts[2].trim();

            Player p1 = parsePlayer(p1Str);
            Player p2 = parsePlayer(p2Str);
            if (p1 == null || p2 == null || p1.color() == p2.color()) {
                return null;
            }

            return new Game(n, p1, p2);
        } catch (Exception e) {
            return null;
        }
    }

    private static Player parsePlayer(String s) {
        String[] tokens = s.split("\\s+");
        if (tokens.length != 2) return null;

        PlayerType type = switch (tokens[0]) {
            case "user" -> PlayerType.USER;
            case "comp" -> PlayerType.COMP;
            default -> null;
        };
        if (type == null) return null;

        char color = tokens[1].charAt(0);
        if (color != 'W' && color != 'B') return null;

        return new Player(type, color);
    }

    public static int[] parseMoveCommand(String args) {
        String[] coords = args.split(",", 2);
        if (coords.length != 2) return null;
        try {
            int x = Integer.parseInt(coords[0].trim());
            int y = Integer.parseInt(coords[1].trim());
            return new int[]{x, y};
        } catch (NumberFormatException e) {
            return null;
        }
    }
}