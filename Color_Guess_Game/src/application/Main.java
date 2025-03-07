package application;

import component.PlayPane;
import component.SoundPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.GameLogic;

public class Main extends Application {
    private Stage primaryStage;
    private GameLogic gameLogic;
    private SoundPlayer soundPlayer = new SoundPlayer();
    private Image img = new Image(ClassLoader.getSystemResource("Background.jpg").toString());
    private DropShadow ds = new DropShadow();
    private BackgroundImage bg;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gameLogic = new GameLogic();
        ds.setColor(Color.BLACK); 
        ds.setRadius(3);
        ds.setSpread(1);
        bg = new BackgroundImage(
        	    img,
        	    BackgroundRepeat.NO_REPEAT,
        	    BackgroundRepeat.NO_REPEAT,
        	    BackgroundPosition.CENTER,
        	    new BackgroundSize(1, 1, true, true, false, false)
        	);

        showStartScene();
    }

    private void showStartScene() {
    	VBox startPane = new VBox(20);
    	soundPlayer.stopMusic();
    	soundPlayer.playMusic1();
    	startPane.setStyle("-fx-alignment: center; -fx-padding: 20;");

    	Text scenetitle = new Text("Color Guess Game");
    	scenetitle.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
    	scenetitle.setFill(Color.YELLOW);
    	scenetitle.setEffect(ds);


    	Button startButton = new Button("Start");
    	startButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
    	startButton.setOnAction(e -> showGameScene());

    	Button howToPlayButton = new Button("How to Play");
    	howToPlayButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
    	howToPlayButton.setOnAction(e -> showHowToPlayScene());

    	startPane.getChildren().addAll(scenetitle, startButton, howToPlayButton);
    	startPane.setBackground(new Background(bg));

    	Scene startScene = new Scene(startPane, 800, 800);
    	
    	startScene.getStylesheets().add(ClassLoader.getSystemResource("style.css").toString());

    	primaryStage.setTitle("Color Guess Game");
    	primaryStage.setScene(startScene);
    	primaryStage.show();


    }

    private void showHowToPlayScene() {
        VBox howToPlayPane = new VBox(20);
        
        howToPlayPane.setStyle("-fx-alignment: center; -fx-padding: 20;");

        ImageView howToPlayImage = new ImageView(new Image(ClassLoader.getSystemResource("howtoplay.png").toString()));
        howToPlayImage.setFitWidth(500);
        howToPlayImage.setFitHeight(600);
        howToPlayImage.setPreserveRatio(true);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        backButton.setOnAction(e -> showStartScene());

        howToPlayPane.getChildren().addAll(howToPlayImage, backButton);
        howToPlayPane.setBackground(new Background(bg));
        howToPlayPane.getStylesheets().add(ClassLoader.getSystemResource("style.css").toString());
        Scene howToPlayScene = new Scene(howToPlayPane, 800, 800);
        primaryStage.setScene(howToPlayScene);
    }

    private void showGameScene() {
    	gameLogic = new GameLogic();
    	soundPlayer.stopMusic();
    	soundPlayer = new SoundPlayer();
        soundPlayer.playMusic2();
        
    	
    	PlayPane playPane = new PlayPane(gameLogic, this::showWinScene, secretColors -> showLoseScene(secretColors));
    	playPane.setStyle("-fx-alignment: center; -fx-padding: 20;");
    	playPane.setPrefSize(500, 720);
    	playPane.setMaxSize(500, 720);

    	BackgroundImage bg = new BackgroundImage(
    	    img,
    	    BackgroundRepeat.NO_REPEAT,
    	    BackgroundRepeat.NO_REPEAT,
    	    BackgroundPosition.CENTER,
    	    new BackgroundSize(1, 1, true, true, false, false)
    	);
    	BorderPane pane = new BorderPane();
    	pane.setBackground(new Background(bg));
    	pane.getStylesheets().add(ClassLoader.getSystemResource("style.css").toString());
    	
    	playPane.setStyle("-fx-background-color: #8B4513; -fx-border-color: black; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-width: 5px;");
    	
    	pane.setCenter(playPane);
    	
        Scene gameScene = new Scene(pane, 800, 800);
        primaryStage.setScene(gameScene);
        
    }

    private void showWinScene() {
    	soundPlayer.stopMusic();
    	soundPlayer.playWin();
        VBox winPane = new VBox(20);
        winPane.setStyle("-fx-alignment: center; -fx-padding: 20;");

    	BackgroundImage bg = new BackgroundImage(
    	    img,
    	    BackgroundRepeat.NO_REPEAT,
    	    BackgroundRepeat.NO_REPEAT,
    	    BackgroundPosition.CENTER,
    	    new BackgroundSize(1, 1, true, true, false, false)
    	);
        
        Text scenetitle = new Text("You Win!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        scenetitle.setFill(Color.CYAN);
    	scenetitle.setEffect(ds);
        
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        playAgainButton.setOnAction(e -> showGameScene());

        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        quitButton.setOnAction(e -> primaryStage.close());

        winPane.getChildren().addAll(scenetitle, playAgainButton, quitButton);
        winPane.setBackground(new Background(bg));
        winPane.getStylesheets().add(ClassLoader.getSystemResource("style.css").toString());
        Scene winScene = new Scene(winPane, 800, 800);
        primaryStage.setScene(winScene);
    }

    private void showLoseScene(String[] secretColors) {
        VBox losePane = new VBox(20);
        soundPlayer.stopMusic();
        soundPlayer.playLose();
        losePane.setStyle("-fx-alignment: center; -fx-padding: 20;");

    	BackgroundImage bg = new BackgroundImage(
    	    img,
    	    BackgroundRepeat.NO_REPEAT,
    	    BackgroundRepeat.NO_REPEAT,
    	    BackgroundPosition.CENTER,
    	    new BackgroundSize(1, 1, true, true, false, false)
    	);

        StringBuilder secretColorString = new StringBuilder("Secret color is: ");
        for (String color : secretColors) {
            secretColorString.append(color).append(" ");
        }

        Text scenetitle = new Text("You Lose!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
    	scenetitle.setFill(Color.RED);
    	scenetitle.setEffect(ds);
        
        Label secretLabel = new Label(secretColorString.toString());
        secretLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        secretLabel.setStyle("-fx-text-fill: red; -fx-effect: dropshadow(gaussian, black, 5, 0, 0, 0);");

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        playAgainButton.setOnAction(e -> showGameScene());

        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-font-size: 15px; -fx-padding: 10px 20px;");
        quitButton.setOnAction(e -> primaryStage.close());

        losePane.getChildren().addAll(scenetitle, secretLabel, playAgainButton, quitButton);
        losePane.setBackground(new Background(bg));
        losePane.getStylesheets().add(ClassLoader.getSystemResource("style.css").toString());
        Scene loseScene = new Scene(losePane, 800, 800);
        primaryStage.setScene(loseScene);
    }
    
}
