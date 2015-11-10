import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;

public class MapSystem {
	
	static int[] limit; 				//highest coordinates
	final static int robotSize = 25 ;				//size of robot
    static int xLimit, yLimit ; 
	
	static int[][] map;                 	//map to be completed
    static int xPos, yPos = 0;     //variables for X and Y position for the map array
	static int[] position = {0,0};			//robots position
	static int i = 0;						//counter to be used for position, limit

	static boolean heading = false; //Boolean value - false = facing Y Axis
	static int direction = 1;			//direction 1-4 of where the robot is facing
	
    //variables added to compile successfully
    static int dest;                    //distance to object ahead
    static int totalCells;              //number of cells
    static int unknownObjs = 4;         //number of obstacles not found
    
    
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
    
    //Constructor
    MapSystem(int c, int r) {
        xLimit = c;
        yLimit = r;        
        map = new int[c][r]; 			//map to be completed
        totalCells = (c * r);
        us.continuous();
    }
    
    /* Works out the probability of the cell
     * ahead being occupied.
     */
    public static float basicProb() {
        float x = ( (unknownObjs / totalCells) * 100);
        return x;
    }

    /* Scans the cell to the ahead of 
     * the robot by turning its head
     */
    public static boolean scanAhead() {
        Motor.A.rotateTo(0);            //rotate to front
        
        dest = us.getDistance();        //scan
        updateMap();
        
        if(dest < 30) {
            return true;
        } else {
            return false;
        }
        
    }
    
    /* Scans the cell to the left of 
     * the robot by turning its head
     */
    public static boolean scanLeft() {
        Motor.A.rotateTo(-650);         //rotate to left
        leftTurn();
        
        dest = us.getDistance();        //scan
        updateMap();         
        
        Motor.A.rotateTo(0);            //rotate to front
        rightTurn();
        
        if(dest < 30) {
            return true;
        } else {
            return false;
        }
        
    }
    
    /* Scans the cell to the right of 
     * the robot by turning its head
     */
    public static boolean scanRight() {
        Motor.A.rotateTo(650);          //rotate to right
        rightTurn();
        
        dest = us.getDistance();        //scan 
        updateMap();
        
        Motor.A.rotateTo(0);
        leftTurn();
        
        if(dest < 30) {
            return true;
        } else {
            return false;
        }                  
    }
    
    /* Updates the current position of the robot 
     * based on it's current axis and heading.
     */
    public static void updatePosition() {
        switch (direction) {
        case 1: //facing up
            ++yPos;
            break;
        case 2:  //facing right
            ++xPos;
            break;
        case 3:  //facing down
            --yPos;
            break;
        case 4:  //facing left
            --xPos;
            break;
        }
 
    }
    
    
	/* How the map system recognises a 
	 * right turn.
	 */
	public static void rightTurn() {
		if (direction == 4){		//if max direction
			direction = 1;		    //reset
		} else {
			direction++;		    //change direction
		}
		
		heading = axisCheck(direction);
	}
	
	/* How the map system recognises a 
	 * right turn.
	 */
	public static void leftTurn() {
		if (direction == 1){
			direction = 4;
		} else {
			direction--;
		}
		
		heading = axisCheck(direction);
	}
	
	/* Decides whether there is an obstacle
     * or an empty space ahead then adds it
     * to the map.
	 */
	public static void updateMap() {
        
        dest = us.getDistance();	    //distance to destination is distance from sonar
        while (dest == 255) {			//get correct distance (to account for 255 error)
            dest = us.getDistance();			
        }
	
        // if robot is facing at a wall and is next to said wall
        if( (xPos == (xLimit - 1) && (direction == 2) ) || 
            (yPos == (yLimit - 1) && (direction == 1) ) ||
            (xPos == 0 && (direction == 4) ) || 
            (yPos == 0 && (direction == 3) ) ) {
                //do nothing
                
        } else if (dest < robotSize) { //if cell ahead is occupied or too close to the wall
        
            if (axisCheck(direction) == true) {											//if x axis
                map[position[xPos]][position[yPos] + heading] = 1;		    //mark obstacle on map
            } else if (i == 0) {									//if y axis
                map[position[0] + heading][position[1]] = 1;        
            }
            unknownObjs--;
            totalCells--;
            
        } else {
            
            if (i == 1) {											//if x axis
                map[position[0]][position[1] + heading] = -1;		    //mark empty space on map
            } else if (i == 0) {									//if y axis
                map[position[0] + heading][position[1]] = -1;
            }
            totalCells--;
        }
    }

	/* Prints current map to lcd 
     * screen.
     */
    public static void printMap(int c, int r) {
        for(int i = 0; i < c; i++) {
			for(int j = 0; j < r; j++){
				System.out.print(map[j][i]);
			}
			System.out.print('\n');
		}   
    }
	
	/* Puts map into a string for 
     * bluetooth
     */
	public static String getMap(int c, int r) {
        StringBuilder sb = new StringBuilder();
		for(int i = 0; i < c; i++) {
			for(int j = 0; j < r; j++){
				sb.append(map[j][i]);
			}
			sb.append("\n");
		}
		String s = sb.toString();
		return s;

	}
    
    //method to output a boolean value based on which axis the robot is facing
    public static boolean axisCheck(int d) {
        if( (d % 2) == 0) {
			return true;  //even = X axis
		} else {
			return false; //odd = Y axis
		}
    }
}