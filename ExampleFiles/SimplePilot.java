//
// Demonstrator of the use of the DifferentialPilot class.
//
// Simon Parsons
// 5th October 2013
//
// Based on Davide Grossi's SquareTracer program.

// Import all the basic lejos.nxt library, plus the DifferentialPilot
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

// Define the SimplePilot class
public class SimplePilot{
	// Include a DifferentialPilot object
    DifferentialPilot pilot ; 
    
    // Here's the main part of the program, a function that repeats two calls
    // to the pilot.
    public void  drawSquare(float length){   
        for(int i = 0; i<4 ; i++){
            pilot.travel(length);         // Drive forward
            pilot.rotate(90);             // Turn 90 degrees    
        }
    }
    
    public static void main(String[] args){
    	// Print a message on the screen and wait for button press
    	LCD.drawString("SimplePilot", 1, 1); 
        Button.waitForAnyPress(); 
        LCD.clear();
        // Create a SimplePilot and instantiate its member pilot
        SimplePilot sp = new SimplePilot(); 
        sp.pilot = new DifferentialPilot(3.25, 19, Motor.B, Motor.C);
        // Call drawSquare with the length of the side.
        sp.drawSquare(40);
    }
}