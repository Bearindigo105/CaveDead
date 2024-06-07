package main.java;

import java.util.ArrayList;
import javafx.scene.Group;

public class Maze extends Group {

    private ArrayList<Cell> maze;
    private ArrayList<Wall> walls;
    public final int MAZE_WIDTH;
    public final int MAZE_HEIGHT;

    public Maze(int x, int y) {
        MAZE_HEIGHT = y;
        MAZE_WIDTH = x;

        maze = new ArrayList<Cell>();
        walls = new ArrayList<Wall>();

        for (int i = 0; i < MAZE_WIDTH; i++) {
            for (int j = 0; j < MAZE_HEIGHT; j++) {
                maze.add(new Cell(i, j, false));
            }
        }

        for (Cell cell : maze) {
            for (Cell neighbour : getUnvisitedNeighbours(cell)) {
                if(getWall(neighbour, cell) == null && getWall(cell, neighbour) == null && cell != neighbour){
                    walls.add(new Wall(cell, neighbour));
                }
            }

        }
        System.out.println();
        maze.set(0, new Cell(0, 0, true));
        DFSgenerateMaze(getCell(0, 0));
    }

    /**
     * 
     * @param currentCell
     * @apiNote
     *          Given a current cell as a parameter,
     *          Mark the current cell as visited,
     *          While the current cell has any unvisited neighbour cells:
     *          1. Choose one of the unvisited neighbours
     *          2. Remove the wall between the current cell and the chosen cell
     *          3. Invoke the routine recursively for the chosen cell
     */
    public void DFSgenerateMaze(Cell currentCell) {
        currentCell.setVisited(true);
        ArrayList<Cell> unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
        while (unvisitedNeighbours.size() > 0) {
            Cell chosenNeighbor = unvisitedNeighbours.get((int) (Math.random() * unvisitedNeighbours.size()));
            removeWall(currentCell, chosenNeighbor);
            DFSgenerateMaze(chosenNeighbor);
            unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
        }
    }

    private void removeWall(Cell currentCell, Cell chosenNeighbor) {
        walls.remove(getWall(currentCell, chosenNeighbor));
    }

    public ArrayList<Cell> getUnvisitedNeighbours(Cell cell) {
        ArrayList<Cell> arrUnvisitedNeighbours = new ArrayList<Cell>();
        Cell checkCell;
        checkCell = getCell(cell.getMazeX() + 1, cell.getMazeY());
        if(checkCell != null && !checkCell.getIsVisited()) arrUnvisitedNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX() - 1, cell.getMazeY());
        if(checkCell != null && !checkCell.getIsVisited()) arrUnvisitedNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX(), cell.getMazeY() + 1);
        if(checkCell != null && !checkCell.getIsVisited()) arrUnvisitedNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX(), cell.getMazeY() - 1);
        if(checkCell != null && !checkCell.getIsVisited()) arrUnvisitedNeighbours.add(checkCell);
        
        return arrUnvisitedNeighbours;
    }

    public Cell getCell(int x, int y) {
        for (Cell cell : maze) {
            if (cell.getMazeX() == x && cell.getMazeY() == y) {
                return cell;
            }
        }
        return null;
    }

    public Wall getWall(Cell c1, Cell c2){
        for (Wall wall : walls) {
            if (wall.getCell1() == c1 && wall.getCell2() == c2) {
                return wall;
            }
        }
        return null;
    }

    public ArrayList<Wall> getWalls(){
        return walls;
    }

}