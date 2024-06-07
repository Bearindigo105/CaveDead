package main.java;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class HUD extends Group{
    private int stamina;
    private int health;
    private int torch;
    private Text staminaText;
    private Text healthText;
    private Text torchText;
    private Inventory inventory;
    public HUD() {
        super();
        staminaText = new Text();
        healthText = new Text();
        torchText = new Text();
        inventory = new Inventory(5);
        staminaText.relocate(0, 0);
        torchText.relocate(0,13);
        this.getChildren().addAll(staminaText, healthText, torchText, inventory);
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

    public void update(){
        staminaText.setText("stamina: " + stamina + "%");
        healthText.setText("health: " + health + "%");
        torchText.setText("torch: " + torch + "%");
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setStamina(int stamina){
        this.stamina = stamina;
    }

    public int getStamina(){
        return this.stamina;
    }

    public void setTorch(int torch){
        this.torch = torch;
    }

    public int getTorch(){
        return this.torch;
    }
}
