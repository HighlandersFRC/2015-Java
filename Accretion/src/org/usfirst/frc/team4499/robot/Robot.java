
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.subsystems.*;
import org.usfirst.frc.team4499.robot.tools.Arduino;

import com.kauailabs.navx.frc.AHRS;

public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	TankDrive drive;
	Gimbal cameraMount; 
	Camera camera;
	
	int print = 0; 
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {   	
    	myRobot = new RobotDrive(0,1);
        drive = new TankDrive(
        		RobotMap.motorLeftTwo,
				RobotMap.motorLeftOne, 
				RobotMap.motorRightTwo, 
				RobotMap.motorRightOne);
        cameraMount = new Gimbal();
        camera = new Camera(50, "cam0");
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit(){
    	System.out.println("Started Autonomous");
    	Arduino.write(2);
    	if(OI.dialOne.get()){
    		System.out.println("Started Autonomous One");
    	}
    	else if(OI.dialTwo.get()){
    		System.out.println("Started Autonomous Two");
    		//DriveForward forward = new DriveForward(15);
        	//forward.start();
    	}
    	else if(OI.dialThree.get()){
    		System.out.println("Started Autonomous Three");
    	}
    	else if(OI.dialFour.get()){
    		System.out.println("Started Autonomous Four");
    	}
    	else if(OI.dialFive.get()){
    		System.out.println("Started Autonomous Five");
    	}
    	else{
    		// Do Nothing
    		// Default to No auto
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	Scheduler.getInstance().run();
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	System.out.println("Teleop Started");
    	Arduino.write(1);
    	drive.start();
    	cameraMount.start();
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
	    while (isOperatorControl() && isEnabled()) {
	    	Scheduler.getInstance().run();
	    	Arduino.write(1);
	    	
	    	if(print > 100){
	    		SmartDashboard.putNumber("Orientation",RobotMap.navx.getAngle());
	    		print = 0;
	    	}
	    	print++; 
	    	
	    	Timer.delay(0.005);
	    }  
    }
    public void disabledInit(){
    	System.out.println("Disabled Started");
    	Arduino.write(0);
    	
    }
       // System.out.println("Test");
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
