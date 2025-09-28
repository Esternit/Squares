package web.controller;

import ai.AI;
import game.Board;
import game.Game;
import game.Player;
import game.PlayerType;
import org.springframework.web.bind.annotation.*;
import web.dto.BoardDto;
import web.dto.SimpleMoveDto;

@RestController
@RequestMapping("/api")
public class GameController {

    @PostMapping("/next-move")
    public SimpleMoveDto getNextMove(
            @RequestBody BoardDto request) {

        char[][] grid = parseData(request.getData(), request.getSize());
        Board board = new Board(request.getSize());
        board.setGrid(grid);

        char color = request.getNextPlayerColor().charAt(0);
        Player current = new Player(PlayerType.COMP, color);
        char oppColor = (color == 'w' || color == 'W') ? 'b' : 'w';
        Player opponent = new Player(PlayerType.USER, oppColor);
        Game game = new Game(request.getSize(), current, opponent);
        game.setBoard(board);

        int[] move = AI.getBestMove(game);
        if (move == null) {
            throw new IllegalStateException("No move available");
        }

        SimpleMoveDto response = new SimpleMoveDto();
        response.setX(move[0]);
        response.setY(move[1]);
        response.setColor(String.valueOf(color));
        return response;
    }

    private char[][] parseData(String data, int size) {
        String clean = data.replaceAll("[\\r\\n]", "");
        if (clean.length() != size * size) {
            throw new IllegalArgumentException("Invalid data length");
        }
        char[][] grid = new char[size][size];
        int k = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char c = clean.charAt(k++);
                grid[i][j] = (c == ' ') ? '.' : c;
            }
        }
        return grid;
    }
}
