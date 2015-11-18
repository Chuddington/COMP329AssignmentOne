// Movement class
//
// 
//
// Simple program to move around the arena. 
// Assuming 10x 5 arena size 
// Includes a small calibration method for the rotation
//


import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.localization.OdometryPoseProvider;

public class Movement {
	
	//Spec says the robot is ~25cm long and 20cm wider, so each cell will be 
  //25x20.  Will measure the size of the arena to make making the grid easier
	
  //global variables
	public static int     dist    = 0   ; //Size of cells, used to move one cell
	public static int     degree  = 107  ; //Can set the rotation value after calibration
	public static int     length  = 10   ; //These need values, for as many cells as there are
	public static int     width   = 5    ; //These need values, for as many cells as there are
	public static boolean right   = false; //variable to define a left/right turn
    public static int     columns = 0    ;
    public static int     numOfRows = 0;
    


    public static BtStuff btVar = new BtStuff();
    public static DifferentialPilot pilot = new DifferentialPilot(3.22, 19, Motor.B, Motor.C);
    public static OdometryPoseProvider opp = new OdometryPoseProvider(pilot);

    Movement(int d, int c, int r) {
        columns = c;
        numOfRows = r;
        dist = d;
    } 
    
    //As to not confuse with the pilot.rotate method
	public static void turn(boolean right, MapSystem ms){	
	
		if(right == true) {
			pilot.rotate(degree) ; //Turns right
            ms.rightTurn()       ; //tells mapping system a right turn has happened
		}
		else {
			pilot.rotate(-degree); //Turn left		
            ms.leftTurn()        ; //tells mapping system a left turn has happened
		}	
	}
    
    public static void turn(MapSystem ms) {
        pilot.rotate(degree) ; //Turns right
        ms.rightTurn()       ; //tells mapping system a right turn has happened
        pilot.rotate(degree) ; //Turns right
        ms.rightTurn()       ; //tells mapping system a right turn has happened
    }
    
    public static void turn(boolean r) {
        if(right == true) {
			pilot.rotate(degree) ; //Turns right
		}
		else {
			pilot.rotate(-degree); //Turn left		
		}
    }
    

	public static void nextCell(MapSystem ms){
	
		pilot.travel(dist);  //Moves one cell over; increments array width value
        //btVar.poseToRCon(opp.getPose() ); //parameter may not work - check
        ms.updatePosition(); //updates the robot's current position in the mapping system
       // ms.printMap(columns, numOfRows);
	}
    
    public static void nextCell(){
	
		pilot.travel(dist);  //Moves one cell over; increments array width value
	}

    //Method to run the robot in a square
    //tests the rotation and movement of the robot
    //Allowing variables to be corrected before running full program
    
	public static void calibrate(){
	
		pilot.travel(dist);
        pilot.rotate(degree);
        pilot.travel(dist);
        pilot.rotate(degree);
        pilot.travel(dist);
        pilot.rotate(degree);
        pilot.travel(dist);
        pilot.rotate(degree);
		
	}


}
