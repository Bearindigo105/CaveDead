import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Player extends Group {

    public Camera playerCamera;
    private Box hitbox;
    private double xAcceleration, yAcceleration, zAcceleration;
    public Rotate rotateY;

    public Player(double x, double y, double z) {
        hitbox = new Box(50, 80, 50);
        playerCamera = new PerspectiveCamera(true);
        xAcceleration = 0;
        yAcceleration = 0;
        zAcceleration = 0;
        rotateY = new Rotate(0, Rotate.Y_AXIS);
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
        getTransforms().add(rotateY);
        setPosition(x, y, z);
    }

    public Bounds getBounds() {
        return hitbox.getBoundsInLocal();
    }

    public void setxAcceleration(double x) {
        xAcceleration = x;
    }

    public double getxAcceleration() {
        return xAcceleration;
    }

    public void setyAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public double getyAcceleration() {
        return yAcceleration;
    }

    public void setzAcceleration(double zAcceleration) {
        this.zAcceleration = zAcceleration;
    }

    public double getzAcceleration() {
        return zAcceleration;
    }

    public void setPosition(double x, double y, double z) {
        translateXProperty().set(x);
        translateYProperty().set(y);
        translateZProperty().set(z);
    }

    public void update() {
        translateXProperty().set(getTranslateX() + xAcceleration);
        translateYProperty().set(getTranslateY() + yAcceleration);
        translateZProperty().set(getTranslateZ() + zAcceleration);
    }

}
