package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private PuzzleLibrary library;
    private int currPuzzle;
    private int[][] lamp;
    List<ModelObserver> observers;
    public ModelImpl(PuzzleLibrary library) {
        this.library = library;
        this.currPuzzle = 0;
        this.observers = new ArrayList<>();

        lamp = new int[library.getPuzzle(currPuzzle).getWidth()][library.getPuzzle(currPuzzle).getHeight()];
    }

    @Override
    public void addLamp(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Outtie bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        lamp[r][c] = 1;
        notify(this);
    }

    @Override
    public void removeLamp(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Outtie bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        lamp[r][c] = 0;
        notify(this);
    }

    @Override
    public boolean isLit(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Outtie bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        int i = r;
        int j = c;

        int x = library.getPuzzle(currPuzzle).getWidth();
        int y = library.getPuzzle(currPuzzle).getHeight();

        while (i < y) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1) {
                return true;
            }
            else {
                i++;
            }
        }

        i = r;

        while (i >= 0) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1) {
                return true;
            }
            else {
                i--;
            }
        }

        i = r;

        while (j < x) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1) {
                return true;
            }
            else {
                j++;
            }
        }

        j = c;

        while (j >= 0) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1) {
                return true;
            }
            else {
                j--;
            }
        }

        return false;
    }

    @Override
    public boolean isLamp(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Outtie bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        return lamp[r][c] == 1;
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Outtie bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CORRIDOR) {
            throw new IllegalArgumentException("Not corridor");
        }

        if (lamp[r][c] != 1) {
            return false;
        }

        int i = r;
        int j = c;

        int x = library.getPuzzle(currPuzzle).getWidth();
        int y = library.getPuzzle(currPuzzle).getHeight();

        while (i < y) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                return true;
            }
            else {
                i++;
            }
        }

        i = r;

        while (i >= 0) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                return true;
            }
            else {
                i--;
            }
        }

        i = r;

        while (j < x) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                return true;
            }
            else {
                j++;
            }
        }

        j = c;

        while (j >= 0) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                return true;
            }
            else {
                j--;
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
            throw new IndexOutOfBoundsException("Index doesn't work");
        }
        currPuzzle = index;
        lamp = new int[library.getPuzzle(currPuzzle).getHeight()][library.getPuzzle(currPuzzle).getWidth()];
        notify(this);
    }

    @Override
    public int getPuzzleLibrarySize() {
        return library.size();
    }

    @Override
    public void resetPuzzle() {
        for (int i = 0; i < lamp.length; i++) {
            for (int j = 0; j < lamp[0].length; j++) {
                lamp[i][j] = 0;
            }
        }

        notify(this);
    }

    @Override
    public boolean isSolved() {
        Puzzle puzzle = library.getPuzzle(currPuzzle);
        for (int i = 0; i < lamp.length; i++) {
            for (int j = 0; j < lamp[0].length; j++) {
                if (puzzle.getCellType(i, j) == CellType.CORRIDOR && (!isLit(i, j) || isLampIllegal(i, j))) {
                    return false;
                }

                else if (puzzle.getCellType(i, j) == CellType.CLUE && !isClueSatisfied(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        if (r < 0 || c < 0 || r >= library.getPuzzle(currPuzzle).getHeight() || c >= library.getPuzzle(currPuzzle).getWidth()) {
            throw new IndexOutOfBoundsException("Outtie bounds");
        }

        if (library.getPuzzle(currPuzzle).getCellType(r, c) != CellType.CLUE) {
            throw new IllegalArgumentException("Not clue");
        }

        int numLamps = library.getPuzzle(currPuzzle).getClue(r, c);
        int countedLamps = 0;

        int i = r;
        int j = c;

        int x = library.getPuzzle(currPuzzle).getWidth();
        int y = library.getPuzzle(currPuzzle).getHeight();

        while (i < y) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                countedLamps++;
            }
            else {
                i++;
            }
        }

        i = r;

        while (i >= 0) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                countedLamps++;
            }
            else {
                i--;
            }
        }

        i = r;

        while (j < x) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                countedLamps++;
            }
            else {
                j++;
            }
        }

        j = c;

        while (j >= 0) {
            if (library.getPuzzle(currPuzzle).getCellType(i, j) == CellType.WALL) {
                break;
            }
            if (lamp[i][j] == 1 && (i != r || j != c)) {
                countedLamps++;
            }
            else {
                j--;
            }
        }

        if (lamp[r][c] == 1) {
            countedLamps++;
        }

        return countedLamps == numLamps;
    }

    @Override
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    private void notify(Model model) {
        for (ModelObserver mo : observers) {
            mo.update(model);
        }
    }
}
