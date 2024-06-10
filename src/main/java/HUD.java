package main.java;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * @author Subhash
 */
public class HUD extends Group{

    private double stamina;
    private double health;
    private double torch;
    private Text staminaText;
    private Text healthText;
    private Text torchText;
    public HUD() {
        super();
        staminaText = new Text();
        healthText = new Text();
        torchText = new Text();
        staminaText.relocate(0, 0);
        torchText.relocate(0,13);
        this.getChildren().addAll(staminaText, healthText, torchText);
        stamina = 100;
        health = 100;
        torch = 100;
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        }.start();
    }

    /**
     * @apiNote updates the text of stamina health and torch based on their respective double fields
     */
    public void update(){
        staminaText.setText("stamina: " + stamina + "%");
        healthText.setText("health: " + health + "%");
        torchText.setText("torch: " + torch + "%");
    }

    public void setHealth(double health){
        this.health = health;
    }

    public double getHealth(){
        return this.health;
    }

    public void setStamina(double stamina){
        this.stamina = stamina;
    }

    public double getStamina(){
        return this.stamina;
    }

    public void setTorch(double torch){
        this.torch = torch;
    }

    public double getTorch(){
        return this.torch;
    }
}
