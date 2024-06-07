package main.java;

public class Cell {
    
    private boolean isVisited;
    private int mazeX;
    private int mazeY;

    public Cell(int x, int y, boolean isVisited){
        this.isVisited = isVisited;
        mazeX = x;
        mazeY = y;
    }

    public void setVisited(boolean visited){
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
    
    @Override
    public String toString(){
        return isVisited ? "#" : "_";
    }
}
