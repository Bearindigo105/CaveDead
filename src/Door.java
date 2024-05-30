import java.io.File;
import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Box;

public class Door extends Box{

    public Door() throws URISyntaxException{
        super(50, 80, 10);
        playSound();
    }

    private void playSound() throws URISyntaxException {
        Media sound = new Media("file:sounds/dooropen.mp3");
        MediaPlayer player = new MediaPlayer(sound);
        player.play();
    }


}
