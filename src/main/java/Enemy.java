package main.java;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends Group{

    public Enemy(){
        this.getChildren().add(new ImageView(new Image("file:src/main/resources/textures/enemy.png")));
    }
    
}