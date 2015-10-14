/*
 * COMP329 Assignment One
 * File Purpose: To start the program and link with other files
 */
 
//import statements here
 
 
public class AssOneMain {
 
    //global variables here - templates for now
    static void    someVarHere   = 0   ;
    static int     someIntVar    = 0   ;
    static String  someStringVar = ""  ;
    static Boolean isEmpty       = true;

    public static int main(String[] args) {
    
        //calibrationClass methods here
        //can be commented out later for the final program
        //purely theoretical at this stage
        public Calibrate calObj = new Calibrate(someVarHere);
        /*
        calObj.chkForwardMovement();
        calObj.chkTurningCircles();
        calObj.chkSonar();
        calObj.chkLightTrail();
        */
        
        //set up a way why follows the robotic cycle of thinking
        //mapping, moving, etc.
        
        public Mapper mapObj = new Mapper(someVarHere);
        public Mover  movObj = new Mover (someVarHere);
        
        /* Examples of stuff
        isEmpty = mapObj.SonarObstacle();
        mapObj.addToWorld(cellInFrontX, cellInFrontY, isEmpty)
        movObj.Cell(someStringVar = "ForwardCell");
        */
        //EndOfProgram
        return 0;
    }
    
    //EndOfFile
 }