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
  public void clickNextPuzzle() {}

  @Override
  public void clickPrevPuzzle() {}

  @Override
  public void clickRandPuzzle() {}

  @Override
  public void clickResetPuzzle() {}

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
