import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        PhongMaterial wallMaterial = new PhongMaterial();
        wallMaterial.setDiffuseMap(new Image("file:/textures/cavewall.jpg"));
        Group mapGroup = new Group();

        Door box = new Door();
        box.setMaterial(wallMaterial);

        mapGroup.getChildren().addAll(box);

        // Room room = new Room(Room.Type.blank);

        mapGroup.getChildren().addAll();


        Group gameGroup = new Group();

        Scene gameScene = new Scene(gameGroup, WIDTH, HEIGHT);

        
        Player player = new Player(0, 0, -500, gameScene);

        
        gameGroup.getChildren().addAll(player, mapGroup);

        gameScene.setFill(Color.ALICEBLUE);

        gameScene.setCamera(player.playerCamera);

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
