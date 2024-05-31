package main.java;
import java.io.File;
import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Door extends Box {

    public Door(PhongMaterial material) throws URISyntaxException {
        super(50, 80, 10);
        playSound();
        setMaterial(material);
    }

    private void playSound() throws URISyntaxException {
        Media sound = new Media(new File("src/main/resources/sounds/dooropen.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }

}
