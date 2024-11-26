package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class PuzzleView implements FXComponent {

    private Button[][] buttonGrid;
    private int rows;
    private int cols;
    private Model model;
    private ControllerImpl controller;

    public PuzzleView(ControllerImpl controller) {
        this.controller = controller;
        this.model = controller.getModel();
        this.rows = model.getActivePuzzle().getHeight();
        this.cols = model.getActivePuzzle().getWidth();
        this.buttonGrid = new Button[rows][cols];
    }

    public Parent render() {
        GridPane grid = new GridPane();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button cellButton = new Button();
                cellButton.setMinSize(60, 60);

                setButtonStyle(cellButton, row, col);

                buttonGrid[row][col] = cellButton;

                int currRow = row;
                int currCol = col;
                cellButton.setOnAction(event -> {
                    if (model.getActivePuzzle().getCellType(currRow, currCol) == CellType.CORRIDOR) {
                        if (model.isLamp(currRow, currCol)) {
                            cellButton.setStyle("-fx-background-color: white;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1;");
                            cellButton.setText("");
                            controller.clickCell(currRow, currCol);
                            lightDown();
                            lightUp();
                        } else {
                            cellButton.setText("\uD83D\uDCA1");
                            cellButton.setStyle("-fx-font-size: 32px;");
                            controller.clickCell(currRow, currCol);
                            lightUp();
                        }
                    }

                    checkIllegalLamps();
                    checkIfSolved();
                });

                grid.add(cellButton, col, row);
            }
        }

        StackPane centeredPane = new StackPane();
        centeredPane.getChildren().add(grid);

        ScrollPane scrollPane = new ScrollPane(centeredPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private void setButtonStyle(Button button, int row, int col) {
        if (model.getActivePuzzle().getCellType(row, col) == CellType.WALL) {
            button.setStyle("-fx-background-color: black;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-padding: 0;" +
                "-fx-alignment: center;");
        } else if (model.getActivePuzzle().getCellType(row, col) == CellType.CLUE) {
            int clueNumber = model.getActivePuzzle().getClue(row, col);
            button.setText(String.valueOf(clueNumber));
            button.setStyle("-fx-background-color: black;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-padding: 0;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 32px;" +
                "-fx-text-fill: white;");
        } else {
            button.setStyle("-fx-background-color: white;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;" +
                "-fx-focus-color: transparent;" +
                "-fx-faint-focus-color: transparent;" +
                "-fx-padding: 0;" +
                "-fx-alignment: center;");
        }
    }

    private void lightUp() {
        int maxHeight = model.getActivePuzzle().getHeight();
        int maxWidth = model.getActivePuzzle().getWidth();

        for (int r = 0; r < maxHeight; r++) {
            for (int c = 0; c < maxWidth; c++) {
                Button button = buttonGrid[r][c];
                if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR &&
                    model.isLit(r, c)) {
                    button.setStyle("-fx-background-color: yellow;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;");
                }
            }
        }
    }

    private void lightDown() {
        int maxHeight = model.getActivePuzzle().getHeight();
        int maxWidth = model.getActivePuzzle().getWidth();

        for (int r = 0; r < maxHeight; r++) {
            for (int c = 0; c < maxWidth; c++) {
                Button button = buttonGrid[r][c];
                if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR &&
                    !model.isLit(r, c) &&
                    button.getStyle().contains("-fx-background-color: yellow;")) {
                    button.setStyle("-fx-background-color: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;");
                }
            }
        }
    }

    private void checkIllegalLamps() {
        int maxHeight = model.getActivePuzzle().getHeight();
        int maxWidth = model.getActivePuzzle().getWidth();

        for (int r = 0; r < maxHeight; r++) {
            for (int c = 0; c < maxWidth; c++) {
                Button button = buttonGrid[r][c];
                if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR &&
                    model.isLamp(r, c) &&
                    model.isLampIllegal(r, c)) {
                    button.setStyle("-fx-background-color: red;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;");
                }
            }
        }
    }

    private void checkIfSolved() {
        if (model.isSolved()) {

        }
    }
}