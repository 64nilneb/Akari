package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI

    PuzzleLibrary library = new PuzzleLibraryImpl();
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    library.addPuzzle(puzzle1);
    library.addPuzzle(puzzle2);
    library.addPuzzle(puzzle3);
    library.addPuzzle(puzzle4);
    library.addPuzzle(puzzle5);

    Model model = new ModelImpl(library);
    model.setActivePuzzleIndex(0);
    ControllerImpl controller = new ControllerImpl(model);

    FXComponent puzzleView = new PuzzleView(controller);
    FXComponent solvedMessage = new SolvedMessage(controller);

    BorderPane root = new BorderPane();
    root.setCenter(puzzleView.render());

    Scene scene = new Scene(root, 1000, 800);
    stage.setTitle("Akari");
    stage.setScene(scene);

    stage.show();
  }
}
