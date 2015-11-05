int i;				//x or y for position[].
int move = 1;		//move +1 or -1 on map.
direction = 1;		//direction robot is facing. 1 is ^, 2 is ->, 3 is v, 4 is <- .
turned = false;		//turned changes between movement of x and y on map.

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


