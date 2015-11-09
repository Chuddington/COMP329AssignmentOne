import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;

public class MapSystem {
	
	final int[] wallDist = {175,125};		//wall to wall distance from robots point of view
	final int[] limit = {6,4}; 				//highest coordinates
	final int robotSize = 25;				//size of robot
	
	int[][] map = new int[5][7]; 			//map to be completed
	int[] position = {0,0};					//robots position
	int i = 0;								//counter to be used for position, limit, wallDist
	
	int heading = 1;			//plus or minus 1 depending on which direction the robot is facing
	int direction = 1;			//direction 1-4 of where the robot is facing
	int turned;
	
	UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
	
	us.continuous();

	int dest = us.getDistance();	//distance to destination is distance from sonar
	while (dest > 180) {			//get correct distance (to account for 255 error)
		dest = us.getDistance();			
	}

	/* How the map system recognises a 
	 * right turn
	 */
	void rightTurn() {
		if (direction = 4){		//if max direction
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
		if (direction = 1){
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
	
	/* Updates the specific part of the
	 * map the robot thinks there is an
	 * obstacle. 
	 * Adds 1 where there is an obstacle
	 * and -1 where there isn't.
	 */  
	void updateBlock(int b) {
		
		if (i = 1) {								//if x axis
			map[position[0]][b] = 1;				//update map
			if (heading = 1) {
				for (int j = position[i]; j < b; j++) {		//add in empty spaces up to obstacle
					map[position[1]][j] = -1;
				}
			} else if (heading = -1) {
				for (int j = position[i]; j > b; j--) {
					map[position[1]][j] = -1;
				}
			}
		} else if (i = 0) {								//if y axis
			map[position[0]][point] = 1;
			if (heading = 1) {
				for (int j = position[i]; j < b; j++) {		//add in empty spaces up to obstacle
					map[j][position[1]] = -1;
				}
			} else if (heading = -1) {
				for (int j = position[i]; j > b; j--) {
					map[j][position[1]] = -1;
				}
			}
		}
		
	}
	
	/* Calculates the distance in coordinates
	 * to obstacle, then the actual coordinate
	 * of the obstacle then puts it into the
	 * map using updateBlock().
	 */
	void updateMap() {
		
		double d = dest / robotSize;	//get number of blocks till object
		int x = (int) Math.round(d);	//get interger of blocks till object
		if(x > limit[i]) {				//incase of error
			//do nothing
		} else {
		
			if(heading = -1) {
				x = 1 + x - position[i];
			}
		
			int obstaclePosition = position[i] + ((x+1) * heading);		//position of blockade
			updateBlock(obstaclePosition);
		}
	}	
}