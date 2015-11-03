import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class BluetoothStuff {

  //global variables
  static int btPin = 4319;
  
  //Method to start a Bluetooth Connection to nxjconsoleviewer.bat
  //nxjconsoleviewer should be open and ready to connect when this method is
  //called.
  public static void startBtConn() {
    LCD.drawString("Waiting for \nRConsole \nConnection", 0, 1);
    RConsole.openBluetooth(0); //wait indefinitely for a BT connection
    //test output: sends the BT PIN to the RConsole
    RConsole.println("RConsole Connected; PIN: " + Integer.toString(btPin) );
  }

  //method to close the RConsole connection to the Computer
  public static void closeBtConn() {
    LCD.drawString("Closing BT Connection", 0, 1);
    RConsole.println("Closing BT Connection");
    RConsole.close();
  }

  //method to print a String input to the RConsole
  public static void stringToRCon(String s) {
    RConsole.println(s);
  }

  //method to print a Pose input to the RConsole
  public static void poseToRCon(Pose p) {
    RConsole.println(Pose.toString(p) );
  }

}
