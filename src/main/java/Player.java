package main.java;

import java.io.File;

import javax.swing.Timer;

import javafx.application.Platform;
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

public class Player extends Group {

    public Camera playerCamera;
    /**
     * @apiNote a box that surrounds the character that is used for collision
     *          detection
     */
    private Box hitbox;
    private double sidewardsAcceleration, forwardsAcceleration;
    public Rotate rotateY;
    public Rotate rotateX;
    /**
     * @apiNote the parent scene
     */
    public SubScene scene;
    /**
     * @apiNote the speed of the character when he walks
     */
    private double walkingSpeed;
    /**
     * @apiNote a boolean variable that stores the value of wheter the character is
     *          sprinting or not
     */
    private boolean isSprinting;
    /**
     * @apiNote the speed of the character when he sprints
     */
    private double sprintingSpeed;
    /**
     * @apiNote the previous mouse X (used for getting delta mouseX)
     */
    private double prevMouseX;
    /**
     * @apiNote the previous mouse Y (used for getting delta mouseY)
     */
    private double prevMouseY;

    private double initialX = 0;
    private double initialY = 0;
    private boolean dragging = false;

    private Timer sprintingFootstepTimer = new Timer(400, sprintlistener -> {
        playSound();
    });

    private Timer walkingFootstepTimer = new Timer(800, walkingfootstepListener -> {
        playSound();
    });
    private boolean isMoving;

