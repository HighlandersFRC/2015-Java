package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardFF extends Command {
	//public double maxAccel = f 
    public DriveForwardFF() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    private double getPowerLevel(double torque){
    	
    	
    	
    	
    	return 0;
    }
    private double getResistance(){
    	double driveSpeed =  15 * RobotMap.motorLeftOne.getEncVelocity()/(7500);
    	double resistance = 8.2 * 0.001 * driveSpeed  + (12 / 133);
    	return resistance;
    }
    
    
}
