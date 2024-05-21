import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.AWTException;
import java.awt.Robot;

public class App extends Application {

    public static final int WIDTH = 1400;
    public static final int HEIGHT = 800;

    public Camera camera;
    private double prevMouseX;
    private double prevMouseY;
    private boolean isMouseLocked;
    private boolean moveMouse;

    @Override
    public void start(Stage primaryStage) throws Exception {
        isMouseLocked = false;
        PhongMaterial wallMaterial = new PhongMaterial();
        wallMaterial.setDiffuseMap(new Image("file:textures/cavewall.jpg"));

        Group mapGroup = new Group();
        
        Box box = new Box(50, 80, 50);
        box.setMaterial(wallMaterial);

        mapGroup.getChildren().addAll(box);

        //Room room = new Room(Room.Type.blank);
        
        mapGroup.getChildren().addAll();
        
        Player player = new Player(0, 0, -500);

        Group gameGroup = new Group();
        gameGroup.getChildren().addAll(player, mapGroup);
        
        Scene gameScene = new Scene(gameGroup, WIDTH, HEIGHT);
        gameScene.setFill(Color.PURPLE);
        gameScene.setCamera(player.playerCamera);
        camera = player.playerCamera;

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
            case W:
                player.setZAcceleration(1);
                break;
            case S:
                player.setZAcceleration(-1);
                break;
            case D:
                player.setXAcceleration(1);
                break;
            case A:
                player.setXAcceleration(-1);
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
                player.setZAcceleration(0);
                break;
            case S:
                player.setZAcceleration(0);
                break;
            case D:
                player.setXAcceleration(0);
                break;
            case A:
                player.setXAcceleration(0);
                break;
            default:
                break;
            }
        });

        gameScene.setOnMouseMoved(event -> {
            player.getTransforms().add(new Rotate((event.getSceneX() - prevMouseX)/ 70, Rotate.Y_AXIS));
            prevMouseX = event.getSceneX();
            prevMouseY = event.getSceneY();
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
