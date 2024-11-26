package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PuzzleView implements FXComponent, ModelObserver {

    private Button[][] buttonGrid;
    private int rows;
    private int cols;
    private Model model;
    private ControllerImpl controller;
    private int currInd;
    private GridPane grid;

    public PuzzleView(ControllerImpl controller) {
        this.controller = controller;
        this.model = controller.getModel();
        this.rows = model.getActivePuzzle().getHeight();
        this.cols = model.getActivePuzzle().getWidth();
        this.buttonGrid = new Button[rows][cols];

        this.currInd = model.getActivePuzzleIndex();

        model.addObserver(this);

    }

    public Parent render() {
        GridPane grid = new GridPane();
        this.grid = grid;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button cellButton = new Button();
                cellButton.setMinSize(60, 60);

                setButtonStyle(cellButton, row, col);

                buttonGrid[row][col] = cellButton;

                int currRow = row;
                int currCol = col;
                cellButton.setOnAction(event -> {
                    controller.clickCell(currRow, currCol);
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
            if (clueNumber == 0) {
                button.setStyle("-fx-background-color: #18ab3d;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1;" +
                                "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-padding: 0;" +
                                "-fx-alignment: center;" +
                                "-fx-font-size: 32px;" +
                                "-fx-text-fill: white;");
            }
            else {
                button.setStyle("-fx-background-color: black;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1;" +
                                "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-padding: 0;" +
                                "-fx-alignment: center;" +
                                "-fx-font-size: 32px;" +
                                "-fx-text-fill: white;");
            }
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
                if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
                    if (model.isLit(r, c)) {
                        button.setStyle("-fx-background-color: yellow;" +
                            "-fx-border-color: black;" +
                            "-fx-border-width: 1;");
                    }
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

    private void lightbulb() {
        int maxHeight = model.getActivePuzzle().getHeight();
        int maxWidth = model.getActivePuzzle().getWidth();

        for (int r = 0; r < maxHeight; r++) {
            for (int c = 0; c < maxWidth; c++) {
                Button button = buttonGrid[r][c];
                if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
                    if (model.isLamp(r, c)) {
                        button.setText("\uD83D\uDCA1");
                    }
                    else {
                        button.setText("");
                    }

          if (button.getStyle().contains("-fx-background-color: red;")
              && (!model.isLamp(r, c) || !model.isLampIllegal(r, c))) {
                        button.setStyle("-fx-background-color: white;" +
                            "-fx-border-color: black;" +
                            "-fx-border-width: 1;" +
                            "-fx-focus-color: transparent;" +
                            "-fx-faint-focus-color: transparent;" +
                            "-fx-padding: 0;" +
                            "-fx-alignment: center;");
                    }
                }
            }
        }
    }

    private void clueSatisfied() {
        int maxHeight = model.getActivePuzzle().getHeight();
        int maxWidth = model.getActivePuzzle().getWidth();

        for (int r = 0; r < maxHeight; r++) {
            for (int c = 0; c < maxWidth; c++) {
                Button button = buttonGrid[r][c];
                if (model.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
                    if (model.isClueSatisfied(r, c)) {
                        button.setStyle("-fx-background-color: #18ab3d;" +
                            "-fx-border-color: black;" +
                            "-fx-border-width: 1;" +
                            "-fx-focus-color: transparent;" +
                            "-fx-faint-focus-color: transparent;" +
                            "-fx-padding: 0;" +
                            "-fx-alignment: center;" +
                            "-fx-font-size: 32px;" +
                            "-fx-text-fill: white;");
                    }
                    else {
                        button.setStyle("-fx-background-color: black;" +
                            "-fx-border-color: black;" +
                            "-fx-border-width: 1;" +
                            "-fx-focus-color: transparent;" +
                            "-fx-faint-focus-color: transparent;" +
                            "-fx-padding: 0;" +
                            "-fx-alignment: center;" +
                            "-fx-font-size: 32px;" +
                            "-fx-text-fill: white;");
                    }
                }
            }
        }
    }

    private void updateBoard(GridPane grid) {
        grid.getChildren().clear();

        int maxHeight = model.getActivePuzzle().getHeight();
        int maxWidth = model.getActivePuzzle().getWidth();

        for (int row = 0; row < maxHeight; row++) {
            for (int col = 0; col < maxWidth; col++) {
                Button cellButton = new Button();
                cellButton.setMinSize(60, 60);

                setButtonStyle(cellButton, row, col);

                buttonGrid[row][col] = cellButton;

                int currRow = row;
                int currCol = col;
                cellButton.setOnAction(event -> {
                    controller.clickCell(currRow, currCol);
                });

                grid.add(cellButton, col, row);
            }
        }
    }

    @Override
    public void update(Model model) {
        if (currInd != model.getActivePuzzleIndex()) {
            this.rows = model.getActivePuzzle().getHeight();
            this.cols = model.getActivePuzzle().getWidth();
            this.buttonGrid = new Button[rows][cols];
            this.model = model;
            this.currInd = model.getActivePuzzleIndex();
            updateBoard(grid);
        }
        lightUp();
        lightDown();
        lightbulb();
        checkIllegalLamps();
        clueSatisfied();
    }
}