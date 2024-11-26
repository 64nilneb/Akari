package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class SolvedMessage implements FXComponent, ModelObserver {
    private ControllerImpl controller;
    private Model model;
    public SolvedMessage(ControllerImpl controller) {
        this.controller = controller;
        this.model = controller.getModel();

        model.addObserver(this);
    }
    @Override
    public Parent render() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Congratulations");
        alert.setHeaderText("You Win!");
        alert.setContentText("Would you like to play again? Try a different puzzle!");

        ButtonType playAgainButton = new ButtonType("Play Again");

        alert.getButtonTypes().setAll(playAgainButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == playAgainButton) {
                System.out.println("Play Again button clicked!");
            }
        });

        return null;
    }

    @Override
    public void update(Model model) {
        if (model.isSolved()) {
            render();
        }
    }
}