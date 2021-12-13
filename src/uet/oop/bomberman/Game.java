package uet.oop.bomberman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

import java.util.Objects;

public class Game extends Application {
    public static void main(String[] args) {
        Application.launch(Game.class);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bomberman 2021 ver1.0");
        Image icon = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("/icon/icon.png")));
        stage.getIcons().add(icon);

        // Menu Scene
        Image menuImg = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("/textures/menu.png")));
        ImageView menuImgView = new ImageView(menuImg);
        AnchorPane menuPane = new AnchorPane(menuImgView);

        Button startButton = new Button("Start");
        Button insButton = new Button("Instruction");
        Button exitButton = new Button("Exit");

        menuPane.getChildren().addAll(startButton, insButton, exitButton);
        Scene menuScene = new Scene(menuPane);
        menuScene.getStylesheets().add("/css/style.css");
        startButton.getStyleClass().add("button");
        startButton.setLayoutX(560);
        startButton.setLayoutY(300);

        insButton.setLayoutX(560);
        insButton.setLayoutY(380);

        exitButton.setLayoutX(560);
        exitButton.setLayoutY(460);

        //Instruction scene
        Image instructionImg = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("/textures/instruction.png")));
        ImageView instructionImgView = new ImageView(instructionImg);
        AnchorPane insPane = new AnchorPane(instructionImgView);
        Button backButton = new Button("Back");
        insPane.getChildren().add(backButton);
        Scene instructionScene = new Scene(insPane);
        instructionScene.getStylesheets().add("/css/style.css");
        backButton.getStyleClass().add("backButton");

        backButton.setLayoutX(24);
        backButton.setLayoutY(44);

        //Game over scene
        Image gameoverImg = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("/textures/gameover.png")));
        ImageView gameoverImgView = new ImageView(gameoverImg);
        AnchorPane gameoverPane = new AnchorPane(gameoverImgView);
        Scene gameoverScene = new Scene(gameoverPane);

        Map.initScene();
        startButton.setOnMouseClicked(value -> {
            stage.setScene(Map.getScene());
            stage.centerOnScreen();
        });

        backButton.setOnMouseClicked(event -> {
            stage.setScene(menuScene);
            stage.centerOnScreen();
        });

        insButton.setOnMouseClicked(value -> {
            stage.setScene(instructionScene);
            stage.centerOnScreen();
        });

        exitButton.setOnMouseClicked(event -> {
            stage.close();
        });

        stage.setScene(menuScene);
        Sound.stageThemeSound().playLoop(true);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }
}
