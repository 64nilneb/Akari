package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
    Puzzle puzzle6 = new PuzzleImpl(SamplePuzzles.PUZZLE_06);
    Puzzle puzzle7 = new PuzzleImpl(SamplePuzzles.PUZZLE_07);
    Puzzle puzzle8 = new PuzzleImpl(SamplePuzzles.PUZZLE_08);
    Puzzle puzzle9 = new PuzzleImpl(SamplePuzzles.PUZZLE_09);
    Puzzle puzzle10 = new PuzzleImpl(SamplePuzzles.PUZZLE_10);
    Puzzle puzzle11 = new PuzzleImpl(SamplePuzzles.PUZZLE_11);
    Puzzle puzzle12 = new PuzzleImpl(SamplePuzzles.PUZZLE_12);
    Puzzle puzzle13 = new PuzzleImpl(SamplePuzzles.PUZZLE_13);
    Puzzle puzzle14 = new PuzzleImpl(SamplePuzzles.PUZZLE_14);
    Puzzle puzzle15 = new PuzzleImpl(SamplePuzzles.PUZZLE_15);
    Puzzle puzzle16 = new PuzzleImpl(SamplePuzzles.PUZZLE_16);

    library.addPuzzle(puzzle1);
    library.addPuzzle(puzzle2);
    library.addPuzzle(puzzle3);
    library.addPuzzle(puzzle4);
    library.addPuzzle(puzzle5);
    library.addPuzzle((puzzle6));
    library.addPuzzle((puzzle7));
    library.addPuzzle((puzzle8));
    library.addPuzzle((puzzle9));
    library.addPuzzle((puzzle10));
    library.addPuzzle((puzzle11));
    library.addPuzzle((puzzle12));
    library.addPuzzle((puzzle13));
    library.addPuzzle((puzzle14));
    library.addPuzzle((puzzle15));
    library.addPuzzle((puzzle16));


    Model model = new ModelImpl(library);
    model.setActivePuzzleIndex(0);
    ControllerImpl controller = new ControllerImpl(model);

    FXComponent puzzleView = new PuzzleView(controller);
    FXComponent controlView = new ControlView(controller);

    VBox vbox = new VBox();
    vbox.getChildren().addAll(puzzleView.render());
    vbox.setAlignment(Pos.CENTER);

    HBox hbox = new HBox();
    hbox.getChildren().add(vbox);
    hbox.setAlignment(Pos.CENTER);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(hbox);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(stackPane);
    borderPane.setBottom(controlView.render());

    borderPane.setStyle("-fx-background-color: #192436");

    FXComponent solvedMessage = new SolvedMessage(controller, stackPane);

    Scene scene = new Scene(borderPane, 900, 900);
    stage.setTitle("Akari");
    stage.setScene(scene);

    stage.show();
  }
}
