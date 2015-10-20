// SimpleDriver.java
//
// A program to illustrate the use of the NXT Motors.
//
// Simon Parsons
// 26th September 2013

// In addition to the Button class, we need the Motor class
// and the LCD class. The latter allows us to do more than
// just print on the screen.
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

// On button presses, start and then stop the motors.
public class SimpleDriver{
	public static void main(String[] args){
		System.out.println("Press any button to start robot");
	    Button.waitForAnyPress();
		LCD.clear();
		Motor.B.forward();
		Motor.C.forward();
		System.out.println("Press any button to stop robot");
		Button.waitForAnyPress();
		Motor.B.stop();
		Motor.C.stop();
    	}
}

