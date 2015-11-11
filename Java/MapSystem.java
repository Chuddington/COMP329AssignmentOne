import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.*;

public class MapSystem {
	
	final static int robotSize = 25;		//size of robot
	
   
	static int[][] map;                 	//map to be completed
    static int[][] C;                       //number of times a cell had been scanned
    static double[][] P;                       //probability of a cell being occupied
    static int[] limit; 		//highest coordinates
	static int[] position = {0,0};			//robots position
	static int i = 1;						//counter to be used for position, limit
    static int obstaclePosition;

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
        P   = new double[c][r]; 			//map to be completed
        C   = new int[c][r]; 			//map to be completed
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
	
	/* Updates the specific part of the
	 * map the robot thinks there is an
	 * obstacle. 
	 * Adds 1 where there is an obstacle
	 * and -1 where there isn't.
	 */  
	public static void updateBlock(int b) {
		
		if (i == 1) {								//if x axis
			if (obstaclePosition > 0 && obstaclePosition <= limit[i]) {
                map[position[0]][b]++;				//update map
                C[position[0]][b]++;
                occupancyGrid(position[0], b);
            }
            
			if (heading == 1) {
				for (int j = position[i]; j < b; j++) {		//add in empty spaces up to obstacle
					map[position[0]][j]--;
                    C[position[0]][j]++;
                    occupancyGrid(position[0], j);
				}
			} else if (heading == -1) {
				for (int j = position[i]; j > b; j--) {
					map[position[1]][j]--;
                    C[position[1]][j]++;
                    occupancyGrid(position[0], j);
				}
			}
		} else if (i == 0) {								//if y axis
            if (obstaclePosition > 0 && obstaclePosition <= limit[i]) {
                map[b][position[1]]++;				//update map
                C[b][position[1]]++;
                occupancyGrid(b, position[1]);
            }
            
			if (heading == 1) {
				for (int j = position[i]; j < b; j++) {		//add in empty spaces up to obstacle
					map[j][position[1]]--;
                    C[j][position[1]]++;
                    occupancyGrid(j, position[1]);
				}
			} else if (heading == -1) {
				for (int j = position[i]; j > b; j--) {
					map[j][position[1]]--;
                     occupancyGrid(j, position[1]);
				}
			}
		}
		
	}
	
    public static void occupancyGrid(int x, int y) {
        P[x][y] = ( (double)map[x][y] + (double)C[x][y]) / (2.0 * (double)C[x][y]);    
    }
    
	/* Calculates the distance in coordinates
	 * to obstacle, then the actual coordinate
	 * of the obstacle then puts it into the
	 * map using updateBlock().
	 */
	public static void updateMap() {

		double d = dest / robotSize;	//get number of blocks till object
		int x = (int) Math.round(d);	//get interger of blocks till object
        obstaclePosition = position[i] + ((x+1) * heading);
		if(obstaclePosition > limit[i]) {				//incase of error
			obstaclePosition = limit[i] + 1;
		} else if (obstaclePosition < 0) {
            obstaclePosition = -1;           
        }
        updateBlock(obstaclePosition);
	}

	/* Prints current map to lcd 
     * screen.
     */
    public static void printMap(int c, int r) {
        LCD.clear();
        for(int i = 0; i < r; i++) {
			for(int j = 0; j < c; j++){
				if (P[j][i] > 0.5) {
                    System.out.print("1");
                } else if (P[j][i] < 0.5) {
                    System.out.print("0");                    
                } else {
                    System.out.print("-");
                }
			}
			System.out.print('\n');
		}   
    }
	
	/* Puts map into a string for 
     * bluetooth
     */
	public static String getMap(int c, int r) {
        StringBuilder sb = new StringBuilder();
		for(int i = 0; i < r; i++) {
			for(int j = 0; j < c; j++){
                sb.append(P[j][i]);
			}
			sb.append("\n");
		}
		String s = sb.toString();
		return s;

	}
}
