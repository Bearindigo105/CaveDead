import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 1400;
    public static final int HEIGHT = 750;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Sphere sphere = new Sphere(50);
        
        Group group = new Group();
        group.getChildren().add(sphere);
        
        Scene scene = new Scene(group, WIDTH, HEIGHT);

        sphere.translateXProperty().set(WIDTH / 2);
        sphere.translateYProperty().set(HEIGHT / 2);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
