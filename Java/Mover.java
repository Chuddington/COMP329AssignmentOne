/*
 * COMP329 Assignment One
 * File Purpose: To handle movement patterns for the project
 */
 
//import statements here
 
 
public class Mover {
 
    //global variables here - templates for now
    static void    someVarHere   = 0   ;
    static int     someIntVar    = 0   ;
    static String  someStringVar = ""  ;
    static boolean isEmpty       = true;

    static DifferentialPilot dP = new DifferentialPilot();

    Mover(int a, int b, String c) {
        //constructor class
    }
    
    public static void validateArenaSize() {
      Motor.B.forward();
      Motor.C.forward();
    }

    //I'm sure there's more methods to do but I can't think of any
    
    //EndOfFile
 }
