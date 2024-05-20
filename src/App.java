import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class App extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle wall = new Rectangle(200, 200, new ImagePattern(new Image("file:textures/cavewall.jpg")));
        
        Group group = new Group();
        group.getChildren().add(wall);
        
        System.out.println(System.getProperty("user.dir"));

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
