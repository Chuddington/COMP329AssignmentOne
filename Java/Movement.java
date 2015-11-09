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
	public int  dist   = 25; //Size of robot, used to move one cell
	public int  degree = 90; //Can set the rotation value after calibration
	public int  length = 10; //These need values, for as many cells as there are
	public int  width  = 5 ; //These need values, for as many cells as there are
	public bool right      ; //variable to define a left/right turn
  public BluetoothStuff btVar = new BluetoothStuff();
	
	//template main method
	public static void main(String[] args) {
	
		DifferentialPilot pilot = new DifferentialPilot(3.22, 19, Motor.B, Motor.C);
		OdometryPoseProvider opp = new OdometryPoseProvider(pilot);
		calibrate();
		arenaTraversal();	
	
	}

	public void arenaTraversal() {
	
		//Assuming we start at the bottom left hand corner, pointing upwards
		
		for(int i = 0; i < 2; i++) {
		
		moveUp()     ; //For loop will move the robot up to the end
		right = true ; //Turn to the next cell over and move back down
		rotate(right); //Where it moves to next cell over and resets the movement
		nextCell()   ;
		rotate(right);
		moveDown()   ;
		right = false;
		rotate(right);
		nextCell()   ;
		rotate(right);
		
		}
		moveUp();
	}
	
	public void moveUp() {
			
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
	public void moveDown(){
		
		for(int i = length; i >= 0; i--){
		
			pilot.travel(dist);
			
			//Can use opp.getPose() again to get coordinates
            btVar.poseToRCon(opp.getPose() ); //parameter may not work - check
			//Check for obstacles in next cell here
			//Update values as needed
		
		}
	}

  //As to not confuse with the pilot.rotate method
	public void turn(bool right){	
	
		if(right == true) {
			pilot.rotate(degree) ; //Turns right
		}
		else {
			pilot.rotate(-degree); //Turn left		
		}	
	}

	public void nextCell(){
	
		pilot.travel(dist); //Moves one cell over; increments array width value
			
	}

	public void calibrate(){
	
		TouchSensor leftBump = new TouchSensor(SensorPort.S2);
		TouchSensor rightBump = new TouchSensor(SensorPort.S1);
		
		System.out.println("Press right bumper to start");
		
		if(rightBump.isPressed() ) {
      rotate(90);
		}
		System.out.println("Increase degree with R.bumper.  L.Bumper to exit");
		if(rightBump.isPressed() ) {
			degree + 5; //Increases rotation degree if needed
			System.out.println("Reset the robot to straight");
			calibrate();
		}
		if(leftBump.isPressed() ) {	
			return;
		}
	}


}
