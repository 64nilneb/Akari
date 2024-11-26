package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent, ModelObserver {
    private ControllerImpl controller;
    private Model model;
    private int currPuzzle;
    private Label label;
    public ControlView(ControllerImpl controller) {
        this.controller = controller;
        this.model = controller.getModel();
        this.currPuzzle = model.getActivePuzzleIndex();
        this.label = new Label("Puzzle " + (model.getActivePuzzleIndex()+1) + " of " + model.getPuzzleLibrarySize());
        this.label.setStyle("-fx-font-size: 32px;" +
                            "-fx-text-fill: white;");
        model.addObserver(this);
    }
    @Override
    public Parent render() {
        Button backwardButton = new Button("Backward");
        Button forwardButton = new Button("Forward");
        Button resetButton = new Button("Reset");
        Button randomPuzzleButton = new Button("Random Puzzle");

        forwardButton.setStyle(
            "-fx-background-color: #007BFF;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 8px 16px;" +
            "-fx-cursor: hand;");

        resetButton.setStyle(
            "-fx-background-color: #007BFF;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 8px 16px;" +
            "-fx-cursor: hand;");

        randomPuzzleButton.setStyle(
            "-fx-background-color: #007BFF;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 8px 16px;" +
            "-fx-cursor: hand;");

        backwardButton.setStyle(
            "-fx-background-color: #007BFF;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 8px 16px;" +
            "-fx-cursor: hand;");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backwardButton, forwardButton, resetButton, randomPuzzleButton);

        BorderPane layout = new BorderPane();
        layout.setBottom(buttonBox);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, layout);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        vbox.setPadding(new Insets(0, 0, 50, 0));

        forwardButton.setOnAction(event -> {
            controller.clickNextPuzzle();
        });

        backwardButton.setOnAction(event -> {
            controller.clickPrevPuzzle();
        });

        resetButton.setOnAction(event -> {
            controller.clickResetPuzzle();
        });

        randomPuzzleButton.setOnAction(event -> {
            controller.clickRandPuzzle();
        });

        return vbox;
    }

    private void updatePuzzle() {
        label.setText("Puzzle " + (model.getActivePuzzleIndex()+1) + " of " + model.getPuzzleLibrarySize());
    }

    @Override
    public void update(Model model) {
        if (currPuzzle != model.getActivePuzzleIndex()) {
            currPuzzle = model.getActivePuzzleIndex();
            updatePuzzle();
        }
    }
}