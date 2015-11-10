public static void movRow() {
    //sonar scan in front of the robot
    objAhead = mapObj.scanAhead();
    objLeft  = mapObj.scanLeft() ;
    objRight = mapObj.scanRight();
    //if obstacle detected:
    if(objAhead) {
        //if there's an obstacle to the left & right.  i.e. a dead end
        if(objLeft && objRight) {
            turnAround(objLeft, mapObj);
        } else {
            //call movAround() method to navigate around the obstacle
            movAround(objLeft, mapObj);
        }
    } else {
        //move forward a cell
        movObj.nextCell(mapObj);
    }
}

public static void turnAround(boolean right, MapSystem ms) {
    
}

public static void moveAround(boolean r, MapSystem ms, Movement mv) {

    mv.turn(r, ms);
    movRow();
    mv.turn(!r, ms);
    movRow();
    movRow();
    mv.turn(!r, ms);
    movRow();
    mv.turn(r, ms);
    
}