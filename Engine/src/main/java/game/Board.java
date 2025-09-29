package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Board {
    private final int size;
    private final char[][] grid;
    private int moveCount;

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        this.moveCount = 0;

        for(int i = 0; i < size; ++i) {
            Arrays.fill(this.grid[i], '.');
        }

    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < this.size && y >= 0 && y < this.size && this.grid[x][y] == '.';
    }

    public void placePiece(int x, int y, char color) {
        if (this.isValidMove(x, y)) {
            this.grid[x][y] = color;
            ++this.moveCount;
        }
    }

    public boolean isFull() {
        return this.moveCount >= this.size * this.size;
    }

    public char getCell(int x, int y) {
        return this.grid[x][y];
    }

    public int getSize() {
        return this.size;
    }

    public List<int[]> getEmptyCells() {
        List<int[]> empty = new ArrayList();

        for(int i = 0; i < this.size; ++i) {
            for(int j = 0; j < this.size; ++j) {
                if (this.grid[i][j] == '.') {
                    empty.add(new int[]{i, j});
                }
            }
        }

        return empty;
    }

    public boolean checkWin(int x, int y, char color) {
        Set<Point> points = new HashSet();

        for(int i = 0; i < this.size; ++i) {
            for(int j = 0; j < this.size; ++j) {
                if (this.grid[i][j] == color) {
                    points.add(new Point(i, j));
                }
            }
        }

        if (points.size() < 4) {
            return false;
        } else {
            Point P = new Point(x, y);

            for(Point Q : points) {
                if (!Q.equals(P)) {
                    int dx = Q.x - P.x;
                    int dy = Q.y - P.y;
                    Point R1 = new Point(P.x - dy, P.y + dx);
                    Point S1 = new Point(Q.x - dy, Q.y + dx);
                    if (points.contains(R1) && points.contains(S1)) {
                        return true;
                    }

                    Point R2 = new Point(P.x + dy, P.y - dx);
                    Point S2 = new Point(Q.x + dy, Q.y - dx);
                    if (points.contains(R2) && points.contains(S2)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public void print() {
        int cellWidth = 2;
        int numWidth = String.valueOf(this.size).length();
        System.out.print(" ".repeat(numWidth + 1));

        for(int j = 0; j < this.size; ++j) {
            System.out.printf("%" + cellWidth + "d ", j);
        }

        System.out.println();
        System.out.print(" ".repeat(numWidth + 1));
        System.out.println("-".repeat(this.size * (cellWidth + 1) + 1));

        for(int i = 0; i < this.size; ++i) {
            System.out.printf("%" + numWidth + "d |", i);

            for(int j = 0; j < this.size; ++j) {
                char c = this.grid[i][j] == '.' ? 183 : this.grid[i][j];
                System.out.printf("%" + cellWidth + "c ", c);
            }

            System.out.println();
        }

    }

    public boolean wouldWin(int x, int y, char color) {
        if (this.grid[x][y] != '.') {
            return false;
        } else {
            this.grid[x][y] = color;
            boolean wins = this.checkWin(x, y, color);
            this.grid[x][y] = '.';
            return wins;
        }
    }

    public void setGrid(char[][] newGrid) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(newGrid[i], 0, grid[i], 0, size);
        }
        moveCount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != '.') moveCount++;
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    private static class Point {
        final int x;
        final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                Point point = (Point)o;
                return this.x == point.x && this.y == point.y;
            } else {
                return false;
            }
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.x, this.y});
        }
    }
}
