
package main;

import game.Game;
import java.util.Scanner;
import util.InputParser;

public class Main {
    public static void main(String[] args) {
        System.out.println("Square Game. Type HELP for commands.");
        Scanner scanner = new Scanner(System.in);
        Game currentGame = null;

        while(true) {
            String input = currentGame != null && currentGame.isCurrentPlayerAI() ? generateAIMoveCommand(currentGame) : scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    String[] parts = input.split("\\s+", 2);
                    String command = parts[0].toUpperCase();
                    String commandArgs = parts.length > 1 ? parts[1] : "";
                    switch (command) {
                        case "GAME":
                            currentGame = InputParser.parseGameCommand(commandArgs);
                            if (currentGame != null) {
                                System.out.println("New game started");
                                if (currentGame.isCurrentPlayerAI()) {
                                    currentGame.playAIFullTurn();
                                }
                            }
                            break;
                        case "MOVE":
                            if (currentGame == null) {
                                System.out.println("Game is not started");
                            } else {
                                int[] coords = InputParser.parseMoveCommand(commandArgs);
                                if (coords == null) {
                                    System.out.println("Incorrect command");
                                } else {
                                    currentGame.makeMove(coords[0], coords[1]);
                                    if (currentGame.isGameOver()) {
                                        currentGame = null;
                                    } else if (currentGame.isCurrentPlayerAI()) {
                                        currentGame.playAIFullTurn();
                                    }
                                }
                            }
                            break;
                        case "EXIT":
                            System.out.println("Goodbye!");
                            return;
                        case "HELP":
                            printHelp();
                            break;
                        default:
                            System.out.println("Incorrect command");
                    }
                } catch (Exception var10) {
                    System.out.println("Incorrect command");
                }
            }
        }
    }

    private static String generateAIMoveCommand(Game game) {
        int[] move = game.getAIMove();
        return "MOVE " + move[0] + "," + move[1];
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  GAME N, TYPE1 COLOR1, TYPE2 COLOR2");
        System.out.println("      Start a new game. N > 2.");
        System.out.println("      TYPE: 'user' or 'comp', COLOR: 'W' or 'B'");
        System.out.println("  MOVE X,Y        Make a move at (X,Y)");
        System.out.println("  EXIT            Exit the program");
        System.out.println("  HELP            Show this help");
    }
}
