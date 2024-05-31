package main.java;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;

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
        loadFXML("../resources/fxmls/corridor.fxml");
    }

    public void loadFXML(String pathName) throws IOException {
        System.out.println(getClass().getResource(pathName));
        this.getChildren().addAll(((Group)FXMLLoader.load(Room.class.getResource(pathName))).getChildren());
    }
}