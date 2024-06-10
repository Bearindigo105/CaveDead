import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Enemy extends Group {

    private final double SPEED;
    private final Cell targetCell; // Player's current cell
    private Cell currentCell;
    private double sidewardsAcceleration;
    private double forwardsAcceleration;
    private Rotate rotateY;
    private Box hitbox;
    private final List<Wall> mazeWalls;
    private AnimationTimer updateTimer;

    public Enemy(double speed, Cell startCell, List<Wall> mazeWalls, Player player) {
        this.SPEED = speed;
        this.currentCell = startCell;
        this.mazeWalls = mazeWalls;
        this.sidewardsAcceleration = 0;
        this.forwardsAcceleration = 0;
        this.rotateY = new Rotate(0, Rotate.Y_AXIS);

        this.hitbox = new Box(20, 80, 20);
        this.getChildren().addAll(hitbox);

        this.targetCell = player.getCurrentCell(); // Initially set to player's cell
        setupMovement();
        updateTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    private Cell findFarthestVisibleCell(Cell startCell, List<Wall> mazeWalls) {
        // Not used in this implementation, can be used for alternative enemy behavior
        return null;
    }

    private void setupMovement() {
        // Similar to Player class, set up movement based on target cell and collisions
    }

    private void update() {
        // Check if reached the target cell (player's current location)
        if (currentCell.equals(targetCell)) {
            // Player might have moved, so find a new target cell (update every few frames)
            if (updateTimer.getCurrentTime() % 2000000000L > 0) { // Update every 2 seconds
                targetCell = findPathToPlayer(currentCell, mazeWalls);
            }
            return;
        }

        // Move towards the target cell using pathfinding
        Cell nextCell = getNextCellInPath(currentCell, targetCell, mazeWalls);
        if (nextCell != null) {
            updateMovementTowardsCell(nextCell);
            currentCell = nextCell;
        } else {
            // Couldn' t find a path, potentially restart enemy or find a new random target
            System.out.println("Enemy lost track of player!");
        }
    }

    private void updateMovementTowardsCell(Cell targetCell) {
        // Similar to Player.update, calculate direction and set accelerations
        double deltaX = targetCell.getMazeX() - currentCell.getMazeX();
        double deltaZ = targetCell.getMazeY() - currentCell.getMazeY();

        double angleInRadians = Math.atan2(deltaZ, deltaX);
        sidewardsAcceleration = SPEED * Math.sin(angleInRadians);
        forwardsAcceleration = SPEED * Math.cos(angleInRadians);

        // Update enemy rotation to face the target cell (optional)
        rotateY.setAngle(-Math.toDegrees(angleInRadians));
    }

    private Cell findPathToPlayer(Cell currentCell, List<Wall> mazeWalls) {
        // Implement Breadth-First Search (BFS) to find the shortest path to the player
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();

        queue.add(currentCell);
        visited.add(currentCell);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            for (Cell neighbor : cell.getVisibleNeighbors(mazeWalls)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    if (neighbor.equals(targetCell)) {
                        return neighbor; // Found the player!
                    }
                    queue.add(neighbor);
                }
            }
        }
        return null; // Couldn't find a path
    }

  private Cell getNextCellInPath(Cell currentCell, Cell targetCell, List<Wall> mazeWalls) {
    // Get the visible neighbors and check which one is closest to the target cell
    List<Cell> neighbors = currentCell.getVisibleNeighbors(mazeWalls);
    double minDistance = Double.MAX_VALUE;
    Cell nextCell = null;
    for (Cell neighbor : neighbors) {
      double distance = neighbor.getDistanceTo(targetCell);
      if (distance < minDistance) {
        minDistance = distance;
        nextCell = neighbor;
      }
    }
    return nextCell;
  }
  public Box getHitbox() {
    return hitbox;
  }

  public void startMovement() {
    updateTimer.start();
  }

  public void stopMovement() {
    updateTimer.stop();
  }
}