package ai;

import game.Board;
import game.Game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AI {
    public static int[] getBestMove(Game game) {
        Board board = game.getBoard();
        char currentColor = game.getCurrentPlayerColor();
        char opponentColor = (char)(currentColor == 'W' ? 66 : 87);
        List<int[]> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) {
            return null;
        } else {
            for(int[] cell : emptyCells) {
                if (board.wouldWin(cell[0], cell[1], currentColor)) {
                    return cell;
                }
            }

            for(int[] cell : emptyCells) {
                if (board.wouldWin(cell[0], cell[1], opponentColor)) {
                    return cell;
                }
            }

            Set<int[]> myPotential = getPotentialCells(board, currentColor);
            Set<int[]> oppPotential = getPotentialCells(board, opponentColor);
            int bestScore = Integer.MIN_VALUE;
            int[] bestMove = null;

            for(int[] cell : emptyCells) {
                int score = 0;
                if (containsPoint(myPotential, cell)) {
                    score += 2;
                }

                if (containsPoint(oppPotential, cell)) {
                    --score;
                }

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = cell;
                }
            }

            return bestMove != null ? bestMove : randomChoice(emptyCells);
        }
    }

    private static boolean containsPoint(Set<int[]> set, int[] point) {
        for(int[] p : set) {
            if (Arrays.equals(p, point)) {
                return true;
            }
        }

        return false;
    }

    private static int[] randomChoice(List<int[]> list) {
        return (int[])list.get((new Random()).nextInt(list.size()));
    }

    private static Set<int[]> getPotentialCells(Board board, char color) {
        int n = board.getSize();
        List<int[]> points = new ArrayList();

        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                if (board.getCell(i, j) == color) {
                    points.add(new int[]{i, j});
                }
            }
        }

        Set<int[]> potential = new HashSet();
        if (points.size() < 2) {
            return potential;
        } else {
            for(int i = 0; i < points.size(); ++i) {
                for(int j = i + 1; j < points.size(); ++j) {
                    int[] A = (int[])points.get(i);
                    int[] B = (int[])points.get(j);
                    int ax = A[0];
                    int ay = A[1];
                    int bx = B[0];
                    int by = B[1];
                    int dx = bx - ax;
                    int dy = by - ay;
                    int[][] candidates = new int[][]{{ax - dy, ay + dx}, {bx - dy, by + dx}, {ax + dy, ay - dx}, {bx + dy, by - dx}};

                    for(int[] pt : candidates) {
                        int x = pt[0];
                        int y = pt[1];
                        if (x >= 0 && x < n && y >= 0 && y < n && board.getCell(x, y) == '.') {
                            potential.add(new int[]{x, y});
                        }
                    }
                }
            }

            return potential;
        }
    }
}
