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
    

    public static int main(String[] args) {
        
        //import classes here
        public MapSystem mapObj = new MapSystem();
        public BtStuff   btObj  = new BtStuff()  ;
        public Movement  movObj = new Movement() ;

        //for each Column
            //movRow() method
            //movCol() method
        
        return 0;
    }
    
    public static void movRow() {
        //sonar scan in front of the robot
        
        //if object detected:
        //    call movAround() method
        //else
        //    move forward a cell
    }
    
    public static void movCol() {
        //rotate 90 degrees
        //move forward a cell
        //rotate 90 degrees
        //scan / move 2 cells forward
        //scan at the side to see if object is passed
        //move back to correct column if empty
        //    keep moving forward if object is still there
        
    }
    
    public static void movAround() {
        //check for empty adjacent space
        //rotate to face it
        //move forward a cell
        //rotate to face the correct way again
        //move forward 
        
    }
    
    }
//EndOfFile