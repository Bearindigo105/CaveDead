import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 1400;
    public static final int HEIGHT = 800;

    public Camera camera;
    public double prevMouseX;
    public double prevMouseY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Rectangle wall = new Rectangle(200, 200, new ImagePattern(new Image("file:textures/cavewall.jpg")));

        PhongMaterial wallMaterial = new PhongMaterial();
        wallMaterial.setDiffuseMap(new Image("file:textures/cavewall.jpg"));

        Box box = new Box(100, 20, 50);
        box.setMaterial(wallMaterial);
        Group gameGroup = new Group();
        gameGroup.getChildren().addAll(box);
        
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(100000);
        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-500);
        Scene gameScene = new Scene(gameGroup, WIDTH, HEIGHT);
        gameScene.setFill(Color.PURPLE);
        gameScene.setCamera(camera);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    camera.translateZProperty().set(camera.getTranslateZ() + 10);
                    break;
                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 10);
                    break;
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() - 10);
                    break;
                case D:
                    camera.translateXProperty().set(camera.getTranslateX() + 10);
                    break;
            }
        });

        gameScene.setOnMouseMoved(event -> {
            camera.getTransforms().add(new Rotate((prevMouseY - event.getSceneY())/ 70, Rotate.X_AXIS));
            camera.getTransforms().add(new Rotate((event.getSceneX() - prevMouseX)/ 70, Rotate.Y_AXIS));
            prevMouseX = event.getSceneX();
            prevMouseY = event.getSceneY();
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
