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
	public static int     dist   = 25   ; //Size of robot, used to move one cell
	public static int     degree = 90   ; //Can set the rotation value after calibration
	public static int     length = 10   ; //These need values, for as many cells as there are
	public static int     width  = 5    ; //These need values, for as many cells as there are
	public static boolean right  = false; //variable to define a left/right turn
    


    public static BtStuff btVar = new BtStuff();
    public static DifferentialPilot pilot = new DifferentialPilot(3.22, 19, Motor.B, Motor.C);
    public static OdometryPoseProvider opp = new OdometryPoseProvider(pilot);
	//template main method

	public static void arenaTraversal() {
	
		//Assuming we start at the bottom left hand corner, pointing upwards
		
		for(int i = 0; i < 2; i++) {
		
		moveUp()     ; //For loop will move the robot up to the end
		right = true ; //Turn to the next cell over and move back down
		turn(right); //Where it moves to next cell over and resets the movement
		nextCell()   ;
		turn(right);
		moveDown()   ;
		right = false;
		turn(right);
		nextCell()   ;
		turn(right);
		
		}
		moveUp();
	}
	
	public static void moveUp() {
			
		for(int i = 0; i <= length; i++) {
		
		pilot.travel(dist); //Moves to next cell in the positive Y direction
		
		//Use opp.getPose() to get coordinates of the current location
		//Use RConsole to print to PC
        btVar.poseToRCon(opp.getPose() ); //parameter may not work - check
		
		//Nothing in the cell, set value to 0
		//keeping track of the Y coordinate
		
		//Method to check for obstacles in the next cell called here
		}
	
	}
	public static void moveDown(){
		
		for(int i = length; i >= 0; i--){
		
			pilot.travel(dist);
			
			//Can use opp.getPose() again to get coordinates
            btVar.poseToRCon(opp.getPose() ); //parameter may not work - check
			//Check for obstacles in next cell here
			//Update values as needed
		
		}
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
        ms.updatePosition(); //updates the robot's current position in the mapping system
	}
    
    public static void nextCell(){
	
		pilot.travel(dist);  //Moves one cell over; increments array width value
	}

	public static void calibrate(){
	
		TouchSensor leftBump = new TouchSensor(SensorPort.S2);
		TouchSensor rightBump = new TouchSensor(SensorPort.S1);
		
		System.out.println("Press right bumper to start");
		
		if(rightBump.isPressed() ) {
            pilot.rotate(90);
		}
		System.out.println("Increase degree with R.bumper.  L.Bumper to exit");
		if(rightBump.isPressed() ) {
			degree += 5; //Increases rotation degree if needed
			System.out.println("Reset the robot to straight");
			calibrate();
		}
		if(leftBump.isPressed() ) {	
			return;
		}
	}


}
