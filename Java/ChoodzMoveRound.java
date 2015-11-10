public static void movRow() {
    //sonar scan in front of the robot
    boolean objAhead = mapObj.scanAhead();
    boolean objLeft  = mapObj.scanLeft() ;
    boolean objRight = mapObj.scanRight();
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