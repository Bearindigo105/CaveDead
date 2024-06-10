package main.java;

/**
 * @author Subhash
 */
public class Cell {
    
    private boolean isVisited;
    private int mazeX;
    private int mazeY;
    private Cell parent;

    public Cell(int x, int y, boolean isVisited){
        this.isVisited = isVisited;
        mazeX = x;
        mazeY = y;
    }

    public void setIsVisited(boolean visited){
        isVisited = visited;
    }
    
    public boolean getIsVisited(){
        return isVisited;
    }

    public int getMazeX() {
        return mazeX;
    }

    public int getMazeY() {
        return mazeY;
    }

    public void setParent(Cell p){
        this.parent = p;
    }

    public Cell getParent(){
        return parent;
    }
    
    /**
     * @return "#" if isVisited else "_"
     */
    @Override
    public String toString(){
        return isVisited ? "#" : "_";
    }
}
