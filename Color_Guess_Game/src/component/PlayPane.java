package component;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import logic.GameLogic;

public class PlayPane extends VBox {
    private final GameLogic gameLogic;
    private int MaxRow = 9;
    private final Runnable onWin;
    private final Consumer<String[]> onLose;
    private final GridPane colorGuessGrid;
    private final GridPane feedbackGrid;
    private final HBox availableColorsBox;
    private final Label errorMessage;
    private int currentRow = 0;
    private String[] guessedColors = new String[4];
    private SoundPlayer soundPlayer = new SoundPlayer();

    public PlayPane(GameLogic gameLogic, Runnable onWin, Consumer<String[]> onLose) {
        this.gameLogic = gameLogic;
        this.onWin = onWin;
        this.onLose = onLose;
        soundPlayer = new SoundPlayer();

        setSpacing(20);
        setPadding(new Insets(20));
        setStyle("-fx-alignment: center; -fx-background-color: #ADD8E6");
        
        
        colorGuessGrid = new GridPane();
        colorGuessGrid.setHgap(10);
        colorGuessGrid.setVgap(10);
        for (int row = 0; row < MaxRow; row++) {
            for (int col = 0; col < 4; col++) {
                Circle cell = createColorCell();
                colorGuessGrid.add(cell, col, row);
            }
        }

        feedbackGrid = new GridPane();
        feedbackGrid.setHgap(5);
        feedbackGrid.setVgap(40);
        feedbackGrid.setMinWidth(120);
        feedbackGrid.setPrefWidth(120);
        feedbackGrid.setAlignment(Pos.CENTER);

        for (int row = 0; row < MaxRow; row++) {
            for (int col = 0; col < 4; col++) {
                Circle circle = createFeedbackCircle(Color.TRANSPARENT); // เริ่มต้นเป็นโปร่งใส
                feedbackGrid.add(circle, col, row);
            }
        }
        
        errorMessage = new Label("");
        errorMessage.setTextFill(javafx.scene.paint.Color.RED);
        errorMessage.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red; "
                + "-fx-effect: dropshadow(gaussian, black, 5, 0, 0, 0);");

        availableColorsBox = new HBox(10);
        availableColorsBox.setStyle("-fx-alignment: center;");
        String[] availableColors = {"RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PURPLE"};
        for (String color : availableColors) {
        	Circle colorBox = createColorOption(color);
            availableColorsBox.getChildren().add(colorBox);
        }

        HBox buttonBox = new HBox(20);
        buttonBox.setStyle("-fx-alignment: center;");

        Button checkButton = new Button("Check");
        checkButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        checkButton.setOnAction(e -> handleCheck());

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        resetButton.setOnAction(e -> handleReset());

        buttonBox.getChildren().addAll(checkButton, resetButton);
        
        StackPane colorGuessPane = new StackPane(colorGuessGrid);
        colorGuessPane.setMinWidth(220);

        HBox mainGameBox = new HBox(50);
        mainGameBox.setAlignment(Pos.CENTER);
        mainGameBox.getChildren().addAll(colorGuessGrid, feedbackGrid);

        getChildren().addAll(mainGameBox, availableColorsBox, buttonBox, errorMessage);
    }

    private Circle createColorCell() {
    	Circle cell = new Circle(25, Color.LIGHTGRAY);
        cell.setStroke(Color.BLACK);
        return cell;
    }
    
    private Circle createFeedbackCircle(Color color) {
        Circle circle = new Circle(10);
        circle.setFill(color);
        return circle;
    }

    private Circle createColorOption(String color) {
    	Circle colorBox = new Circle(25, Color.valueOf(color));
        colorBox.setStroke(Color.BLACK);
        colorBox.setOnMouseClicked(e -> handleColorOptionClick(color));
        return colorBox;
    }

    private void handleColorOptionClick(String color) {
    	soundPlayer.playChoose();
        for (int col = 0; col < 4; col++) {
        	Circle cell = (Circle) getNodeFromGridPane(colorGuessGrid, col, currentRow);
            if (cell.getFill() == Color.LIGHTGRAY) {
                cell.setFill(Color.valueOf(color));
                guessedColors[col] = color;
                errorMessage.setText("");
                return;
            }
        }
        soundPlayer.playBadClick();
        errorMessage.setText("This row is full! You have to check first.");
    }

    private void handleCheck() {
        for (int col = 0; col < 4; col++) {
        	Circle cell = (Circle) getNodeFromGridPane(colorGuessGrid, col, currentRow);
        	if(cell.getFill() == Color.LIGHTGRAY) {
        		errorMessage.setText("Guessed colors must have exactly 4 elements.");
        		soundPlayer.playBadClick();
        		return;
        	}
        }
        
        soundPlayer.playGoodClick();
        int[] feedback = gameLogic.checkColors(guessedColors);
        if (feedback == null) {
        	for (int col = 0; col < 4; col++) {
                Circle circle = (Circle) getNodeFromGridPane(feedbackGrid, col, currentRow);
                circle.setFill(Color.TRANSPARENT);
            }
        } else {
        	int correctPositionAndColor = feedback[0];
            int correctColorOnly = feedback[1];
            
            if(correctPositionAndColor == 0 && correctColorOnly == 0) {
            	for(int i = 0; i < 4; i++) {
            		Circle circle = (Circle) getNodeFromGridPane(feedbackGrid, i, currentRow);
                    circle.setFill(Color.RED);
                    
            	}
            }

            for (int i = 0; i < correctPositionAndColor; i++) {
                Circle circle = (Circle) getNodeFromGridPane(feedbackGrid, i, currentRow);
                circle.setFill(Color.BLACK);
                
            }
            for (int i = 0; i < correctColorOnly; i++) {
                Circle circle = (Circle) getNodeFromGridPane(feedbackGrid, correctPositionAndColor + i, currentRow);
                circle.setFill(Color.WHITE);
                
            }

            currentRow++;
            
            if (correctPositionAndColor == 4) {
                onWin.run();
                return;
            }

            
            if (currentRow == MaxRow) {
            	onLose.accept(gameLogic.getSecretColors());
            }
        }
    }

    private void handleReset() {
    	soundPlayer.playReset();
        for (int col = 0; col < 4; col++) {
        	Circle cell = (Circle) getNodeFromGridPane(colorGuessGrid, col, currentRow);
            cell.setFill(Color.LIGHTGRAY);
            guessedColors = new String[4];
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public String[] getGuessedColors() {
		return guessedColors;
	}

	public void setGuessedColors(String[] guessedColors) {
		this.guessedColors = guessedColors;
	}

	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}

	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	public GameLogic getGameLogic() {
		return gameLogic;
	}

	public int getMaxRow() {
		return MaxRow;
	}

	public void setMaxRow(int maxRow) {
		MaxRow = maxRow;
	}

	public Runnable getOnWin() {
		return onWin;
	}

	public Consumer<String[]> getOnLose() {
		return onLose;
	}

	public GridPane getColorGuessGrid() {
		return colorGuessGrid;
	}

	public GridPane getFeedbackGrid() {
		return feedbackGrid;
	}

	public HBox getAvailableColorsBox() {
		return availableColorsBox;
	}

	public Label getErrorMessage() {
		return errorMessage;
	}
    

}
