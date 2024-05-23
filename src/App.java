import javax.swing.Timer;

import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public Camera camera;
    private double prevMouseX;
    private boolean isMouseLocked;
    private boolean isMouseMovedByRobot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        isMouseLocked = false;
        PhongMaterial wallMaterial = new PhongMaterial();
        wallMaterial.setDiffuseMap(new Image("file:textures/cavewall.jpg"));

        Group mapGroup = new Group();

        Box box = new Box(50, 80, 50);
        box.setMaterial(wallMaterial);

        mapGroup.getChildren().addAll(box);

        // Room room = new Room(Room.Type.blank);

        mapGroup.getChildren().addAll();

        Player player = new Player(0, 0, -500);

        Group gameGroup = new Group();
        gameGroup.getChildren().addAll(player, mapGroup);

        Scene gameScene = new Scene(gameGroup, WIDTH, HEIGHT);
        gameScene.setFill(Color.PURPLE);

        gameScene.setCamera(player.playerCamera);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    player.setzAcceleration(1);
                    break;
                case S:
                    player.setzAcceleration(-1);
                    break;
                case D:
                    player.setxAcceleration(1);
                    break;
                case A:
                    player.setxAcceleration(-1);
                    break;
                case ESCAPE:
                    isMouseLocked = false;
                    break;
                default:
                    break;
            }
        });

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case W:
                    player.setzAcceleration(0);
                    break;
                case S:
                    player.setzAcceleration(0);
                    break;
                case D:
                    player.setxAcceleration(0);
                    break;
                case A:
                    player.setxAcceleration(0);
                    break;
                default:
                    break;
            }
        });

        gameScene.setOnMouseMoved(event -> {
            Platform.runLater(() -> {
                if (isMouseLocked && isMouseMovedByRobot) {
                    player.rotateY.setAngle(player.rotateY.getAngle() + (event.getSceneX() - prevMouseX) / 70);
                    prevMouseX = event.getSceneX();
                    try {
                        Robot robot = new Robot();
                        robot.mouseMove((int) (primaryStage.getX() + WIDTH / 2),
                                (int) (primaryStage.getY() + HEIGHT / 2));
                        return;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                } else if (isMouseMovedByRobot) {
                    isMouseMovedByRobot = false;
                }
                isMouseMovedByRobot = true;
            });
        });

        gameScene.setOnMouseClicked(event -> {
            isMouseLocked = true;
        });

        ActionListener update = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.update();
            }
        };

        Timer timer = new Timer(17, update);
        timer.start();

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
