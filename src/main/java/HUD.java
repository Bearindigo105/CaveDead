package main.java;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class HUD extends Pane{
    private int stamina;
    private int health;
    private int torch;
    private Text staminaText;
    private Text healthText;
    private Text torchText;
    public HUD() {
        super();
        staminaText = new Text();
        healthText = new Text();
        torchText = new Text();
        stamina = 100;
        health = 100;
        torch = 100;
    }

    public void update(){
        staminaText.setText("stamina: " + stamina + "%");
        healthText.setText("health: " + health + "%");
        torchText.setText("torch: " + torch + "%");
    }
}
