// SimpleSensor.java
//
// A program to illustrate the use of the NXT Touch Sensor.
//
// Simon Parsons
// 26th September 2013

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;

public class SimpleSensor{
	public static void main(String[] args){

		TouchSensor leftBump = new TouchSensor(SensorPort.S2);
		TouchSensor rightBump = new TouchSensor(SensorPort.S1);
	    
		System.out.println("Press the left bumper to start");
		System.out.println("Press the right bumper to stop");
		System.out.println("Press any orange button to quit");

		while (!Button.ENTER.isDown()){
			if(leftBump.isPressed()) {
				Motor.B.forward();
				Motor.C.forward();
			}
			if(rightBump.isPressed()){
				Motor.B.stop();
				Motor.C.stop();
			}
    	}
    }
}