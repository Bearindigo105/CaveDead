# APCSAFinalProject : caveDEAD

This is a repository for our AP Computer Science A final project named

## caveDEAD

### Description:
In this game you will use the WASD keys to move your character forward, backwards, left, and right. Using these movements you will try to navigate yourself through a maze in first person. You are losing health the longer you take to complete the maze so time is very important. You can also press the shift button then WASD to sprint. The sprint feature has a cooldown that can be seen in the HUD. When your sprint gauge/percent hits 0 you will have to let go of the sprint key to regenerate your sprint. 

### Problems Encountered:
At the start we encountered a lot of issues with github and creating proper repositories that we could work on, and on how to import the libraries properly. Other issues that we faced was implementing the maze generator and collision detection. 

### Features Not Implemented:
We tried to add someone that chases the character through the maze and pickable items such as torches and medkits but unfortunately we could not implement them. 

### Reflection:
We both learnt a lot about javafx, importing refrenced libraries, fxmls, xml, 3D collision detection and other elements in general. Throughout the project the issues that we face with the libraries helped us understand them better and how to fix those issues more efficently next time, learning about the api and what javafx can do also broadened our knowledge about it, and finally implementing 3D colision detection helped us learn more about the 3D elements in code and how to better solve those issues. 

### DOCUMENTATION (Incomplete):

#### App

##### Constants:

    WIDTH and HEIGHT: Define the window size of the game.
    wallMaterial: A PhongMaterial object used for the texture of the maze walls.

##### Public Fields:

    player: A reference to the Player object representing the game character.

##### Methods:

    start(Stage primaryStage): The main entry point of the application. It performs the following:
        Sets up the wall texture using an image file.
        Creates a MazeGenerator object to generate the maze layout.
        Creates a SubScene for the game view and a group for the map view.
        Initializes the Player object with its position, speeds, scene reference, and maze walls.
        Creates a HUD object for displaying health and stamina.
        Sets up the game scene with the SubScene for the game view, HUD at the top, and the map group.
        Creates two timers:
            sprintingTimer: Reduces stamina while sprinting.
            sprintRegenTimer: Recovers stamina when not sprinting.
        Sets up event handlers for key presses and releases to control player movement and sprinting.
        Creates a title scene with a title image and a play button.
        Sets the stage properties and shows the initial title scene.
        Defines the behavior when the play button is pressed:
            Switches the scene to the game scene.
            Starts an AnimationTimer that slowly reduces the player's health (presumably to simulate health drain or a time limit).
            Starts the player's update timer, likely for continuous movement and game logic updates.

    playSound(): This method attempts to play a sound effect from a file specified by a path. It uses a Media and MediaPlayer object to play the sound.

    main(String[] args): Launches the JavaFX application.

#### MazeGenerator

##### Fields:

    maze: An ArrayList of Cell objects representing the grid cells of the maze.
    walls: An ArrayList of Wall objects representing the walls between cells.
    MAZE_WIDTH: The width of the maze.
    MAZE_HEIGHT: The height of the maze.

##### Constructor:

    MazeGenerator(int x, int y): Initializes the maze dimensions and creates empty ArrayLists for cells and walls. It also populates the maze with Cell objects and creates border walls around the entire maze.

##### Methods:

    DFSgenerateMaze(Cell currentCell): This is the core recursive function that implements the DFS algorithm. It performs the following steps:
        Marks the current cell as visited.
        Gets a list of unvisited neighbors of the current cell and shuffles them for randomness.
        Loops through each unvisited neighbor:
            Checks if the neighbor is not visited.
            Removes the wall between the current cell and the neighbor.
            Recursively calls DFSgenerateMaze on the neighbor cell to explore further.

    removeWall(Cell c1, Cell c2): Removes a wall between two cells from the walls ArrayList.

    getUnvisitedNeighbours(Cell cell): Returns a list of unvisited neighbor cells of a given cell.

    getNeighbours(Cell cell): Returns a list of all neighbor cells (visited or unvisited) of a given cell.

    getCell(int x, int y): Retrieves a cell from the maze ArrayList based on its coordinates. If the cell doesn't exist, it creates a new one (which shouldn't happen with valid coordinates).

    getWall(Cell c1, Cell c2): Checks the walls ArrayList to find and return a wall between two specified cells.

    getWalls(): Returns the walls ArrayList containing all the walls in the maze.

    printMaze(): This method is for debugging purposes. It creates a character representation of the maze with '+' for intersections, '_' for horizontal walls, '|' for vertical walls, and spaces for empty corridors.  It then prints this representation to the console.

#### Wall
##### Properties:

    c1 and c2: References to the two Cell objects that the wall connects.
    rotateY: A Rotate object used to rotate the wall on the Y-axis.

##### Constructor:

    Wall(Cell c1, Cell c2): The only constructor that takes two Cell objects representing the endpoints of the wall. It creates a 3D box with specified dimensions, sets the material, and positions and rotates the wall based on the provided cells.

##### Methods:

    getCell1(): Returns the first cell connected to the wall.
    getCell2(): Returns the second cell connected to the wall.
    isIntersecting(double x, double z, double width, double height): Commented out This method checks if a rectangular area defined by the parameters intersects with the wall.
    isIntersecting(double playerX, double playerZ, double playerWidth, double playerDepth): This method checks if the player's hitbox (represented by a box with center at playerX, playerZ and dimensions playerWidth and playerDepth) intersects with the wall. It calculates the bounding boxes for both the wall and the player and then checks for overlap.
#### Cell
##### Fields:

    isVisited: A boolean flag indicating whether the cell has been visited by the maze generation algorithm.
    mazeX: The X-coordinate of the cell within the maze grid.
    mazeY: The Y-coordinate of the cell within the maze grid.
    parent: A reference to the parent cell in the path during the maze generation (potentially used for solving mazes).

##### Constructor:

    Cell(int x, int y, boolean isVisited): Initializes the cell with its coordinates, visited state, and sets the parent to null (initially).

##### Methods:

    setIsVisited(boolean visited): Sets the visited flag of the cell.

    getIsVisited(): Returns the visited flag of the cell.

    getMazeX(): Returns the X-coordinate of the cell.

    getMazeY(): Returns the Y-coordinate of the cell.

    setParent(Cell p): Sets the parent cell reference for this cell.

    getParent(): Returns the parent cell reference for this cell.

    toString(): This method overrides the default toString() method and is used when a Cell object is directly converted to a String. It returns "#" if the cell is visited and "_" (underscore) if it's not visited. This provides a simple way to visualize the maze during debugging by printing the maze representation

