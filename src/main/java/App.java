package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        PhongMaterial doorMaterial = new PhongMaterial();
        doorMaterial.setDiffuseMap(new Image("file:src/main/resources/images/metaldoor.jpg"));
        Group mapGroup = new Group();

        //Box box = new Door(false);

        Room room = new Room(Room.Type.corridor);

        mapGroup.getChildren().addAll(room);
        
        Group gameGroup = new Group();

        SubScene gameSubScene = new SubScene(gameGroup, WIDTH, HEIGHT);

        Player player = new Player(0, 0, -500, gameSubScene);

        HUD hud = new HUD();
        gameGroup.getChildren().addAll(player, mapGroup);

        gameSubScene.setFill(Color.ALICEBLUE);

        gameSubScene.setCamera(player.playerCamera);

        BorderPane gamePane = new BorderPane();

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                switch (event.getCode()) {
                    case W:
                        player.setForwardAcceleration(player.getSpeed());
                        break;
                    case S:
                        player.setForwardAcceleration(-1 * player.getSpeed());
                        break;
                    case D:
                        player.setSidewardAcceleration(player.getSpeed());
                        break;
                    case A:
                        player.setSidewardAcceleration(-1 * player.getSpeed());
                        break;
                    case SHIFT:
                        player.setSpeed(3);
                        break;
                    default:
                        break;
                }
            });
        });

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            Platform.runLater(() -> {
                switch (event.getCode()) {
                    case W:
                        player.setForwardAcceleration(0);
                        break;
                    case S:
                        player.setForwardAcceleration(0);
                        break;
                    case D:
                        player.setSidewardAcceleration(0);
                        break;
                    case A:
                        player.setSidewardAcceleration(0);
                        break;
                    case SHIFT:
                        player.setSpeed(1);
                        break;
                    default:
                        break;
                }
            });
        });

        Group titleGroup = new Group();
        Scene titleScene = new Scene(titleGroup, WIDTH, HEIGHT);

        primaryStage.setTitle("caveDEAD");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
