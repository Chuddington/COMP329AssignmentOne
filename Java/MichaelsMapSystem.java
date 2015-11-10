import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.*;

public class MapSystem {
  
  final static int     robotSize      = 25   ; //Used for travelling distance
        static int     xPos, yPos     = 0    ; //Current Position of Robot
        static int[]   position       = {0,0}; //robots position
        static int     i              = 0    ; //count used for position, limit
        static boolean heading        = false; //false = facing Y Axis; True = X
        static int     direction      = 1    ; //1-4 range; 1 = Up, 4 = Right
        static int     unknownObjs    = 4    ; //number of obstacles not found
        static int[][] map                   ; //map to be completed
        static int[][] occGrid               ; //probability grid
        static int[][] countGrid             ; //grid to count scans
        static int     dest                  ; //distance to object ahead
        static int     totalCells            ; //number of cells
        static int     xLimit, yLimit        ; //Stores max. array size for map 
        static UltrasonicSensor us    = new  UltrasonicSensor(SensorPort.S4);
    
  //Constructor - c = columns; r = number of cells in each column
  MapSystem(int c, int r) {
    //Store parameters locally
    xLimit     = c                ;
    yLimit     = r                ;
    totalCells = (xLimit * yLimit); //Number of cells within the arena
    
    //Variable Definitions
    map        = new int[xLimit][yLimit]; //Stores cell state
    occGrid    = new int[xLimit][yLimit]; //Stores cell probability state
    countGrid  = new int[xLimit][yLimit]; //Stores cell scan count

    //normalise array values
    for(int loop1 = 0; loop1 < xLimit; ++loop1) {
      for(int loop2 = 0; loop2 < yLimit; ++loop2) {
        map[loop1][loop2]       = 0;
        occGrid[loop1][loop2]   = 0;
        countGrid[loop1][loop2] = 0;
      }
    }
    us.continuous();
  }
  
  //Works out the probability of the cell ahead being occupied.
  public static double basicProb() {
    return ( ( (double)unknownObjs / (double)totalCells) * 100);
  }

  //Method to increase probability for a given cell
  public static void incOccGrid(int x, int y) {
    ++occGrid[x][y]  ;
    ++countGrid[x][y];
  }

  //Method to decrease probability for a given cell
  public static void decOccGrid(int x, int y) {
    --occGrid[x][y]  ;
    ++countGrid[x][y];
  }

  //Method to calculate probability of a cell being occupied
  public static double calcCellProb(int x, int y) {
    double numerator = (double)occGrid[x][y] + (double)countGrid[x][y];
    return (numerator / (2 * (double)countGrid[x][y] ) );
  }

  //Scans the cell in front of the robot by turning its Ultrasonic sensor
  public static boolean scanAhead() {
    Motor.A.rotateTo(0)    ; //rotate Ultrasonic sensor to front
    
    updateMap(us.getDistance(), direction);
    
    //if an obstacle is close
    if(dest < 30) {
      return true ;
    } else {
      return false;
    }   
  }
    
  //Scans the cell to the left of the robot by turning its Ultrasonic sensor
  public static boolean scanLeft() {
    Motor.A.rotateTo(-650); //rotate to left
    leftTurn() ;
    
    updateMap(us.getDistance(), direction);
        
    Motor.A.rotateTo(0)   ; //rotate to front
    rightTurn();
    
    //if an obstacle is close
    if(dest < 30) {
      return true ;
    } else {
      return false;
    }   
  }
    
  //Scans the cell to the right of the robot by turning its Ultrasonic sensor
  public static boolean scanRight() {
    Motor.A.rotateTo(650); //rotate to right
    rightTurn();

    updateMap(us.getDistance(), direction);
        
    Motor.A.rotateTo(0)  ; //rotate to front
    leftTurn() ;
    
    //if an obstacle is close
    if(dest < 30) {
      return true ;
    } else {
      return false;
    }                  
  }
    
  //Updates current position of robot
  public static void updatePosition() {
    switch (direction) {
    case 1: //facing up
      ++yPos;
      break;
    case 2: //facing right
      ++xPos;
      break;
    case 3: //facing down
      --yPos;
      break;
    case 4: //facing left
      --xPos;
      break;
    }
  }
    
    
  //How the map system recognises a right turn
  public static void rightTurn() {
    ++direction    ;     //change direction in a clockwise fashion
    if(direction == 5) { //if direction is out of the 1-4 range
      direction = 1;     //resets the direction to up
    } else {
    }
  }
  
