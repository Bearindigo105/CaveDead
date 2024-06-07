package main.java;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Player extends Group {

    public Camera playerCamera;
    private Box hitbox;
    private double forwardAcceleration, verticalAcceleration, sidewardsAcceleration;
    public Rotate rotateY;
    public Rotate rotateX;
    public SubScene scene;
    private double speed;

    public Player(double x, double y, double z, SubScene s) {
        speed = 1;
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
            Platform.runLater(() -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    rotateX.setAngle(rotateX.getAngle() + (scene.getHeight() / 2 - event.getSceneY()) / 70);
                    rotateY.setAngle(rotateY.getAngle() + (event.getSceneX() - scene.getWidth() / 2) / 70);
                }
            });
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        }.start();
    }

    public Bounds getBounds() {
        return hitbox.getBoundsInLocal();
    }

    public void setSidewardAcceleration(double x) {
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

    public void setForwardAcceleration(double zAcceleration) {
        this.sidewardsAcceleration = zAcceleration;
    }

    public double getSidewardsAcceleration() {
        return sidewardsAcceleration;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getSpeed(){
        return this.speed;
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