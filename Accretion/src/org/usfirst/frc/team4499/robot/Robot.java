
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4499.robot.commands.*;
import org.usfirst.frc.team4499.robot.subsystems.*;
import com.kauailabs.navx.frc.AHRS;

public class Robot extends IterativeRobot {
	AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
	RobotDrive myRobot;
	DoubleSolenoid pistonOne;
	double safety = 0.5;
	int print = 0; 
	Value pistonValue = DoubleSolenoid.Value.kOff;
	CameraServer server;
	
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
         //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit(){
    	System.out.println("Started Autonomous");
    	DriveForward forward = new DriveForward(5,0);
    	forward.start();
    	
    	
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
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
	    while (isOperatorControl() && isEnabled()) {
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
	    	SmartDashboard.putNumber("Orientation",ahrs.getAngle());
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
	    	}
	    Timer.delay(0.005);
    }
       // System.out.println("Test");
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
