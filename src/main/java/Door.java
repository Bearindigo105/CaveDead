package main.java;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.Timer;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Door extends Box {

    private boolean isLocked;

    public Door(boolean locked){
        super(50, 80, 10);
        this.setRotationAxis(Rotate.Y_AXIS);
        isLocked = locked;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image("file:src/main/resources/images/metaldoor.jpg"));
        setMaterial(material);
    }

    public void openDoor(){
        playSound();
    }

    public void unlockDoor(){
        isLocked = false;
    }

    private void playSound(){
        try {
            Media sound = new Media(new File("src/main/resources/sounds/dooropen.mp3").toURI().toString());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();
        } catch (Exception e) {}
    }

}
