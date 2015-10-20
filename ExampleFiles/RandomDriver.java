// RandomDriver.java
//
// A program to test use of the OdometryPoseProvider class.
//
// Simon Parsons
// 6th October 2013
//
// Based on Davide Grossi's PoseProvider code.

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.localization.OdometryPoseProvider;
import java.util.Random;

public class RandomDriver {	
	public static void main(String[] args) throws Exception {
		// Set up the differential pilot for our robot
		DifferentialPilot dp = new DifferentialPilot(3.22, 19, Motor.B, Motor.C);
		// Create a pose provider and link it to the differential pilot
		OdometryPoseProvider opp = new OdometryPoseProvider(dp);

		// Wait to start
		System.out.println("Press any button to start");
		Button.waitForAnyPress();
		LCD.clear();
		System.out.println("Doing my random thing");

		// Seed the random number generator
		Random randomGenerator = new Random();
		// Then make ten random movements
		int move, travel;
		boolean right;
		for(int i=0; i < 10; i++) {			
		    // Generate a number between 0 and 3 and travel or rotate 
                    // accordingly
		    travel = randomGenerator.nextInt(3);
		    if(travel < 2) {
		    	// Generate a number between 10 and 20
		    	move = 10 + randomGenerator.nextInt(11);
		    	dp.travel(move);
		    }
		    else {
		    	// Generate a number between 45 and 90
		    	move = 45 + randomGenerator.nextInt(46);
		    	// Decide which way to turn
		    	right = randomGenerator.nextBoolean();
				if(right){
					dp.rotate(move);
				}
				else{
					dp.rotate(-move);
				}
		    }
		}

		// Clear the LCD and display the final pose again.
		LCD.clear();
		System.out.println("Final pose = " + opp.getPose());
		
		// Wait to be told to exit
		System.out.println("Press any button to exit");
		Button.waitForAnyPress();
	}
}