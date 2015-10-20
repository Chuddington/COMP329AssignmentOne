import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.Delay;

/**
 * Synchornization test
 * @author owner.GLASSEY
 *
 */
public class MotorTutor5 {

     /**
      * @param args
      */
     public static void main(String[] args) {
          LCD.drawString("Progam 5", 0, 0);
          Button.waitForAnyPress();
          LCD.clear();
          int speed = 720;
          Motor.A.setSpeed(speed);
          Motor.B.setSpeed(speed);
          Motor.C.setSpeed(speed);
          Motor.A.rotate(720,true);
          Motor.B.rotate(720,true);
          Motor.C.rotate(720,true);
          for (int i = 0 ; i < 8 ; i++)
          {
               Delay.msDelay(200);
               LCD.drawInt(Motor.A.getTachoCount(), 0,i);
               LCD.drawInt(Motor.B.getTachoCount(), 6,i);
               LCD.drawInt(Motor.A.getTachoCount(), 12,i);
          }
          while(Motor.A.isMoving()|| Motor.B.isMoving()||Motor.C.isMoving());
          Button.waitForAnyPress();
     }