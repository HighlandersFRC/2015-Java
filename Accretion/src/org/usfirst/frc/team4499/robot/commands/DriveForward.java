package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.RobotStats;
import org.usfirst.frc.team4499.robot.tools.PID;

public class DriveForward extends Command{
	// PID Values for this motor
	Preferences prefs;
	
	private double kI = 0;
	private double kP = .5;
	private double kD = 0;
	
	private double ticksPerRotation = 7500;
	private double leftStarting; //starting position of left encoder in ticks
	private double rightStarting;// starting position of right encoder in ticks
	double rotations; // distance to travel
	double runTime; // Maximum time that the system can run
	double startTime; // Time that the robot started this command
	PID rightWheel = new PID(kP,kI,kD); // PID's for both motors
	PID leftWheel = new PID(kP,kI,kD); 
	
	
    
	public DriveForward(double inches) {
        //TODO once PID is working change this to distance
    	this.rotations = inches / (RobotStats.driveDiameter * Math.PI);
    	this.runTime = 20; //default to 20 seconds, longer than autonomous
    	leftStarting = RobotMap.motorLeftOne.getEncPosition()/ticksPerRotation;
    	rightStarting = RobotMap.motorRightTwo.getEncPosition()/ticksPerRotation;
    	prefs = Preferences.getInstance();
		kP = prefs.getDouble("P", .5);
		kI = prefs.getDouble("I", 0);
		kD = prefs.getDouble("D", 0);
    }
	public DriveForward(double feet, double inches){
		this.rotations = (feet * 12 + inches) / (RobotStats.driveDiameter * Math.PI);
		this.runTime = 20;//default to 20 seconds, longer than autonomous
		leftStarting = RobotMap.motorLeftOne.getEncPosition()/ticksPerRotation;
    	rightStarting = RobotMap.motorRightTwo.getEncPosition()/ticksPerRotation;
		
	}
	public DriveForward(double feet, double inches, double timeout){
		this.rotations = (feet * 12 + inches) / (RobotStats.driveDiameter * Math.PI);
		this.runTime = timeout;
		leftStarting = RobotMap.motorLeftOne.getEncPosition()/ticksPerRotation;
    	rightStarting = RobotMap.motorRightTwo.getEncPosition()/ticksPerRotation;
		
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = Timer.getFPGATimestamp();
    	leftStarting = RobotMap.motorLeftOne.getEncPosition()/ticksPerRotation;
    	rightStarting = RobotMap.motorRightTwo.getEncPosition()/ticksPerRotation;
    	CameraServer server = CameraServer.getInstance();
    	server.setQuality(50);
    	server.startAutomaticCapture("cam0");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftWheel.setSetPoint(rotations + leftStarting);
    	rightWheel.setSetPoint(-rotations + rightStarting);
    	//rightWheel.setSetPoint(distance);
    	leftWheel.updatePID(RobotMap.motorLeftOne.getEncPosition()/ticksPerRotation);
    	rightWheel.updatePID(RobotMap.motorRightTwo.getEncPosition()/ticksPerRotation);
    	//rightWheel.updatePID(RobotMap.motorRightTwo.getEncPosition());
    	
    	System.out.println("Wheel Position" + (rightWheel.getSetPoint() - RobotMap.motorRightTwo.getEncPosition()/ticksPerRotation));
    	
    	RobotMap.motorLeftOne.set(-leftWheel.getResult());
    	RobotMap.motorLeftTwo.set(-leftWheel.getResult());
    	
    	RobotMap.motorRightOne.set(-rightWheel.getResult());
    	RobotMap.motorRightTwo.set(-rightWheel.getResult());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return Timer.getFPGATimestamp() >= runTime + startTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.motorLeftOne.set(0);
    	RobotMap.motorLeftTwo.set(0);
    	
    	RobotMap.motorRightOne.set(0);
    	RobotMap.motorRightTwo.set(0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	
    }
}
