package com.comp301.a09akari.model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelImpl implements Model {
    private PuzzleLibrary library;
    private int currPuzzle;
    private List<ModelObserver> modelObservers;
    private int[][] lamp;
    public ModelImpl(PuzzleLibrary library) {
        this.library = library;
        this.currPuzzle = 0;
        this.modelObservers = new ArrayList<>();

        this.lamp = new int[library.getPuzzle(currPuzzle).getHeight()][library.getPuzzle(currPuzzle).getHeight()];
    }
    @Override
    public void addLamp(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        lamp[r][c] = 2;

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        int maxHeight = library.getPuzzle(currPuzzle).getHeight();
        int maxWidth = library.getPuzzle(currPuzzle).getWidth();

        for(int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            int x = c;
            int y = r;

            while (x < maxWidth && y < maxHeight && x >= 0 && y >= 0) {
                if (lamp[y][x] == 2) {
                    x += dx;
                    y += dy;
                    continue;
                }

                if (library.getPuzzle(currPuzzle).getCellType(y, x) == CellType.WALL) {
                    break;
                }

                if (library.getPuzzle(currPuzzle).getCellType(y, x) == CellType.CLUE) {
                    break;
                }

                else {
                    lamp[y][x] = 1;
                }

                x += dx;
                y += dy;
            }
        }

        notifyObservers();
    }

    @Override
    public void removeLamp(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        int maxHeight = library.getPuzzle(currPuzzle).getHeight();
        int maxWidth = library.getPuzzle(currPuzzle).getWidth();

        for(int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            int x = c;
            int y = r;

            while (x < maxWidth && y < maxHeight && x >= 0 && y >= 0) {
                if (library.getPuzzle(currPuzzle).getCellType(y, x) == CellType.WALL) {
                    break;
                }

                if (library.getPuzzle(currPuzzle).getCellType(y, x) == CellType.CLUE) {
                    break;
                }

                else {
                    lamp[y][x] = 0;
                }

                x += dx;
                y += dy;
            }
        }

        notifyObservers();
    }

    @Override
    public boolean isLit(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        return lamp[r][c] == 1 || lamp[r][c] == 2;
    }

    @Override
    public boolean isLamp(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        return lamp[r][c] == 2;
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        if (lamp[r][c] != 2) {
            throw new IllegalArgumentException("Not lamp");
        }

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        int maxHeight = library.getPuzzle(currPuzzle).getHeight();
        int maxWidth = library.getPuzzle(currPuzzle).getWidth();

        for(int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            int x = c;
            int y = r;

            while (x < maxWidth && y < maxHeight && x >= 0 && y >= 0) {
                if (library.getPuzzle(currPuzzle).getCellType(y, x) == CellType.WALL) {
                    break;
                }

                if (library.getPuzzle(currPuzzle).getCellType(y, x) == CellType.CLUE) {
                    break;
                }

                if (lamp[y][x] == 2 && (x != c || y != r)) {
                    return true;
                }

                x += dx;
                y += dy;
            }
        }
        return false;
    }

    @Override
    public Puzzle getActivePuzzle() {
        return library.getPuzzle(currPuzzle);
    }

    @Override
    public int getActivePuzzleIndex() {
        return currPuzzle;
    }

    @Override
    public void setActivePuzzleIndex(int index) {
        if (index < 0 || index >= library.size()) {
            throw new IndexOutOfBoundsException("No puzzles");
        }
        currPuzzle = index;
        resetPuzzle();

        notifyObservers();
    }

    @Override
    public int getPuzzleLibrarySize() {
        return library.size();
    }

    @Override
    public void resetPuzzle() {
        Puzzle curr = library.getPuzzle(currPuzzle);
        lamp = new int[curr.getHeight()][curr.getWidth()];

        notifyObservers();
    }

    @Override
    public boolean isSolved() {
        Puzzle curr = library.getPuzzle(currPuzzle);
        for (int i = 0; i < lamp.length; i++) {
            for (int j = 0; j < lamp[0].length; j++) {
                if (curr.getCellType(i, j) == CellType.CORRIDOR) {
                    if (!isLit(i, j)) {
                        return false;
                    }
                    if (isLamp(i,j) && isLampIllegal(i, j)) {
                        return false;
                    }
                }

                else if (curr.getCellType(i, j) == CellType.CLUE) {
                    if (!isClueSatisfied(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CLUE) {
            throw new IllegalArgumentException("Not clue");
        }

        Puzzle curr = library.getPuzzle(currPuzzle);

        int clues = curr.getClue(r, c);
        int numLamps = 0;
        if (r > 0 && lamp[r - 1][c] == 2) numLamps++;
        if (c > 0 && lamp[r][c - 1] == 2) numLamps++;
        if (r < curr.getHeight() - 1 && lamp[r + 1][c] == 2) numLamps++;
        if (c < curr.getWidth() - 1 && lamp[r][c + 1] == 2) numLamps++;

        for (int i = 0; i < lamp.length; i++) {
            System.out.println(Arrays.toString(lamp));
        }
        System.out.println();

        return numLamps == clues;
    }

    @Override
    public void addObserver(ModelObserver observer) {
        modelObservers.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer) {
        modelObservers.remove(observer);
    }

    private void notifyObservers() {
        for (ModelObserver mo : modelObservers) {
            mo.update(this);
        }
    }
}
