# COMP329AssignmentOne
2015 Robotics Assignment - Basics of Environment Mapping and Exploration

## Obtaining the Project from the University Computers
Variables will be denoted with hyphens '-Variable-'

1. Open command prompt; cd /D -WhereYouWantTheFolderToDownloadTo-

2. If you haven't already, initialise your settings on the uni computer

    * git config --global user.name "-YourUserName-"
    
    * git config --global user.email "-YourEmailAddress-"
    
3. git clone https://github.com/Chuddington/COMP329AssignmentOne

## Uploading changes to the Project from the University Computers
Once you've finished editing files and wish to commit them, open command prompt:

1. 'cd' into the COMP329AssignmentOne folder that was created when you cloned the project

2. git add .

3. git commit -m "-CommentOfTheChangesMade-"

4. git push

    1. There will probably be informative output about the way in which 'push' works
    
    2. It will ask you to type in your github username/email address
    
    3. It will ask you to type in the password associated with the username/email
    
    4. if done correctly, the changes will be uploaded

## Editing/Obtaining the project whilst at home
If you're running a windows OS or Mac OS X, obtaining the [GitHub Desktop Application](https://desktop.github.com/) is probably the best way of accessing the project:

You may have to add an SSH key to your account as a security measure - GitHub will tell you if this is the case and how you can go about doing it.

If you have a computer running a form of GNU/Linux or BSD, you probably know what you are doing and don't need to read this.

At home you can use the desktop application or the terminal commands, whichever is most convenient for you.  At University though you will need to use the terminal commands so it may be best if you learn how to correctly perform changes to the project at home so stuff gets done.

## Uploading Changes in General
Please make sure that whenever you are about to commit and push your changes, you have the most recent version of the project.  If this is not the case anhad you push changes, I do not entirely know what will happen but I think it creates a separate branch, which I will have to try and merge without conflicts.

## Lecture Log

### 2015-10-20

- Basic idea is that the robot travels along the X axis to find out the width of the arena, then the Y axis to acquire the length.  Once this happens, the robot should be at the opposite corner from which it had started and have an idea about the size of the world it is in.

- Possible way to obtain the size of the robot through the calibration method could be to get the robot to run onto coloured card until it picks up the colour, then have it turn 180 degrees, start the Odometry functions and move forward until it no longer senses the coloured card.

- Using the size of the world and the size of itself, the robot can then model the world into cells.  This is by having each cell's size the same as the robot and can be modelled in a 2D array.  As an example, the robot thinks the area is 4 robots x 6 robots in size, so a 4x6 2D array is created.

- I'm not entirely sure how the assignment wishes the final map to look like.  It may be as complex as an image file or simply a text file.  In any case, we will have to come up with an idea during next week's lab session.

- From what I've been told, we need to create a program for the desktop which will accept the robot's map when we transfer the data via bluetooth.  This is probably
because the robot needs to know which computer to send the information to and all of the computers in the robot lab have bluetooth.

- Robot shall use the sonar capabilities for it's collision detection.  This is because the bumpers aren't really sensitive enough for the assignment.  From what I've been told, the sonar is strong enough to recognise the entire world that it is in.

- If the sonar is capable, we could use the sonar during the arena scan to identify where the objects are on both X and Y axis.  The program could then become much more simple by going to the halfway point on the X axis and doing a validatory scan whilst going down the Y axis.  This may be a suitable solution to the problem given in the assignment