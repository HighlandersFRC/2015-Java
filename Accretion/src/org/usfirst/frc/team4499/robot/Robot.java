
package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team4499.robot.commands.ExampleCommand;
import org.usfirst.frc.team4499.robot.subsystems.ExampleSubsystem;

import com.kauailabs.navx.frc.AHRS;

public class Robot extends IterativeRobot {
	AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
	RobotDrive myRobot;
	DoubleSolenoid pistonOne;
	double safety = 0.5;
	int print = 0; 
	Value pistonValue = DoubleSolenoid.Value.kOff;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {   	
    	myRobot = new RobotDrive(0,1);
    	pistonOne = new DoubleSolenoid(0,1);
    	safety = 0.5;
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit(){
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
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
    	
    	System.out.println("IMU_TotalYaw    " +ahrs.getAngle());
    	System.out.print(RobotMap.motorRightOne.getEncPosition() + "  ");
    	System.out.print(RobotMap.motorRightTwo.getEncPosition() + "  ");
    	System.out.print(RobotMap.motorLeftOne.getEncPosition() + "  ");
    	System.out.println(RobotMap.motorLeftTwo.getEncPosition());
    	print = 0;
    	}
    	print++; 
    	
		pistonOne.set(pistonValue);
    	RobotMap.motorLeftOne.set(OI.controllerOne.getRawAxis(1) * safety);
    	RobotMap.motorLeftTwo.set(OI.controllerOne.getRawAxis(1) * safety);
    	
    	RobotMap.motorRightOne.set(-OI.controllerOne.getRawAxis(5)* safety);
    	RobotMap.motorRightTwo.set(-OI.controllerOne.getRawAxis(5)* safety);
    	}
       // System.out.println("Test");
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
