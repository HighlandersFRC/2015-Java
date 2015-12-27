
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
	DoubleSolenoid pistonOne;
	double safety = 0.5;
	int print = 0; 
	Value pistonValue = DoubleSolenoid.Value.kOff;
	CameraServer server;
	double cameraYawValue = .5;
	double cameraPitchValue = .5;

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {   	
    	myRobot = new RobotDrive(0,1);
    	pistonOne = new DoubleSolenoid(0,1);
    	safety = 0.5;
    	server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
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
    		System.out.println("Started Autonomous Two");
    		//DriveForward forward = new DriveForward(15);
        	//forward.start();
    	}
    	else if(OI.dialFour.get()){
    		System.out.println("Started Autonomous Two");
    		//DriveForward forward = new DriveForward(15);
        	//forward.start();
    	}
    	else if(OI.dialFive.get()){
    		System.out.println("Started Autonomous Two");
    		//DriveForward forward = new DriveForward(15);
        	//forward.start();
    	}
    	//DriveForward forward = new DriveForward(5,0);
    	//forward.start();
    	Turn turn = new Turn(90);
    	turn.start();
    	
    	

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
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
	    while (isOperatorControl() && isEnabled()) {
	    	Arduino.write(1);
	    	//autonomousInit();
	    	if(OI.safetyOn.get()){
	    		safety = 0.5;
	    	}
	    	if (OI.safetyOff.get()&& OI.safetyOffTwo.get()){
	    		safety = 1;
	    	}
	    	if(OI.shiftUp.get()){
	    		pistonValue = DoubleSolenoid.Value.kForward;
	    	}
	    	if(OI.shiftDown.get()){
	    		pistonValue = DoubleSolenoid.Value.kReverse;
	    	}
	    	if(print > 100){
	    	//SmartDashboard.putNumber("Orientation",ahrs.getAngle());
	    //	System.out.println("IMU_TotalYaw    " +ahrs.getAngle());
	    	//System.out.print(RobotMap.motorRightOne.getEncPosition() + "  ");
	    	//System.out.print(RobotMap.motorRightTwo.getEncPosition() + "  ");
	    	//System.out.print(RobotMap.motorLeftOne.getEncPosition() + "  ");
	    	//System.out.println(RobotMap.motorLeftTwo.getEncPosition());
	    	print = 0;
	    	}
	    	print++; 
	    	
			pistonOne.set(pistonValue);
	    	RobotMap.motorLeftOne.set(OI.controllerOne.getRawAxis(1) * safety);
	    	RobotMap.motorLeftTwo.set(OI.controllerOne.getRawAxis(1) * safety);
	    	
	    	RobotMap.motorRightOne.set(-OI.controllerOne.getRawAxis(3)* safety);
	    	RobotMap.motorRightTwo.set(-OI.controllerOne.getRawAxis(3)* safety);
	    	
	    	
	    	int pov = OI.controllerOne.getPOV();
	    	System.out.println("Pov: " + pov);
	    	if(pov == 0){
	    		if(cameraPitchValue < 1){
	    			cameraPitchValue = cameraPitchValue + .01;
	    		}
	    	}
	    	else if(pov == 90){
	    		if(cameraYawValue < 180 ){
	    			cameraYawValue = cameraYawValue + .01;
	    		}
	    	}
	    	else if(pov == 180){
	    		if(cameraPitchValue > 0 ){
	    			cameraPitchValue = cameraPitchValue - .01;
	    		}
	    	}
	    	else if(pov == 270){
	    		if(cameraYawValue > 0 ){
	    			cameraYawValue = cameraYawValue - .01;
	    		}
	    	}
	    	else{
	    	}
	    	RobotMap.cameraPitch.set(cameraPitchValue);
	    	RobotMap.cameraYaw.set(cameraYawValue);
	    	
	    	
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
