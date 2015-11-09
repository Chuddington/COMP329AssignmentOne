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

    public static int main(String[] args) {
        
        //import classes here
        public MapSystem mapObj = new MapSystem();
        public BtStuff   btObj  = new BtStuff()  ;
        public Movement  movObj = new Movement() ;

        
        //for each cell in a Row
        for(columns) {
            for(numOfRowCells) {
                movRow();
            }
            movCol();
        }
        return 0;
    }
    
    public static void movRow() {
        //sonar scan in front of the robot
        boolean objAhead = mapObj.scanAhead();
        //if obstacle detected:
        if(objAhead) {
            //call movAround() method to navigate around the obstacle
            movAround();
        } else {
            //move forward a cell
            movObj.nextCell(movObj);
        }
    }
    
    public static void movCol() {
        //rotate 90 degrees to the right
        movObj.turn(true, mapObj);
        //move forward a cell
        movRow();
        //rotate 90 degrees to the right
        movObj.turn(true, mapObj);
        
    }
    
    public static void movAround() {
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