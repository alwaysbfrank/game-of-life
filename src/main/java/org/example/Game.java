package org.example;

import java.util.Set;
import java.util.stream.IntStream;

import static java.lang.System.out;
public class Game {

    private static final String COLOR_RED = "";
    private static final String COLOR_BLACK = "";
    private static final String COLOR_GREEN = "";
    private static final String ALIVE = "#";
    private static final String DEAD = ".";
    private static final String UPPER_GRID = COLOR_BLACK + "_";
    private static final String LOWER_GRID = COLOR_BLACK + "-";
    private static final String SIDE_GRID = COLOR_BLACK + "|";
    private final Rule rule;
    private final Integer gridSize;
    private boolean[][] currentGrid;
    private boolean[][] previousGrid;
    public Game(ArgumentParser arguments) {
        rule = arguments.getRule();
        gridSize = arguments.getGridSize();
        initializeGrid(arguments.getSeed());
    }

    public void play() {
        for (int i = 1; i < 200; i++) {
            out.println("Generation " + i);
            showGrid(currentGrid);
            playRound();
            if (nothingChanged()) {
                break;
            }
        }
    }

    private void playRound() {
        var nextGrid = getNewGrid();

        for (int x = 0; x < nextGrid.length; x++) {
            var row = nextGrid[x];
            for (int y = 0; y < row.length; y++) {
                var aliveNeighbours = countNeighbours(x, y);
                nextGrid[x][y] = shouldBeAlive(currentGrid[x][y], aliveNeighbours);
            }
        }
        previousGrid = currentGrid;
        currentGrid = nextGrid;
    }

    private boolean shouldBeAlive(boolean isAlive, int aliveNeighbours) {
        return isAlive
                ? rule.getShouldSurvive().contains(aliveNeighbours)
                : rule.getShouldArise().contains(aliveNeighbours);
    }

    private int countNeighbours(int x, int y) {
        int result = 0;
        for (int ix = -1; ix < 2; ix++) {
            for (int iy = -1; iy < 2; iy++) {
                if (ix != 0 || iy != 0) {
                    result += countIfAlive(x + ix, y + iy);
                }
            }
        }
        return result;
    }

    private int countIfAlive(int x, int y) {
        return checkIfAlive(x, y) ? 1 : 0;
    }

    private boolean checkIfAlive(int x, int y) {
        try {
            return currentGrid[x % gridSize][y % gridSize];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    private void initializeGrid(Set<Coordinates> seed) {
        currentGrid = getNewGrid();
        seed.forEach(coord -> currentGrid[coord.getX()][coord.getY()] = true);
        previousGrid = getNewGrid();
    }

    private boolean[][] getNewGrid() {
        return new boolean[gridSize][gridSize];
    }

    private void showGrid(boolean[][] grid) {
        printBorder(UPPER_GRID);
        out.println();
        for (int x = 0; x < grid.length; x++) {
            var row = grid[x];
            out.print(SIDE_GRID);
            for (int y = 0; y < row.length; y++) {
                var cell = row[y];
                var prevCell = previousGrid[x][y];
                var color = COLOR_BLACK;
                var result = "";
                if (cell) {
                    result = ALIVE;
                    if (!prevCell) color = COLOR_GREEN;
                } else {
                    result = DEAD;
                    if (prevCell) color = COLOR_RED;
                }
                out.print(color + result);
            }
            out.print(SIDE_GRID);
            out.println();
        }
        printBorder(LOWER_GRID);
        out.println();
    }

    private boolean nothingChanged() {
        for (int x = 0; x < currentGrid.length; x++) {
            var row = currentGrid[x];
            for (int y = 0; y < row.length; y++) {
                var cell = row[y];
                var prevCell = previousGrid[x][y];
                if (cell != prevCell) return false;
            }
        }
        return true;
    }

    private void printBorder(String upperGrid) {
        IntStream.rangeClosed(-1, gridSize).forEach(i -> out.print(upperGrid));
    }
}
