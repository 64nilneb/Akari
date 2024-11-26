package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
public class ControllerImpl implements ClassicMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  public Model getModel() {
    return model;
  }

  @Override
  public void clickNextPuzzle() {
    int size = model.getPuzzleLibrarySize();
    if (size == 0) {
      return;
    }

    int curr = model.getActivePuzzleIndex();
    if (curr >= size - 1) {
      model.setActivePuzzleIndex(0);
    } else {
      model.setActivePuzzleIndex(curr + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int size = model.getPuzzleLibrarySize();
    if (size == 0) {
      return;
    }

    int curr = model.getActivePuzzleIndex();
    if (curr == 0) {
      model.setActivePuzzleIndex(size - 1);
    } else {
      model.setActivePuzzleIndex(curr - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int size = model.getPuzzleLibrarySize();
    int rand = (int)(Math.random() * size);

    while (rand == model.getActivePuzzleIndex()) {
      rand = (int)(Math.random() * size);
    }

    model.setActivePuzzleIndex(rand);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
