
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.usfirst.frc.team4499.robot.*;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.tools.Arduino;

public class Robot extends IterativeRobot {
	TankDrive drive;
	Gimbal cameraMount; 
	Camera camera;
	Value pistonValue;
	int print = 0; 
	Lidar distance;
	
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {   	
        drive = new TankDrive(
        		RobotMap.motorLeftTwo,
				RobotMap.motorLeftOne, 
				RobotMap.motorRightTwo, 
				RobotMap.motorRightOne,
				RobotMap.shifters);
        cameraMount = new Gimbal();
        camera = new Camera(50, "cam0");
        pistonValue = DoubleSolenoid.Value.kOff;
        distance = new Lidar(I2C.Port.kMXP);
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
    		DriveForward forward = new DriveForward(20);
        	forward.start();
        	System.out.println("Autonomous Completed");
    	}
    	else if(OI.dialThree.get()){
    		System.out.println("Started Autonomous Three");
    		Turn turnaround = new Turn(-180);
    		turnaround.start();
    	}
    	else if(OI.dialFour.get()){
    		System.out.println("Started Autonomous Four");
    		TestSequence sequence = new TestSequence();
    		sequence.start();
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
    	SmartDashboard.putNumber("Orientation",RobotMap.navx.getAngle());
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	System.out.println("Teleop Started");
    	Arduino.write(1);
    	drive.start();
    	cameraMount.start();
    	distance.start();
    	
    	
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
	    while (isOperatorControl() && isEnabled()) {
	    	distance.update();
	    	System.out.println(distance.getDistance());
	    	Scheduler.getInstance().run();
	    	Arduino.write(1);
	    	SmartDashboard.putNumber("Orientation",RobotMap.navx.getAngle());
	    	if(print > 100){
	    		
	    		print = 0;
	    	}
	    	print++; 
	    	
	    	if(OI.pistonIn.get()){
	    		pistonValue = DoubleSolenoid.Value.kForward;
	    	}
	    	if(OI.pistonOut.get()){
	    		pistonValue = DoubleSolenoid.Value.kReverse;
	    	}
	    	RobotMap.piston.set(pistonValue);
	    	
	    	distance.update();
	    	System.out.println(distance.getDistance());
	    	
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
