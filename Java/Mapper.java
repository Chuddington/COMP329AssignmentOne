/*
 * COMP329 Assignment One
 * File Purpose: To handle environmental input for the project
 */
 
//import statements here
 
 
public class Mapper {
 
    //global variables here - templates for now
    static void    someVarHere   = 0   ;
    static int     someIntVar    = 0   ;
    static String  someStringVar = ""  ;
    static boolean isEmpty       = true;

    Mapper(int a, int b, String c) {
        //constructor class
    }
    
    public static void SonarObstacle(SonarObj sV, char direction, double str) {
        //scan an object to obtain the rough 'size' of the object
        //can then use other methods to provide evasive maneuvers
        
    }
    
    public static void afterCollision(bumperPressed bp) {
        //after bumpers have picked up a collision - possibly reverse
        //then sonar the obstacle?  Switch case to control which way to turn
        //or move after the collision too?
        
    }
    
    public static void editMap(location[] l, location m, boolean isEmpty) {
        //method to add/edit the location 'l' (can be current or in front etc.)
        //used to generate the map of the environment
        
    }
    
    public static void uploadMap(location[] l) {
        //should upload via BlueTooth the area's map.  Will be used near the
        //end of the program
        
    }

    //I'm sure there's more methods to do but I can't think of any
    
    //EndOfFile
 }