  //How the map system recognises a left turn
  public static void leftTurn() {
    --direction    ;     //change direction in an anti-clockwise fashion
    if(direction == 0) { //if direction is out of the 1-4 range
      direction = 4;     //resets the direction to right
    } else {
    }
  }
  
  //Decides whether an obstacle or empty space is in the cell in front
  public static void updateMap(int dis, int dir) {

    // if robot is facing at a wall and is next to said wall
    if( (xPos == (xLimit - 1) && (direction == 2) ) || 
        (yPos == (yLimit - 1) && (direction == 1) ) ||
        (xPos == 0 && (direction == 4) ) || 
        (yPos == 0 && (direction == 3) ) ) {
        //do nothing

    //if cell the Ultrasonic sensor's is pointing at is occupied
    } else if (dis < robotSize) { 
      switch(dir) {
        case 1: //when sensor faces up
          map[xPos + 1][yPos] = 1;
          incOccGrid(   (xPos + 1), yPos); //increase probability
          incCountGrid( (xPos + 1), yPos); //increase count
          break;
        case 2: //when sensor faces right
          map[xPos][yPos + 1] = 1;
          incOccGrid(xPos,   (yPos + 1) ); //increase probability
          incCountGrid(xPos, (yPos + 1) ); //increase count
          break;
        case 3: //when sensor faces down
          map[xPos - 1][yPos] = 1;
          incOccGrid(   (xPos - 1), yPos); //increase probability
          incCountGrid( (xPos - 1), yPos); //increase count
          break;
        case 4: //when sensor faces left
          map[xPos][yPos - 1] = 1;
          incOccGrid(xPos,   (yPos - 1) ); //increase probability
          incCountGrid(xPos, (yPos - 1) ); //increase count
          break;
      }
      --totalCells ; //lower total unknown cells; increases basic probability
      --unknownObjs; //lower unknown obstacles;   decreases basic probability
      
    //if cell ahead is empty
    } else {
      switch(dir) {
        case 1: //when sensor faces up
          map[xPos + 1][yPos] = 2;
          decOccGrid(   (xPos + 1), yPos); //decrease probability
          incCountGrid( (xPos + 1), yPos); //increase count
          break;
        case 2: //when sensor faces right
          map[xPos][yPos + 1] = 2;
          decOccGrid(xPos,   (yPos + 1) ); //decrease probability
          incCountGrid(xPos, (yPos + 1) ); //increase count
          break;
        case 3: //when sensor faces down
          map[xPos - 1][yPos] = 2;
          decOccGrid(   (xPos - 1), yPos); //decrease probability
          incCountGrid( (xPos - 1), yPos); //increase count
          break;
        case 4: //when sensor faces left
          map[xPos][yPos - 1] = 2;
          decOccGrid(xPos,   (yPos - 1) ); //decrease probability
          incCountGrid(xPos, (yPos - 1) ); //increase count
          break;
      }
      --totalCells ; //lower total unknown cells; increases basic probability
    }
  }

  //Prints current map to lcd screen
  public static void printMap(int[][] target, int col, int row) {
    LCD.clear();

    //for each row (Y axis) going backwards for output layout purposes
    for(int loop1 = (row - 1); loop1 >= 0; --loop1) {
      //for each cell in the current row
      for(int loop2 = (col - 1); loop2 >= 0; --loop2) {
        System.out.print(target[loop2][loop1] );
      }
      System.out.print('\n');
    }
  }
  
  //Puts current map to string for RConsole
  public static String getMap(int[][] target, int c, int r) {
    StringBuilder sb = new StringBuilder();

    //for each row (Y axis) going backwards for output layout purposes
    for(int loop1 = (row - 1); loop1 >= 0; --loop1) {
      //for each cell in the current row
      for(int loop2 = (col - 1); loop2 >= 0; --loop2) {
        sb.append(target[loop2][loop1] ); //concatenate element to String Builder
        sb.append(" ") ; //space added for layout purposes
      }
      sb.append("\n")  ; //newline to indicate different row
    }
    return sb.toString();
  }
}
