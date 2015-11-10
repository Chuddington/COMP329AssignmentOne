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
    static Movement  movObj = new Movement() ; 

    static boolean objAhead = false;
    static boolean objLeft  = false;
    static boolean objRight = false;
    

    public static void main(String[] args) {

        columns = 5;
        numOfRowCells = 7;
        mapObj = new MapSystem(columns, numOfRowCells);
            
        //for each cell in a Row
        for(int loop1 = 0; loop1 < columns; ++loop1) {
            for(int loop2 = 0; loop2 < numOfRowCells; ++loop2) {
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
        movRow();
        //face the correct way to continue the patrol
        mv.turn(r, ms);   
    }
    
    public static void turnAround() {
        //check for empty adjacent space
        //Move to said empty space
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