int dest;    //sonar reading
int position[] = {0,0}    //robots position
final int[] wallDist = {175,125};     //distance wall to wall -25(robot size) for practical use
final int[] limit = {5,7};    //map limits (may be incorrect)
int robotSize = 25;     //size of robot (may be incorrect)
int move;     //1 or -1, direction robot moves 


if (move = 1 && dest < wallDist[i] - (position[i] * robotSize)) {		//if facing forward and sonar reading is less than distance to wall from robots position.
	updateMap();
} else if (move = -1 && dest < wallDist[i] - ((1 + limit[i] - position[i]) * robotSize)) {	//if facing back and " " " " ... with position changed so distance is correct.
	updateMap();
}

void updateMap() {
	
	double d = dest / robotSize;	//get number of blocks till object
	int x = (int) Math.round(d);	//get interger of blocks till object
	if(x > limit[i]) {				//incase of error
		x = limit[i];
	}
	
	int point = position[i] + ((x+1) * move);		//position of blockade
	
	if (i < 1) {									//if x axis
		map[point][position[i+1]] = 1;				//update map
	}
	if (i > 0) {									//if y axis
		map[position[i-1]][point] = 1;
	}
}
