package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

    library.addPuzzle(puzzle1);
    library.addPuzzle(puzzle2);
    library.addPuzzle(puzzle3);
    library.addPuzzle(puzzle4);
    library.addPuzzle(puzzle5);

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
