package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {
	CANTalon motorLeftOne = new CANTalon(1);
	CANTalon motorLeftTwo = new CANTalon(2);
	CANTalon motorRightOne = new CANTalon(3);
	CANTalon motorRightTwo = new CANTalon(4);
	RobotDrive myRobot;
	Joystick stickLeft;
	Joystick stickRight;
	int autoLoopCounter;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {   	
    	myRobot = new RobotDrive(0,1);
    	stickLeft = new Joystick(0);
    	stickRight = new Joystick(0);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {    	
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        //myRobot.arcadeDrive(stick);
    	motorLeftOne.set(stickLeft.getRawAxis(1));
    	motorLeftTwo.set(stickLeft.getRawAxis(1));
    	
    	motorRightOne.set(-stickRight.getRawAxis(5));
    	motorRightTwo.set(-stickRight.getRawAxis(5));
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
