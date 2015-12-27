package org.usfirst.frc.team4499.robot;

import org.usfirst.frc.team4499.robot.tools.DCMotor;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static AHRS navx = new AHRS(SerialPort.Port.kMXP);
	public static DCMotor motorLeftOne = new DCMotor(1);
	public static DCMotor motorLeftTwo = new DCMotor(2);
	public static DCMotor motorRightOne = new DCMotor(3);
	public static DCMotor motorRightTwo = new DCMotor(4);
	
	public static Servo cameraYaw = new Servo(2); // for some reason pins 0, 1 won't work they crash the code
	public static Servo cameraPitch = new Servo(3);
	

	
	
	
	
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
