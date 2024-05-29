import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Door extends Box{

    public Door(PhongMaterial material) throws URISyntaxException{
        super(50, 80, 50);
        playSound();
        this.setMaterial(material);
    }

    private void playSound() throws URISyntaxException {
        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("/music/hero.mp3").toURI().toString()));
        player.play();
    }


}
