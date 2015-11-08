package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//THIS IS A GITHUB TEST COMMENT

public class Robot extends IterativeRobot {
	CANTalon motorLeftOne = new CANTalon(1);
	CANTalon motorLeftTwo = new CANTalon(2);
	CANTalon motorRightOne = new CANTalon(3);
	CANTalon motorRightTwo = new CANTalon(4);
	double startTime;
	double currentTime;
	double lastTime = 0;
	double currentAccel = 0;
	double currentVelocity = 0;
	double currentPosition = 0;
	double acceleration = 0;
	double lastVelocity;
	double lastPos;
	double screeningValue = 0.95;
	double lastAccel;
	BuiltInAccelerometer accel;
	double Acc1;
	RobotDrive myRobot;
	Joystick stickLeft;
	Joystick stickRight;
	int autoLoopCounter;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() { 
    	acceleration = 0;
    	accel = new BuiltInAccelerometer();
    	lastTime = 0;
    	currentAccel = 0;
    	currentVelocity = 0;
    	currentPosition = 0;
    	lastVelocity = 0;
    	lastPos = 0;
    	lastAccel = 0;
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
