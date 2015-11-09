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
            //moveRow() method
            //movCol
        
        return 0;
    }
    
    public static void movRow() {
        //sonar scan in front of the robot
        
        //if empty move forward; if not, call movAround() method
    }
    
    public static void movAround() {
        
        
    }
    
    }
//EndOfFile