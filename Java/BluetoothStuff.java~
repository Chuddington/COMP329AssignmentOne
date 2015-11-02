import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.pc.tools;

public class BluetoothStuff {

  static int btPin = 4319;

  public static void main(String[] args) {

    LCD.drawString("right BT",0, 0);
    NXTConnection connection = null;

      LCD.drawString("waiting for BT", 0,1 );
      RConsole.open();
      RConsole.openBluetooth(10000);
      connection = Bluetooth.waitForConnection();

    DataOutputStream dataOut = connection.openDataOutputStream();

    try {
      dataOut.writeInt(btPin);
    } catch (IOException e ) {
      System.out.println(" write error "+e); 
    }

  }

}
