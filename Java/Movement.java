// Movement class
//
// The concept of it all is very basic, but I'm gonna go to labs 
//and have a play with it tomorrow, see if I can get it to work
//
// Simple program to move around the arena. 
// Parameters need working out though, just the size of the arena really
//
//


import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.localization.OdometryPoseProvider;

public class Movement {
	
	//Spec says the robot is ~25cm long and 20cm wider, so each cell will be 25x20
	//will measure the size of the arena to make making the grid easier
	
	public int dist = 25;	//Size of the robot, uses in the travel method to move one cell
	public int degree = 90;	//Can set the rotation value after calibration
	public int length, width;	//These need values, for as many cells as there are
	public static int x, y;
	public bool right;
	public int columns; 	//Will be set once pairs of movement are measured 
	public int[][] arena = new int[10][10] //However big the arena is
	
	public static void main(String[] args){
	
		DifferentialPilot pilot = new DifferentialPilot(3.22, 19, Motor.B, Motor.C);
		OdometryPoseProvider opp = new OdometryPoseProvider(pilot);
		calibrate();
		arenaTraversal();	
	
	}
	public void arenaTraversal(){
	
		//Assuming we start at the bottom left hand corner, pointing upwards
		
		for(int i = 0; i < columns; i++){
		
		moveUp();								//For loop will move the robot up to the end
		right = true;							//Turn to the next cell over and move back down
		rotate(right);							//Where it moves to next cell over and resets the movement
		nextCell();
		rotate(right);
		moveDown();
		right = false;
		rotate(right);
		nextCell();
		rotate(right);
		
		}
	
	}
	
	public void moveUp(){
			
		for(int i = 0; i <= length; i++){
		
		pilot.travel(dist); //Moves to next cell in the positive Y direction
		
		//Use opp.getPose() to get coordinates of the current location
		//Use RConsole to print to PC
		
		arena[x][i] = 0;		//Nothing in the cell, set value to 0
		y = i;					//keeping track of the Y coordinate
		
		//Method to check for obstacles in the next cell called here
		}
	
	}
	public void moveDown(){
		
		for(int i = length; i >= 0; i--){
		
			pilot.travel(dist);
			
			//Can use opp.getPose() again to get coordinates
			//Check for obstacles in next cell here
			
			arena[x][i];
			y = i;
		
		}
	}
	public void turn(bool right){			//As to not confuse with the pilot.rotate method
	
		if(right == true){
		
			pilot.rotate(degree);			//Turns right
		
		}
		else{
		
			pilot.rotate(-degree);			//Turn left
		
		}
	
	}
	public void nextCell(){
	
		pilot.travel(dist);		//Moves one cell over, used for incrementing the width value of the array
		x ++;
			
	}
	public void calibrate(){
	
		TouchSensor leftBump = new TouchSensor(SensorPort.S2);
		TouchSensor rightBump = new TouchSensor(SensorPort.S1);
		
		System.out.println("Press right bumper to start");
		
		if(rightBump.isPressed()){
		rotate(90);
		}
		System.out.println("Increase degree with right bumper. Left Bumper to exit");
		if(rightBump.isPressed()){
		
			degree + 5;							//Increases rotation degree if needed
			System.out.println("Reset the robot to straight");
			calibrate();
			}
		if(leftBump.isPressed)){
		
			return;
		
		}
		
	
	
	
	
	}
	}
