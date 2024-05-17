import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle wall = RectangleBuilder.create()
            .x(WIDTH / 2)
            .y(WIDTH / 2)
            .width(200)
            .height(200)
            .fill(
                new ImagePattern(
                    new Image("file:../textures/cavewall.jpg", 0, 0, 1, 1, true)
                )
            )
            .build();
        
        Group group = new Group();
        group.getChildren().add(sphere);
        
        Scene scene = new Scene(group, WIDTH, HEIGHT);

        sphere.translateXProperty().set(WIDTH / 2);
        sphere.translateYProperty().set(HEIGHT / 2);

        primaryStage.setTitle("caveDEAD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