    public Player(double x, double y, double z, double v, double sv, SubScene s) {
        walkingSpeed = v;
        sprintingSpeed = sv;
        scene = s;
        hitbox = new Box(1, 80, 1);
        playerCamera = new PerspectiveCamera(true);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateX = new Rotate(0, Rotate.X_AXIS);
        playerCamera.setNearClip(0.1);
        playerCamera.setFarClip(700);
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

        getTransforms().addAll(rotateY, rotateX);
        setPosition(x, y, z);

        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                initialX = event.getSceneX();
                initialY = event.getSceneY();
                dragging = true;
            }
        });

        scene.setOnMouseDragged(event -> {
            if (dragging) {
                double deltaX = event.getSceneX() - initialX;
                double deltaY = event.getSceneY() - initialY;
                rotateX.setAngle(rotateX.getAngle() - deltaY / 30);
                rotateY.setAngle(rotateY.getAngle() + deltaX / 30);
                initialX = event.getSceneX();
                initialY = event.getSceneY();
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                dragging = false;
            }
        });

    }

    /**
     * @author Pranav
     * @apiNote this method generates the footstep noise when the player moves
     */
    private void playSound() {
        try {
            Media sound = new Media(new File("src/main/resources/sounds/footstep.mp3").toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();
        } catch (Exception e) {
        }
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

    public void setPosition(double x, double y, double z) {
        translateXProperty().set(x);
        translateYProperty().set(y);
        translateZProperty().set(z);
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    /**
     * @author Pranav, Subhash
     * @apiNote updates the players poistion based on isSprinting also makes sound
     *          for footsteps by alternating between the 2 different timers
     */

    public void update() {
        double angleInRadians = Math.toRadians(rotateY.getAngle());
        double angleInRadiansPlus90 = Math.toRadians(rotateY.getAngle() + 90);

        // Calculate the forward and sideward vectors for x and z axes
        Point2D forwardVector = new Point2D(
                forwardsAcceleration * Math.sin(angleInRadians),
                forwardsAcceleration * Math.cos(angleInRadians));

        Point2D sidewardVector = new Point2D(
                sidewardsAcceleration * Math.sin(angleInRadiansPlus90),
                sidewardsAcceleration * Math.cos(angleInRadiansPlus90));

        // Calculate the deltas for the new position
        double deltaX = forwardVector.getX() + sidewardVector.getX();
        double deltaZ = forwardVector.getY() + sidewardVector.getY();

        // Adjust speed based on whether the player is sprinting
        if (isSprinting) {
            deltaX *= sprintingSpeed;
            deltaZ *= sprintingSpeed;
        } else {
            deltaX *= walkingSpeed;
            deltaZ *= walkingSpeed;
        }

        // Update the position by adding the deltas to the current position
        double newX = getTranslateX() + deltaX;
        double newZ = getTranslateZ() + deltaZ;

        translateXProperty().set(newX);
        translateZProperty().set(newZ);

        // Timer management
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
    
    public void updateCollision(A){
        for (Box wall : walls) {
            if (isCollidingWithWall(wall)) {
                resolveWallCollision(wall);
            }
        }
    }

    public boolean isCollidingWithWall(Box wall) {
        // Calculate player and wall boundaries
        double playerMinX = hitbox.getTranslateX() - hitbox.getWidth() / 2;
        double playerMaxX = hitbox.getTranslateX() + hitbox.getWidth() / 2;
        double playerMinY = hitbox.getTranslateY() - hitbox.getHeight() / 2;
        double playerMaxY = hitbox.getTranslateY() + hitbox.getHeight() / 2;
        double playerMinZ = hitbox.getTranslateZ() - hitbox.getDepth() / 2;
        double playerMaxZ = hitbox.getTranslateZ() + hitbox.getDepth() / 2;

        double wallMinX = wall.getTranslateX() - wall.getWidth() / 2;
        double wallMaxX = wall.getTranslateX() + wall.getWidth() / 2;
        double wallMinY = wall.getTranslateY() - wall.getHeight() / 2;
        double wallMaxY = wall.getTranslateY() + wall.getHeight() / 2;
        double wallMinZ = wall.getTranslateZ() - wall.getDepth() / 2;
        double wallMaxZ = wall.getTranslateZ() + wall.getDepth() / 2;

        // Check for collision in each dimension
        boolean collisionX = (playerMinX <= wallMaxX && playerMaxX >= wallMinX);
        boolean collisionY = (playerMinY <= wallMaxY && playerMaxY >= wallMinY);
        boolean collisionZ = (playerMinZ <= wallMaxZ && playerMaxZ >= wallMinZ);

        // Return true if there is a collision in all dimensions
        return collisionX && collisionY && collisionZ;
    }

    public void resolveWallCollision(Box wall) {
        // If colliding with the wall, move the player outside the wall
        double playerMinX = hitbox.getTranslateX() - hitbox.getWidth() / 2;
        double playerMaxX = hitbox.getTranslateX() + hitbox.getWidth() / 2;
        double playerMinY = hitbox.getTranslateY() - hitbox.getHeight() / 2;
        double playerMaxY = hitbox.getTranslateY() + hitbox.getHeight() / 2;
        double playerMinZ = hitbox.getTranslateZ() - hitbox.getDepth() / 2;
        double playerMaxZ = hitbox.getTranslateZ() + hitbox.getDepth() / 2;

        double wallMinX = wall.getTranslateX() - wall.getWidth() / 2;
        double wallMaxX = wall.getTranslateX() + wall.getWidth() / 2;
        double wallMinY = wall.getTranslateY() - wall.getHeight() / 2;
        double wallMaxY = wall.getTranslateY() + wall.getHeight() / 2;
        double wallMinZ = wall.getTranslateZ() - wall.getDepth() / 2;
        double wallMaxZ = wall.getTranslateZ() + wall.getDepth() / 2;

        // Calculate the penetration depth in each dimension
        double penetrationX = Math.min(Math.abs(playerMinX - wallMaxX), Math.abs(playerMaxX - wallMinX));
        double penetrationY = Math.min(Math.abs(playerMinY - wallMaxY), Math.abs(playerMaxY - wallMinY));
        double penetrationZ = Math.min(Math.abs(playerMinZ - wallMaxZ), Math.abs(playerMaxZ - wallMinZ));

        // Move the player outside the wall
        if (penetrationX < penetrationY && penetrationX < penetrationZ) {
            if (playerMinX < wallMinX) {
                hitbox.setTranslateX(wallMinX + hitbox.getWidth() / 2);
            } else {
                hitbox.setTranslateX(wallMaxX - hitbox.getWidth() / 2);
            }
        } else if (penetrationY < penetrationX && penetrationY < penetrationZ) {
            if (playerMinY < wallMinY) {
                hitbox.setTranslateY(wallMinY + hitbox.getHeight() / 2);
            } else {
                hitbox.setTranslateY(wallMaxY - hitbox.getHeight() / 2);
            }
        } else {
            if (playerMinZ < wallMinZ) {
                hitbox.setTranslateZ(wallMinZ + hitbox.getDepth() / 2);
            } else {
                hitbox.setTranslateZ(wallMaxZ - hitbox.getDepth() / 2);
            }
        }
    }
    
}