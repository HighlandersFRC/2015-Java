package org.usfirst.frc.team4499.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

public class Robot extends IterativeRobot {
	AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
	CANTalon motorLeftOne = new CANTalon(1);
	CANTalon motorLeftTwo = new CANTalon(2);
	CANTalon motorRightOne = new CANTalon(3);
	CANTalon motorRightTwo = new CANTalon(4);
	RobotDrive myRobot;
	Joystick controllerOne;
	JoystickButton safetyOff;
	JoystickButton safetyOn;
	JoystickButton safetyOffTwo;
	
	JoystickButton pistonOneIn;
	JoystickButton pistonOneOut;
	DoubleSolenoid pistonOne;
	int autoLoopCounter;
	double safety = 0.5;
	Value pistonValue = DoubleSolenoid.Value.kOff;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {   	
    	myRobot = new RobotDrive(0,1);
    	controllerOne = new Joystick(0);
    	safetyOff = new JoystickButton(controllerOne,7);
    	safetyOffTwo = new JoystickButton(controllerOne,8);
    	safetyOn = new JoystickButton(controllerOne,2);
    	pistonOne = new DoubleSolenoid(0,1);
    	pistonOneIn = new JoystickButton(controllerOne, 6);
    	pistonOneOut = new JoystickButton(controllerOne,5);
    	safety = 0.5;
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit(){
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
    	if(safetyOn.get()){
    		safety = 0.5;
    	}
    	if (safetyOff.get()&& safetyOffTwo.get()){
    		safety = 1;
    	}
    	if(pistonOneIn.get()){
    		pistonValue = DoubleSolenoid.Value.kForward;
    	}
    	if(pistonOneOut.get()){
    		pistonValue = DoubleSolenoid.Value.kReverse;
    	}
    	//System.out.println("IMU_TotalYaw    " +ahrs.getAngle());
    	System.out.print(motorRightOne.getEncPosition() + "  ");
    	System.out.print(motorRightTwo.getEncPosition() + "  ");
    	System.out.print(motorLeftOne.getEncPosition() + "  ");
    	System.out.println(motorLeftTwo.getEncPosition());
    	
    		pistonOne.set(pistonValue);
	    	motorLeftOne.set(controllerOne.getRawAxis(1) * safety);
	    	motorLeftTwo.set(controllerOne.getRawAxis(1) * safety);
	    	
	    	motorRightOne.set(-controllerOne.getRawAxis(5)* safety);
	    	motorRightTwo.set(-controllerOne.getRawAxis(5)* safety);
    	}
       // System.out.println("Test");
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}

