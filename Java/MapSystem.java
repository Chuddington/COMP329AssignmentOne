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
	
	int one = 1;		//
	int direction = 1;
	int turned;
	int heading = 1;
	
	UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
	
	us.continuous();

	int dest = us.getDistance();
	while (dest > 180) {
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
			move = 1;			
		} else  {
			move = -1;
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
			move = 1;
		} else  {
			move = -1;
		}
		
		if(turned) {
			i = 1;		
		} else {
			i = 0;
		}
	}
	
	/* Adds a 1 if detects an obstacle
	 * ahead and a -1 if empty.
	 */
	void updateMap() {
		
		double d = dest / robotSize;	//get number of blocks till object
		int x = (int) Math.round(d);	//get interger of blocks till object
		if(x > limit[i]) {				//incase of error
			x = limit[i];
		}
		
		int point = position[i] + ((x+1) * move);		//position of blockade
		
		if (i = 1) {									//if x axis
			map[point][position[1]] = 1;				//update map
			if (heading = 1) {
				for (j = position[i]; j < point; j++) {
					map[j][position[1]] = -1;
				}
			} else if (heading = -1) {
				for (j = position[i]; j > point; j--) {
					map[j][position[1]] = -1;
				}
			}
		} else if (i = 0) {								//if y axis
			map[position[0]][point] = 1;
			if (heading = 1) {
				for (j = position[i]; j < point; j++) {
					map[j][position[1]] = -1;
				}
			} else if (heading = -1) {
				for (j = position[i]; j > point; j--) {
					map[j][position[1]] = -1;
				}
			}
		}
	}	
}