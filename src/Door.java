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

    public Door() throws URISyntaxException{
        super(50, 80, 10);
        playSound();
    }

    private void playSound() throws URISyntaxException {
        Media sound = new Media(new File("sounds/dooropen.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }


}
