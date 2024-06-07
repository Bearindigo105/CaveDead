package main.java;

import javafx.scene.shape.Box;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Wall extends Box{
    private Cell c1;
    private Cell c2;
    private Rotate rotateY;

    public Wall(Cell c1, Cell c2){
        super(20,20,5);
        setMaterial(App.wallMaterial);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        this.c1 = c1;
        this.c2 = c2;
        translateXProperty().set((c1.getMazeX() + c2.getMazeX())/2);
        translateZProperty().set((c1.getMazeY() + c2.getMazeY())/2);
        
        rotateY.pivotXProperty().bind(translateXProperty());
        rotateY.pivotYProperty().bind(translateYProperty());
        rotateY.pivotZProperty().bind(translateZProperty());

        if(c1.getMazeX() - c2.getMazeX() > 0){
            
        }else if(c1.getMazeX() - c2.getMazeX() < 0){

        }else if(c1.getMazeX() - c2.getMazeX() > 0){
            
        }else if(c1.getMazeX() - c2.getMazeX() < 0){
            
        }
    }

    public Cell getCell1(){
        return c1;
    }
    public Cell getCell2(){
        return c2;
    }
}
