package main.java;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * @author Subhash
 */
public class HUD extends Group {

    private double stamina;
    private Text staminaText;
    private Text timeText;
    private Timer timer;
    private int timeLeft;
    public final int WIDTH;
    public final int HEIGHT;

    public HUD(int w, int h) {
        super();
        WIDTH = w;
        HEIGHT = h;
        staminaText = new Text();
        timeText = new Text();
        staminaText.relocate(0, 0);
        this.getChildren().addAll(staminaText, timeText);
        stamina = 100;
        timeLeft = 0;

        
        
        new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                update();
            }
        }.start();

        int delay = 1000; // thanks stackoverflow
        int period = 1000;
        timer = new java.util.Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                setTimeLeft();
            }
        }, delay, period);
    }

    /**
     * @apiNote updates the text of stamina health and health based on their
     *          respective double fields
     */
    public void update() {
        staminaText.setText(String.format("stamina: %3f", stamina));
        timeText.setText(String.format("timer: %d:%02d", timeLeft / 60, timeLeft % 60));
    }
    
    public final int setTimeLeft() {
        return ++timeLeft;
    }

    public int getTimeLeft() {
        return this.timeLeft;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public double getStamina() {
        return this.stamina;
    }
}
