package main.java;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javax.swing.Timer;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     * @apiNote the texture of the walls in the maze
     */
    public final static PhongMaterial wallMaterial = new PhongMaterial();

    /**
     * @apiNote the character
     */
    public static Player player;

    /**
     * @author Subhash, Pranav
     * @apiNote this method starts the JavaFX application. It sets up the
     * game, initializes the player and maze, configures the HUD and input handlers, and manages
     * the transition between the title screen and the game scene.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        wallMaterial.setDiffuseMap(new Image("file:src/main/resources/textures/cavewall.jpg"));

        Group gameGroup = new Group();

        MazeGenerator maze = new MazeGenerator(20, 20);

        SubScene gameSubScene = new SubScene(gameGroup, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        Group mapGroup = new Group();
        mapGroup.getChildren().addAll(maze.getWalls());

        player = new Player(0, -10, 0, 0.5, 1, gameSubScene, maze.getWalls());

        HUD hud = new HUD();
        gameGroup.getChildren().addAll(mapGroup, player);

        gameSubScene.setFill(Color.PURPLE);

        gameSubScene.setCamera(player.getCamera());

        BorderPane gamePane = new BorderPane();

        gamePane.setTop(hud);
        gamePane.setCenter(gameSubScene);

        Scene gameScene = new Scene(gamePane);

        Timer sprintingTimer = new Timer(1, sprintlistener -> {
            if (hud.getStamina() > 0) {
                hud.setStamina(hud.getStamina() - 0.022);
                player.setIsSprinting(true);
            } else if (hud.getStamina() <= 0) {
                hud.setStamina(0);
                player.setIsSprinting(false);
            }
        });

        Timer sprintRegenTimer = new Timer(1, sprintRegenlistener -> {
            if (hud.getStamina() < 100) {
                hud.setStamina(hud.getStamina() + 0.01);
            } else {
                hud.setStamina(100);
            }
        });

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            Platform.runLater(() -> {
                switch (event.getCode()) {
                    case W:
                        player.setIsMoving(true);
                        player.setForwardsAcceleration(1);
                        break;
                    case S:
                        player.setIsMoving(true);
                        player.setForwardsAcceleration(-1);
                        break;
                    case D:
                        player.setIsMoving(true);
                        player.setSidewardsAcceleration(1);
                        break;
                    case A:
                        player.setIsMoving(true);
                        player.setSidewardsAcceleration(-1);
                        break;
                    case SHIFT:
                        sprintRegenTimer.stop();
                        sprintingTimer.start();
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
                        player.setIsMoving(false);
                        player.setForwardsAcceleration(0);
                        break;
                    case S:
                        player.setIsMoving(false);
                        player.setForwardsAcceleration(0);
                        break;
                    case D:
                        player.setIsMoving(false);
                        player.setSidewardsAcceleration(0);
                        break;
                    case A:
                        player.setIsMoving(false);
                        player.setSidewardsAcceleration(0);
                        break;
                    case SHIFT:
                        sprintingTimer.stop();
                        sprintRegenTimer.start();
                        player.setIsSprinting(false);
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
        title.relocate((WIDTH / 2) - 167, (HEIGHT / 2) - 120);

        Image playButtonIdle = new Image("file:src/main/resources/textures/playbutton.png");
        Image playButtonHover = new Image("file:src/main/resources/textures/playbutton1.png");
        ImageView playButtonImageView = new ImageView(playButtonIdle);
        Button playButton = new Button("", playButtonImageView);
        playButton.setPadding(Insets.EMPTY);
        playButton.relocate(WIDTH / 2 - 113 / 2.0, HEIGHT / 2 + 55);
        playButton.setOnMouseEntered(event -> {
            Platform.runLater(() -> {
                ((ImageView) playButton.getGraphic()).setImage(playButtonHover);
            });
        });
        playButton.setOnMouseExited(event -> {
            Platform.runLater(() -> {
                ((ImageView) playButton.getGraphic()).setImage(playButtonIdle);
            });
        });

        titlePane.getChildren().addAll(title);
        titlePane.getChildren().addAll(playButton);

        primaryStage.setTitle("caveDEAD");
        primaryStage.setScene(titleScene);
        primaryStage.show();

        playButton.setOnMousePressed(event -> {
            Platform.runLater(() -> {
                primaryStage.setScene(gameScene);
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        hud.setHealth(hud.getHealth() - 0.002);
                    }
                }.start();
                player.updateTimer.start();
            });
        });

    }

    /**
     * @author Subhash
     * @apiNote a method to play a sound
     */
    private void playSound() {
        try {
            Media sound = new Media(new File("src/main/resources/sounds/.mp3").toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();
        } catch (Exception e) {
        }
    }


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
