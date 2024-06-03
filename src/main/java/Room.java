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
                this.getChildren().addAll(wall1, wall2);
                break;
        }
    }

    public void bindAndAdd(Node node, double x, double y, double z){
        node.translateYProperty().bind(translateYProperty());
        node.translateXProperty().bind(translateXProperty());
    }
}