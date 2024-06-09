package main.java;

import java.util.List;

import javax.swing.ImageIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static PhongMaterial doorMaterial = new PhongMaterial();
    public static PhongMaterial wallMaterial = new PhongMaterial();

    @Override
    public void start(Stage primaryStage) throws Exception {
        doorMaterial.setDiffuseMap(new Image("file:src/main/resources/images/metaldoor.jpg"));
        wallMaterial.setDiffuseMap(new Image("file:src/main/resources/images/cavewall.jpg"));

        
        Group gameGroup = new Group();

        Maze maze = new Maze(20, 20);
        Box floor = new Box(20000, 20000, 0.1);
        floor.setMaterial(wallMaterial);

        SubScene gameSubScene = new SubScene(gameGroup, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        Group mapGroup = new Group();
        mapGroup.getChildren().addAll(floor);

        Player player = new Player(0, 10, -500, gameSubScene);

        HUD hud = new HUD();
        gameGroup.getChildren().addAll(player, mapGroup, maze);

        gameSubScene.setFill(Color.ALICEBLUE);

        gameSubScene.setCamera(player.playerCamera);

        BorderPane gamePane = new BorderPane();

        gamePane.setTop(hud);
        gamePane.setCenter(gameSubScene);

        Scene gameScene = new Scene(gamePane);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                switch (event.getCode()) {
                    case W:
                        player.setForwardAcceleration(1);
                        break;
                    case S:
                        player.setForwardAcceleration(-1);
                        break;
                    case D:
                        player.setSidewardAcceleration(1);
                        break;
                    case A:
                        player.setSidewardAcceleration(-1);
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

        Pane titlePane = new Pane();
        Scene titleScene = new Scene(titlePane, WIDTH, HEIGHT);

        titlePane.setStyle("-fx-background-color: black;");
        ImageView title = new ImageView();
        title.setImage(new Image("file:src/main/resources/textures/game.png"));
        title.relocate((WIDTH / 2) - 175, (HEIGHT / 2) - 100);
        
        ImageView playButtonImageView = new ImageView(new Image("file:src/main/resources/textures/playbutton.png"));
        Button playButton = new Button("", playButtonImageView);
        playButton.setPadding(Insets.EMPTY);
        playButton.relocate(WIDTH /2 - 50, HEIGHT / 2 + 50);

        titlePane.getChildren().addAll(title); 
        titlePane.getChildren().addAll(playButton); 


        primaryStage.setTitle("caveDEAD");
        primaryStage.setScene(titleScene);
        primaryStage.show();

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                switch (event.getCode()) {
                    
                }
            });
        });
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
