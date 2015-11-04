/* SonarSensor.java
 *
 * Johnathan Edwards 200965323
 * 
 * Basic mapping function. More the base idea than anything practical.
 * More will be added 5/11/15 to make the thing actually functional,
 * will add functionality to track coordinate and better map display.
 * Colour sensor coming soon!
 */


import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;

public class SonarSensor {
	public static void main (String[] args) {
		
		int[][] map = new int[5][7]; 
		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		TouchSensor leftBump = new TouchSensor(SensorPort.S2);
		TouchSensor rightBump = new TouchSensor(SensorPort.S1);
		
		Button.waitForAnyPress();
		
		us.continuous();
		int y = us.getDistance();
		while (y > 180) {
			y = us.getDistance();			
		}
		System.out.println(y);
		
		if (y < 125) {
			
			int x = 125 - y;
			double z = x / 25;
			x = (int) Math.round(z);
			map[4-x][0] = 1;
			System.out.print(x);
			
		}
		
		Button.waitForAnyPress();
		Motor.B.forward();
		Motor.C.forward();
		
		int xCount = 1;
		while(!leftBump.isPressed() && !rightBump.isPressed()) {
			System.out.println(us.getDistance());	
			if(us.getDistance() + 25 * xCount <= y) {
				xCount++;
			}
			if(us.getDistance() < 8){
				Motor.B.stop();
				Motor.C.stop();
				break;
			}
		}
		us.off();
		Motor.B.stop();
		Motor.C.stop();
		
		Button.waitForAnyPress();
		
		us.continuous();
		y = us.getDistance();
		while (y > 180) {
			y = us.getDistance();			
		}
		System.out.println(y);
		
		if (y < 175) {
			
			int x = 175 - y;
			double z = x / 25;
			x = (int) Math.round(z);
			map[4][6-x] = 1;
			System.out.print(x);
			
		}
		
		Button.waitForAnyPress();
		Motor.B.forward();
		Motor.C.forward();
		
		int yCount = 1;
		while(!leftBump.isPressed() && !rightBump.isPressed()) {
			System.out.println(us.getDistance());	
			if(us.getDistance() + 25 * yCount <= y) {
				yCount++;
			}
			if(us.getDistance() < 8){
				Motor.B.stop();
				Motor.C.stop();
				break;
			}
		}
		us.off();
		Motor.B.stop();
		Motor.C.stop();
		
		System.out.println("xCount = " + xCount);
		System.out.println("yCount = " + yCount);
		
		Button.waitForAnyPress();
		
		for(int i = 0; i < 7; i++) {
			System.out.print('\n');
			for(int j = 0; j < 5; j++){
				System.out.print(map[j][i]);
			}
		}
		
		Button.waitForAnyPress();
		
	//	int[][] map = new int[xCount][yCount];
		
		
		
		
	}
}
