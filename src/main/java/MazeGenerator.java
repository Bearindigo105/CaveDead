package main.java;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Subhash
 * @apiNote This is a class for generating a maze of walls
 */
public class MazeGenerator {

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

        for (int i = 0; i < MAZE_WIDTH; i++) {
            walls.add(new Wall(getCell(i, 0), getCell(i, -1)));
            walls.add(new Wall(getCell(i, MAZE_HEIGHT - 1), getCell(i, MAZE_HEIGHT)));
        }
        for (int j = 0; j < MAZE_HEIGHT; j++) {
            walls.add(new Wall(getCell(0, j), getCell(-1, j)));
            walls.add(new Wall(getCell(MAZE_WIDTH - 1, j), getCell(MAZE_WIDTH, j)));
        }

        for (Cell cell : maze) {
            for (Cell neighbour : getNeighbours(cell)) {
                if (getWall(neighbour, cell) == null && getWall(cell, neighbour) == null && cell != neighbour) {
                    walls.add(new Wall(cell, neighbour));
                }
            }
        }

        Cell startCell = getCell(0, 0);
        if (startCell != null) {
            startCell.setIsVisited(true);
            DFSgenerateMaze(startCell);
        }
    }

    /**
     * @param currentCell
     * @apiNote Given a current cell as a parameter, Mark the current cell as
     *          visited, While the current cell has any unvisited neighbour cells:
     *          1. Choose one of the unvisited neighbours
     *          2. Remove the wall between the current cell and the chosen cell
     *          3. Invoke the routine recursively for the chosen cell
     */
    public void DFSgenerateMaze(Cell currentCell) {
        currentCell.setIsVisited(true);

        ArrayList<Cell> unvisitedNeighbours = getUnvisitedNeighbours(currentCell);
        Collections.shuffle(unvisitedNeighbours);

        for (Cell chosenNeighbor : unvisitedNeighbours) {
            if (!chosenNeighbor.getIsVisited()) {
                removeWall(currentCell, chosenNeighbor);
                DFSgenerateMaze(chosenNeighbor);
            }
        }
    }

    /**
     * @apiNote removes a wall (that is between two cells) from the walls array
     * @param cell1
     * @param cell2
     */
    private void removeWall(Cell c1, Cell c2) {
        if (c1 != null && c2 != null) {
            Wall wallToRemove = getWall(c1, c2);
            if (wallToRemove != null) {
                walls.remove(wallToRemove);
            }
        }
    }

    /**
     * @apiNote goes through all neighbours and if visited is false, add it to an
     *          arraylist
     * @param cell
     * @return an arraylist of Cell
     */
    public ArrayList<Cell> getUnvisitedNeighbours(Cell cell) {
        ArrayList<Cell> arrUnvisitedNeighbours = new ArrayList<Cell>();
        for (Cell neighbour : getNeighbours(cell)) {
            if (!neighbour.getIsVisited()) {
                arrUnvisitedNeighbours.add(neighbour);
            }
        }
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
        if (checkCell != null)
            arrNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX() - 1, cell.getMazeY());
        if (checkCell != null)
            arrNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX(), cell.getMazeY() + 1);
        if (checkCell != null)
            arrNeighbours.add(checkCell);
        checkCell = getCell(cell.getMazeX(), cell.getMazeY() - 1);
        if (checkCell != null)
            arrNeighbours.add(checkCell);

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
        return new Cell(x, y, true);
    }

    /**
     * @param cell1
     * @param cell2
     * @return returns a wall from walls that is between two cells
     */
    public Wall getWall(Cell c1, Cell c2) {
        for (Wall wall : walls) {
            if ((wall.getCell1() == c1 && wall.getCell2() == c2) || (wall.getCell1() == c2 && wall.getCell2() == c1)) {
                return wall;
            }
        }
        return null;
    }

    /**
     * @return returns field walls
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    /**
     * @apiNote prints the current state of the maze
     */
    public void printMaze() {
        char[][] mazeRepresentation = new char[MAZE_HEIGHT * 2 + 1][MAZE_WIDTH * 2 + 1];

        for (int y = 0; y < mazeRepresentation.length; y++) {
            for (int x = 0; x < mazeRepresentation[0].length; x++) {
                if (y % 2 == 0 && x % 2 == 0) {
                    mazeRepresentation[y][x] = '+';
                } else if (y % 2 == 0) {
                    mazeRepresentation[y][x] = '_';
                } else if (x % 2 == 0) {
                    mazeRepresentation[y][x] = '|';
                } else {
                    mazeRepresentation[y][x] = ' ';
                }
            }
        }

        for (int y = 0; y < MAZE_HEIGHT; y++) {
            for (int x = 0; x < MAZE_WIDTH; x++) {
                Cell cell = getCell(x, y);
                if (cell != null && cell.getIsVisited()) {
                    mazeRepresentation[y * 2 + 1][x * 2 + 1] = ' ';
                }
            }
        }

        for (Wall wall : walls) {
            int x1 = wall.getCell1().getMazeX() * 2 + 1;
            int y1 = wall.getCell1().getMazeY() * 2 + 1;
            int x2 = wall.getCell2().getMazeX() * 2 + 1;
            int y2 = wall.getCell2().getMazeY() * 2 + 1;

            if (x1 == x2) {
                mazeRepresentation[Math.min(y1, y2) + 1][x1] = ' ';
            } else if (y1 == y2) {
                mazeRepresentation[y1][Math.min(x1, x2) + 1] = ' ';
            }
        }

        for (int y = 0; y < mazeRepresentation.length; y++) {
            for (int x = 0; x < mazeRepresentation[0].length; x++) {
                System.out.print(mazeRepresentation[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
}