import javafx.scene.robot.Robot;

import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Player extends Group {

    public Camera playerCamera;
    private Box hitbox;
    private double forwardAcceleration, verticalAcceleration, sidewardsAcceleration;
    public Rotate rotateY;
    public Rotate rotateX;
    public Scene scene;

    public Player(double x, double y, double z, Scene s) {

        Robot robot = new Robot();
        scene = s;
        hitbox = new Box(50, 80, 50);
        playerCamera = new PerspectiveCamera(true);
        forwardAcceleration = 0;
        verticalAcceleration = 0;
        sidewardsAcceleration = 0;
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateX = new Rotate(0, Rotate.X_AXIS);
        playerCamera.setNearClip(0.1);
        playerCamera.setFarClip(10000);
        this.getChildren().addAll(hitbox, playerCamera);
        for (Node childNode : this.getChildren()) {
            childNode.translateXProperty().bind(translateXProperty());
            childNode.translateYProperty().bind(translateYProperty());
            childNode.translateZProperty().bind(translateZProperty());
        }
        rotateY.pivotXProperty().bind(translateXProperty());
        rotateY.pivotYProperty().bind(translateYProperty());
        rotateY.pivotZProperty().bind(translateZProperty());
        rotateX.pivotXProperty().bind(translateXProperty());
        rotateX.pivotYProperty().bind(translateYProperty());
        rotateX.pivotZProperty().bind(translateZProperty());

        getTransforms().addAll(rotateY, rotateX);
        setPosition(x, y, z);


        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY){
                rotateY.setAngle(rotateY.getAngle() + (event.getSceneX() - scene.getWidth() / 2) / 100);
                rotateX.setAngle(rotateX.getAngle() + (event.getSceneY() - scene.getHeight() / 2) / 100);
            }
        });
    }

    public Bounds getBounds() {
        return hitbox.getBoundsInLocal();
    }

    public void setForwardAcceleration(double x) {
        forwardAcceleration = x;
    }

    public double getForwardAcceleration() {
        return forwardAcceleration;
    }

    public void setVerticalAcceleration(double yAcceleration) {
        this.verticalAcceleration = yAcceleration;
    }

    public double getVerticalAcceleration() {
        return verticalAcceleration;
    }

    public void setSidewardsAcceleration(double zAcceleration) {
        this.sidewardsAcceleration = zAcceleration;
    }

    public double getSidewardsAcceleration() {
        return sidewardsAcceleration;
    }

    public void setPosition(double x, double y, double z) {
        translateXProperty().set(x);
        translateYProperty().set(y);
        translateZProperty().set(z);
    }

    public void update() {
        translateXProperty().set(getTranslateX() + forwardAcceleration);
        translateYProperty().set(getTranslateY() + verticalAcceleration);
        translateZProperty().set(getTranslateZ() + sidewardsAcceleration);
    }

}