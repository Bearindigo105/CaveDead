import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.scene.shape.RectangleBuilder;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle wall = new Rectangle();
        
        Group group = new Group();
        group.getChildren().add(wall);
        
        Scene scene = new Scene(group, WIDTH, HEIGHT);

        wall.translateXProperty().set(WIDTH / 2);
        wall.translateYProperty().set(HEIGHT / 2);

        primaryStage.setTitle("caveDEAD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
