import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;

public class MapSystem {
	
	final int[] wallDist = {175,125};		//wall to wall distance from robots point of view
	final int[] limit = {6,4}; 				//highest coordinates
	final int robotSize = 25;				//size of robot
	
	static int[][] map;                 	//map to be completed
	static int[] position = {0,0};			//robots position
	static int i = 0;						//counter to be used for position, limit, wallDist

	static int heading = 1;			    //plus or minus 1 depending on which direction the robot is facing
	static int direction = 1;			//direction 1-4 of where the robot is facing
	
    //variables added to compile successfully
    static boolean turned = false;
    static int dest;
    static int totalCells;
    static int unknownObjs = 4;
    
    
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
    
    
    MapSystem(int c, int r) {
        map = new int[c][r]; 			//map to be completed
        totalCells = (c * r);
        us.continuous();

        int dest = us.getDistance();	//distance to destination is distance from sonar
        while (dest > 180) {			//get correct distance (to account for 255 error)
            dest = us.getDistance();			
        }
    }
    
    public static int basicProb() {
        int x = ( (unknownObjs / totalCells) * 100);
        
        --totalCells;
        return x;
    }

    public static boolean scanAhead() {
        Motor.A.rotateTo(0);
        dest = us.getDistance();
        if(dest < 30) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean scanLeft() {
        Motor.A.rotateTo(-650);
        dest = us.getDistance();
        if(dest < 30) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean scanRight() {
        Motor.A.rotateTo(650);
        dest = us.getDistance();
        if(dest < 30) {
            return true;
        } else {
            return false;
    }
    }
        
    public static void updatePosition() {
        if(heading = -1) {
			position[i]--;
		} else {
			position[i]++;
		}
    }
    
    
	/* How the map system recognises a 
	 * right turn
	 */
	void rightTurn() {
		if (direction == 4){		//if max direction
			direction = 1;		//reset
		} else {
			direction++;		//change direction
		}
		
		if (direction < 3) {	//if facing forward
			heading = 1;			
		} else  {
			heading = -1;
		}
		
		turned = !turned;
		
		if(turned) {
			i = 1;		//y is 0 and x is 1
		} else {
			i = 0;
		}
	}
	
	/* How the map system recognises a 
	 * right turn
	 */
	void leftTurn() {
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
		
		if(turned) {
			i = 1;		
		} else {
			i = 0;
		}
	}
	
	/* Calculates the distance in coordinates
	 * to obstacle, then the actual coordinate
	 * of the obstacle then puts it into the
	 * map using updateBlock().
	 */
	void updateMap(int dest) {
	
        if (dest < robotSize) {
        
            if (i = 1) {											//if x axis
                map[position[0]][position[1] + heading] = 1;		//update map
            } else if (i = 0) {										//if y axis
                map[position[0] + heading][position[1]] = 1;
            }	
            
        } else {
            
            if (i = 1) {											//if x axis
                map[position[0]][position[1] + heading] = -1;		//update map
            } else if (i = 0) {										//if y axis
                map[position[0] + heading][position[1]] = -1;
            }
            
        }
    }

    void printMap(int c, int r) {
        for(int i = 0; i < c; i++) {
			System.out.print('\n');
			for(int j = 0; j < r; j++){
				System.out.print(map[j][i]);
			}
		}   
    }
}