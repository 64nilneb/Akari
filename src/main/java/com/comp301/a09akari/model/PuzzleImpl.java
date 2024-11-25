package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
    private int[][] board;
  public PuzzleImpl(int[][] board) {
      this.board = board;
  }

    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public CellType getCellType(int r, int c) {
      if (r >= getWidth() || c >= getHeight() || r < 0 || c < 0) {
          throw new IndexOutOfBoundsException("Out of bounds");
      }
      int cellVal = board[r][c];

      if (cellVal == 5) {
          return CellType.WALL;
      }
      else if (cellVal == 6) {
          return CellType.CORRIDOR;
      }
      else {
          return CellType.CLUE;
      }
    }

    @Override
    public int getClue(int r, int c) {
      if (r >= getWidth() || c >= getHeight() || r < 0 || c < 0) {
          throw new IndexOutOfBoundsException("Out o bounds sir");
      }

      if (getCellType(r, c) != CellType.CLUE) {
          throw new IllegalArgumentException("Not a clue");
      }

      return board[r][c];
    }
}
