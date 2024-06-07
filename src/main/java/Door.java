package main.java;

import java.io.File;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Box;
import javafx.util.Duration;

public class Door {
    private String id;
    private boolean isOpen;
    private Box doorBox;

    public Door(String id) {
        this.id = id;
        this.isOpen = false;
        doorBox = new Box(0.8, 4.0 , 0.25);
    }

    public String getId() {
        return id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    private void playSound(){
        try {
            Media sound = new Media(new File("src/main/resources/sounds/dooropen.mp3").toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();
        } catch (Exception e) {}
    }

    private void animateDoor() {
        double rotationAngle = isOpen ? 90 : 0; // Open = 90 degrees, Closed = 0 degrees
        Timeline timeline = new Timeline();

        // Define the animation (replace durations and easing as needed)
        KeyValue keyValue = new KeyValue(doorBox.rotateProperty(), rotationAngle, Interpolator.LINEAR);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), keyValue));

        timeline.play();
    }
}