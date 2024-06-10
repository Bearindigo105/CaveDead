package main.java;

import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 * @author Subhash, Pranav
 * @apiNote a class that can be utilized to make walls of a maze super easily
 */
public class Wall extends Box {
    private Cell c1;
    private Cell c2;
    private Rotate rotateY;

    /**
     * @apiNote only constructor. also rotates the wall and positions it in the
     * 
     *          correct spot.
     */
    public Wall(Cell c1, Cell c2) {
        super(100, 100, 20); // Adjust dimensions as needed
        setMaterial(App.wallMaterial);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        this.c1 = c1;
        this.c2 = c2;

        // Calculate midpoint between two cells
        double midX = (c1.getMazeX() + c2.getMazeX()) / 2.0;
        double midY = (c1.getMazeY() + c2.getMazeY()) / 2.0;

        // Set wall position based on midpoint
        translateXProperty().set(midX * 100); // Adjust scale factor as needed
        translateZProperty().set(midY * 100); // Adjust scale factor as needed

        rotateY.pivotXProperty().bind(translateXProperty());
        rotateY.pivotZProperty().bind(translateZProperty());

        if (c1.getMazeX() < c2.getMazeX()) {
            getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        } else if (c1.getMazeX() > c2.getMazeX()) {
            getTransforms().add(new Rotate(270, Rotate.Y_AXIS));
        } else if (c1.getMazeY() > c2.getMazeY()) {
            getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
        }
    }

    public Cell getCell1() {
        return c1;
    }

    public Cell getCell2() {
        return c2;
    }
}
