import java.util.Collection;

import javafx.scene.Group;
import javafx.scene.Node;

public class Room extends Group {
    private Type type;

    public static enum Type{
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

    public Room(Type type) {
        super();
        this.type = type;
    }

    public Room(Type type, Node... var1) {
        super(var1);
        this.type = type;
    }

    public Room(Type type, Collection<Node> var1) {
        super(var1);
        this.type = type;
    }
}
