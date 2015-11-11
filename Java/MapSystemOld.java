import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.*;

public class MapSystem {
	
	final static int robotSize = 25;		//size of robot
	
   
	static int[][] map;                 	//map to be completed
    static int[] limit; 		//highest coordinates
	static int[] position = {0,0};			//robots position
	static int i = 1;						//counter to be used for position, limit

	static int heading = 1;			        //plus or minus 1 depending on which direction the robot is facing
	static int direction = 1;			    //direction 1-4 of where the robot is facing
	
    //variables added to compile successfully
    static int dest;                        //distance to object ahead
    static int totalCells;                  //number of cells
    static int unknownObjs = 4;             //number of obstacles not found
    static int lastTurn = 0;                //the direction the head was last turned
    
    
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
    
    //Constructor
    MapSystem(int c, int r) {
        map = new int[c][r]; 			//map to be completed
        limit = new int[2];
        limit[0] = (c - 1);
        limit[1] = (r - 1);
        totalCells = (c * r);
        us.continuous();
    }
    
    /* Works out the probability of the cell
     * ahead being occupied.
     */
    public static double basicProb() {
        double x = ( (unknownObjs / totalCells) * 100);
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
        position[i] = position[i] + heading;
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
		
		if (direction < 3) {	    //if facing forward
			heading = 1;			
		} else  {
			heading = -1;
		}
		
		if(i == 0) {
			i = 1;		            //y axis is 0 and x axis is 1
		} else {
			i = 0;
		}
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
		
		if (direction < 3) {
			heading = 1;
		} else  {
			heading = -1;
		}
		
		if(i == 0) {
			i = 1;		
		} else {
			i = 0;
		}
	}
	
	/* Decides whether there is an obstacle
     * or an empty space ahead then adds it
     * to the map.
	 */
	public static void updateMap() {
        	
        if((position[i] + heading) > limit[i] || (position[i] + heading) < 0) {     //if cell ahead is a wall
             //do nothing
        } else if (dest < robotSize) {                              //if cell ahead is occupied
        
            if (i == 1) {											//if x axis
                map[position[0]][position[1] + heading] = 1;		//mark obstacle on map
            } else if (i == 0) {									//if y axis
                map[position[0] + heading][position[1]] = 1;        
            }
            unknownObjs--;
            totalCells--;
            
        } else {
            
            if (i == 1) {											//if x axis
                map[position[0]][position[1] + heading] = 2;		    //mark empty space on map
            } else if (i == 0) {									//if y axis
                map[position[0] + heading][position[1]] = 2;
            }
            totalCells--;
        }
    }

	/* Prints current map to lcd 
     * screen.
     */
    public static void printMap(int c, int r) {
        LCD.clear();
        for(int i = 0; i < r; i++) {
			for(int j = 0; j < c; j++){
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
}