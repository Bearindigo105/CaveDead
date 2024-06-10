package main.java;

import java.io.File;
import java.util.List;

import javax.swing.Timer;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 * Represents the player in the game.
 */
public class Player extends Group {

    private Camera playerCamera;
    private Box hitbox;
    private double sidewardsAcceleration, forwardsAcceleration;
    private Rotate rotateY;
    private Rotate rotateX;
    private SubScene scene;
    private double walkingSpeed;
    private boolean isSprinting;
    private double sprintingSpeed;
    private double prevMouseX;
    private double prevMouseY;
    private boolean dragging = false;
    private boolean isMoving;
    private Timer sprintingFootstepTimer;
    private Timer walkingFootstepTimer;
    private List<Wall> mazeWalls;

    /**
     * Creates a new Player object.
     * 
     * @param x              Initial x-coordinate of the player.
     * @param y              Initial y-coordinate of the player.
     * @param z              Initial z-coordinate of the player.
     * @param walkingSpeed   The walking speed of the player.
     * @param sprintingSpeed The sprinting speed of the player.
     * @param scene          The SubScene that the player is in.
     * @param walls          The list of maze walls for collision detection.
     */
    public Player(double x, double y, double z, double walkingSpeed, double sprintingSpeed, SubScene scene,
            List<Wall> walls) {
        this.walkingSpeed = walkingSpeed;
        this.sprintingSpeed = sprintingSpeed;
        this.scene = scene;
        this.mazeWalls = walls;

        hitbox = new Box(20, 80, 20);
        playerCamera = new PerspectiveCamera(true);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateX = new Rotate(0, Rotate.X_AXIS);

        playerCamera.setNearClip(0.1);
        playerCamera.setFarClip(10000);

        this.getChildren().addAll(hitbox, playerCamera);

        playerCamera.translateXProperty().bind(translateXProperty());
        playerCamera.translateYProperty().bind(translateYProperty());
        playerCamera.translateZProperty().bind(translateZProperty());

        hitbox.translateXProperty().bindBidirectional(translateXProperty());
        hitbox.translateYProperty().bindBidirectional(translateYProperty());
        hitbox.translateZProperty().bindBidirectional(translateZProperty());

        rotateY.pivotXProperty().bind(translateXProperty());
        rotateY.pivotYProperty().bind(translateYProperty());
        rotateY.pivotZProperty().bind(translateZProperty());

        rotateX.pivotXProperty().bind(translateXProperty());
        rotateX.pivotYProperty().bind(translateYProperty());
        rotateX.pivotZProperty().bind(translateZProperty());

        getTransforms().add(rotateY);

        setPosition(x, y, z);

        setupMouseControls();

        setupFootstepTimers();
    }

    /**
     * Sets up mouse controls for camera rotation.
     */
    private void setupMouseControls() {
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                prevMouseX = event.getSceneX();
                prevMouseY = event.getSceneY();
                dragging = true;
            }
        });

        scene.setOnMouseDragged(event -> {
            if (dragging) {
                double deltaX = event.getSceneX() - prevMouseX;
                double deltaY = event.getSceneY() - prevMouseY;
                rotateX.setAngle(rotateX.getAngle() - deltaY / 30);
                rotateY.setAngle(rotateY.getAngle() + deltaX / 30);
                prevMouseX = event.getSceneX();
                prevMouseY = event.getSceneY();
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                dragging = false;
            }
        });
    }

    /**
     * Sets up footstep timers for playing sounds.
     */
    private void setupFootstepTimers() {
        sprintingFootstepTimer = new Timer(400, sprintListener -> playSound());
        walkingFootstepTimer = new Timer(800, walkingFootstepListener -> playSound());
    }

    /**
     * Starts the animation loop for player updates.
     */
    public final AnimationTimer updateTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };

    /**
     * Plays a footstep sound.
     */
    private void playSound() {
        try {
            Media sound = new Media(new File("src/main/resources/sounds/footstep.mp3").toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();
        } catch (Exception e) {
        }
    }

    /**
     * Sets the position of the player.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public void setPosition(double x, double y, double z) {
        translateXProperty().set(x);
        translateYProperty().set(y);
        translateZProperty().set(z);
    }

    /**
     * @author Pranav, Subhash
     * @apiNote updates the players poistion based on isSprinting also makes sound
     *          for footsteps by alternating between the 2 different timers
     */
    private void update() {

        double angleInRadians = Math.toRadians(rotateY.getAngle());
        double angleInRadiansPlus90 = Math.toRadians(rotateY.getAngle() + 90);

        Point2D forwardVector = new Point2D(
                forwardsAcceleration * Math.sin(angleInRadians),
                forwardsAcceleration * Math.cos(angleInRadians));

        Point2D sidewardVector = new Point2D(
                sidewardsAcceleration * Math.sin(angleInRadiansPlus90),
                sidewardsAcceleration * Math.cos(angleInRadiansPlus90));

        double deltaX = forwardVector.getX() + sidewardVector.getX();
        double deltaZ = forwardVector.getY() + sidewardVector.getY();

        if (isSprinting) {
            deltaX *= sprintingSpeed;
            deltaZ *= sprintingSpeed;
        } else {
            deltaX *= walkingSpeed;
            deltaZ *= walkingSpeed;
        }

        if (checkCollision()) {
            translateXProperty().set(getTranslateX() + deltaX);
            translateZProperty().set(getTranslateZ() + deltaZ);
        } else {
            translateXProperty().set(getTranslateX() - deltaX);
            translateZProperty().set(getTranslateZ() - deltaZ);
        }

        if (forwardsAcceleration != 0 || sidewardsAcceleration != 0) {
            if (isSprinting) {
                if (!sprintingFootstepTimer.isRunning()) {
                    walkingFootstepTimer.stop();
                    sprintingFootstepTimer.start();
                }
            } else {
                if (!walkingFootstepTimer.isRunning()) {
                    sprintingFootstepTimer.stop();
                    walkingFootstepTimer.start();
                }
            }
        } else {
            sprintingFootstepTimer.stop();
            walkingFootstepTimer.stop();
        }
    }

    /**
     * Checks for collision with walls.
     * 
     * @param newX The new x-coordinate after movement.
     * @param newZ The new z-coordinate after movement.
     * @return True if there is no collision, false otherwise.
     */
    private boolean checkCollision() {
        for (Wall wall : mazeWalls) {
            if (isIntersecting(wall)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param Wall
     * @return true if the wall in param intersects this Player's hitbox
     */
    public boolean isIntersecting(Wall wall) {
        Bounds playerBounds = this.localToParent(this.getHitbox().getBoundsInParent());
        Bounds wallBounds = wall.getBoundsInParent();
        return playerBounds.intersects(wallBounds);
    }

    public Box getHitbox() {
        return hitbox;
    }

    public void setSidewardsAcceleration(double x) {
        sidewardsAcceleration = x;
    }

    public double getSidewardsAcceleration() {
        return sidewardsAcceleration;
    }

    public void setForwardsAcceleration(double zAcceleration) {
        this.forwardsAcceleration = zAcceleration;
    }

    public double getForwardsAcceleration() {
        return forwardsAcceleration;
    }

    public void setWalkingSpeed(double speed) {
        this.walkingSpeed = speed;
    }

    public void setIsSprinting(boolean isSprinting) {
        this.isSprinting = isSprinting;
    }

    public double getWalkingSpeed() {
        return this.walkingSpeed;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public Camera getCamera() {
        return playerCamera;
    }

    public Cell getCurrentCell() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentCell'");
    }
}