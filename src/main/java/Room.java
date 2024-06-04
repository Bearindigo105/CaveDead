package main.java;

import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.*;

public class Room extends Group {
    private Type type;

    public static enum Type {
        blank,
        corridor,
        leftcorridor,
        rightcorridor,
        square,
        twodoor,
        threedoor
    }

    public Room() {
        super();
        this.type = Type.blank;
    }

    public Room(Type type) throws IOException {
        super();
        this.type = type;
        LoadRoom();
    }

    public void LoadRoom() {
        switch (type) {
            case Type.blank:
                break;
            case Type.corridor:
                Box wall1 = new Box(10, 80, 300);
                Box wall2 = new Box(10, 80, 300);
                Door door1 = new Door(false);
                setPosition(wall1, getTranslateX() + -50, 0, 0);
                setPosition(wall2, getTranslateX() + 50, 0, 0);
                setPosition(door1, getTranslateX(), 0, 150);
                this.getChildren().addAll(door1, wall1, wall2);
                break;
        }
    }

    public void addPosition(Node node, double x, double y, double z){
        node.translateXProperty().add(x);
        node.translateYProperty().add(y);
        node.translateZProperty().add(z);
    }

    public void setPosition(Node node, double x, double y, double z) {
        node.translateXProperty().set(x);
        node.translateYProperty().set(y);
        node.translateZProperty().set(z);
    }
}