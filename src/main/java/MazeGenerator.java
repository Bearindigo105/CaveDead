package main.java;

import java.util.ArrayList;

/**
 * @author Subhash
 * @apiNote This is a class for generating a maze of walls
 */
public class MazeGenerator{

    private ArrayList<Cell> maze;
    private ArrayList<Wall> walls;
    public final int MAZE_WIDTH;
    public final int MAZE_HEIGHT;

    /**
     * @apiNote only constructor
     * @param x
     * @param y
     */
    public MazeGenerator(int x, int y) {
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
        currentCell.setIsVisited(true);
        ArrayList<Cell> unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
        while (unvisitedNeighbours.size() > 0) {
            Cell chosenNeighbor = unvisitedNeighbours.get((int) (Math.random() * unvisitedNeighbours.size()));
            removeWall(currentCell, chosenNeighbor);
            DFSgenerateMaze(chosenNeighbor);
            unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
        }
    }

    /**
     * @apiNote removes a wall (that is between two cells) from the walls array
     * @param cell1
     * @param cell2
     */
    private void removeWall(Cell c1, Cell c2) {
        if(c1 != null && c2 != null)
        walls.remove(getWall(c1, c2));
    }

    /**
     * @apiNote goes through all neighbours and if visited is false, add it to an arraylist
     * @param cell
     * @return an arraylist of Cell
     */
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

    /**
     * @apiNote gets Neighbours of a cell
     * @param cell
     * @return
     */
    public ArrayList<Cell> getNeighbours(Cell cell) {
        ArrayList<Cell> arrNeighbours = new ArrayList<Cell>();
        Cell checkCell;
        checkCell = getCell(cell.getMazeX() + 1, cell.getMazeY());
        if(checkCell != null) arrNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX() - 1, cell.getMazeY());
        if(checkCell != null) arrNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX(), cell.getMazeY() + 1);
        if(checkCell != null) arrNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX(), cell.getMazeY() - 1);
        if(checkCell != null) arrNeighbours.add(checkCell);
        
        return arrNeighbours;
    }

    /**
     * @param x
     * @param y
     * @return returns a cell that is looked up in the arraylist field maze
     */
    public Cell getCell(int x, int y) {
        for (Cell cell : maze) {
            if (cell.getMazeX() == x && cell.getMazeY() == y) {
                return cell;
            }
        }
        return null;
    }

    /**
     * 
     * @param cell1
     * @param cell2
     * @return returns a wall from walls that is between two cells
     */
    public Wall getWall(Cell c1, Cell c2){
        for (Wall wall : walls) {
            if (wall.getCell1() == c1 && wall.getCell2() == c2) {
                return wall;
            }
        }
        return null;
    }

    /**
     * 
     * @return returns field walls
     */
    public ArrayList<Wall> getWalls(){
        return walls;
    }

}