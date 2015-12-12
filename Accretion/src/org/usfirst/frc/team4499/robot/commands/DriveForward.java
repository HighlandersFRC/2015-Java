package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4499.robot.Robot;
import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.PID;

public class DriveForward extends Command{
	private double kI = 0;
	private double kP = 0.06;
	private double kD = 0;
	
	
	private double leftStarting;
	private double rightStarting;
	PID rightWheel = new PID(kP,kI,kD);
	PID leftWheel = new PID(kP,kI,kD);
	double distance;
	double runTime;
	double startTime;
    
	public DriveForward(double distance) {
        //TODO once PID is working change this to distance
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftStarting = RobotMap.motorLeftOne.getEncPosition()/7500;
    	rightStarting = RobotMap.motorRightTwo.getEncPosition()/7500;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftWheel.setSetPoint(distance + leftStarting);
    	rightWheel.setSetPoint(-distance + rightStarting);
    	//rightWheel.setSetPoint(distance);
    	leftWheel.updatePID(RobotMap.motorLeftOne.getEncPosition()/7500);
    	rightWheel.updatePID(RobotMap.motorRightTwo.getEncPosition()/7500);
    	//rightWheel.updatePID(RobotMap.motorRightTwo.getEncPosition());
    	
    	System.out.println("Wheel Position" + (rightWheel.getSetPoint() - RobotMap.motorRightTwo.getEncPosition()/7500));
    	
    	RobotMap.motorLeftOne.set(-leftWheel.getResult());
    	RobotMap.motorLeftTwo.set(-leftWheel.getResult());
    	
    	RobotMap.motorRightOne.set(-rightWheel.getResult());
    	RobotMap.motorRightTwo.set(-rightWheel.getResult());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return false;
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
