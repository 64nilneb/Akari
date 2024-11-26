package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SolvedMessage implements FXComponent, ModelObserver {
    private Model model;
    private ControllerImpl controller;
    private StackPane stackPane;
    private boolean displayed;
    private StackPane currMessage;
    public SolvedMessage(ControllerImpl controller, StackPane stackPane) {
        this.model = controller.getModel();
        this.controller = controller;
        this.stackPane = stackPane;
        displayed = false;

        model.addObserver(this);
    }
    @Override
    public Parent render() {
        VBox vbox = new VBox();
        Label winLabel = new Label("You Win!");
        winLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");

        Label playAgain = new Label("You lit up the grid! Try another puzzle!");
        playAgain.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        Button playButton = new Button("Play Again");
        playButton.setStyle(
            "-fx-background-color: #007BFF;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 8px 16px;" +
            "-fx-cursor: hand;");
        playButton.setOnAction(e -> {
            controller.clickResetPuzzle();
        });

        int maxWidth = model.getActivePuzzle().getWidth() * 60;
        int maxHeight = model.getActivePuzzle().getHeight() * 60;

        vbox.getChildren().addAll(winLabel, playAgain, playButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        vbox.setMaxHeight(maxHeight);
        vbox.setMaxWidth(maxWidth);
        vbox.setSpacing(25);
        StackPane winMessage = new StackPane(vbox);
        winMessage.setAlignment(Pos.CENTER);

        currMessage = winMessage;

        return winMessage;
    }

    @Override
    public void update(Model model) {
        if (model.isSolved()) {
            if (!displayed) {
                stackPane.getChildren().add(this.render());
                displayed = true;
            }
        }

        else if (displayed) {
            stackPane.getChildren().remove(currMessage);
            displayed = false;
        }
    }
}