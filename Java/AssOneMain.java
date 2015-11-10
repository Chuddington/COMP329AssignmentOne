/*
 * COMP329 Assignment One
 * File Purpose: To start the program and link with other files
 */
 
//import statements here
import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.nxt.Button;
import lejos.nxt.Motor;
 
 
public class AssOneMain {
 
    //global variables here - templates for now
    static int columns, numOfRowCells = 0;   
    static MapSystem mapObj                  ;
    static BtStuff   btObj  = new BtStuff()  ;
    static Movement  movObj;

    static boolean objAhead = false;
    static boolean objLeft  = false;
    static boolean objRight = false;
    

    public static void main(String[] args) {
        //grid values for mapping
        columns = 5;
        numOfRowCells = 7;
        
        mapObj = new MapSystem(columns, numOfRowCells);
        movObj = new Movement(columns, numOfRowCells); 
        btObj.startBtConn();
            
        //for each cell in a Row
        for(int loop1 = 0; loop1 < columns; loop1++) {
            for(int loop2 = 0; loop2 < numOfRowCells; loop2++) {
                movRow();
            }
            if(loop1 % 2 == 0) {
                movCol(true);
            } else {
                movCol(false);
            }
        }
    }
    
    public static void movRow() {
        //work out probability
        double nextCell = mapObj.basicProb();
        btObj.stringToRCon("Object Probability in next Cell: " + nextCell);
        //sonar scan in front of the robot
        objLeft  = mapObj.scanLeft() ;
        objAhead = mapObj.scanAhead();
        objRight = mapObj.scanRight();
        
       // mapObj.printMap(columns, numOfRowCells);
        
        //if obstacle detected:
        if(objAhead) {
            //if there's an obstacle to the left & right.  i.e. a dead end
            if(objLeft && objRight) {
                turnAround(objLeft, mapObj, movObj);
            } else {
                //call movAround() method to navigate around the obstacle
                movAround(objLeft, mapObj, movObj);
            }
        } else {
            //move forward a cell
            movObj.nextCell(mapObj);
        }
    }
    
    public static void movCol(boolean b) {
        //rotate 90 degrees to the right
        movObj.turn(b, mapObj);
        //move forward a cell
        movRow();
        //rotate 90 degrees to the right
        movObj.turn(b, mapObj);
        
    }
    
    //Method to move around a single obstacle - runs when obstacle is detected ahead but not in a dead end
    public static void movAround(boolean r, MapSystem ms, Movement mv) {
        //turn to the right if true (if an obstacle is on the left, for example)
        mv.turn(r, ms);
        //scan and move forward if necessary
        movRow();
        //invert the boolean to turn the other way
        mv.turn(!r, ms);
        //move forward twice to pass the obstacle
        movRow();
        movRow();
        //turn and move to the correct column the robot should be in
        mv.turn(!r, ms);
        boolean extend = mapObj.scanAhead();
        while(extend) {
            mv.turn(r, ms);
            movRow();
            mv.turn(!r, ms);
        }
        movRow();
        //face the correct way to continue the patrol
        mv.turn(r, ms);   
    }
    
    //method to run when the robot is in a dead end and has to back up and go around
    public static void turnAround(boolean r, MapSystem ms, Movement mv) {
        //turn around and move back a square
        mv.turn(ms);
        movRow();
        //check for empty adjacent space
        objLeft  = mapObj.scanLeft() ;
        objRight = mapObj.scanRight();
        //Move to said empty space
        if(objLeft && !objRight) { //Obstacle on the Left
            mv.turn(objLeft, ms);
            movRow();
        } else if(objRight && !objLeft) { //Obstacle on the Right
            mv.turn(objRight, ms);
            movRow();
        } else { //in a corridor
            movRow();
        }
        
        //move forward 2 cells, scanning as we go
        //if an obstacle is still on the same axis as the first
        //    class it as a different obstacle
        //    move forward a cell
        //    scan again - recursive
        //move back to the correct column
        
        //if at a 'limit cell', turn left instead of right
        
    }
    
    }
//EndOfFile