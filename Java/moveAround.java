//turn is left or right turn
//scan is scan ahead
//move is move forward

//turn around is turn 180*

int extraMove = 0;

moveAround() {
	int stored = position[i];
	int count = 0;
	
	turn
	scan
	if (block) {
		extraMove++;
		diagCount++;
		moveAround():
	} else {
		move
	}
	
	turn
	scan
	if (block) {
		moveAround();
		turn
	} else {    
		move
	}
	
	if (position[i] != stored) {
		scan
		if block
			count++;
			moveAround();
		else
			move
	
		if (position[i] != stored) {
			turn
			scan
			if (block) {
				moveAround();
			} else { 
				move * extraMove
			}
		}
	}
	
	
	
	
	turn
	
	//done
}

finishMoveAround() {
	for(extraMove; extraMove > 0; extraMove--) {
			move;
	}
	
		
	if(count > 0) {
		turn around
		scan
		count--;
		if (scan == empty)
			while(count < 0) {
				move
				scan
				count--;			
		}
		turn around
	}
	
}
