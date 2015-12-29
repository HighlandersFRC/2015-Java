package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command {

	
	private double kI = 0;
	private double kP = 0.0025;
	private double kD = 0;
	private double target = 0;
	Preferences prefs;
	PID orientation = new PID(kP,kI,kD);
	public Turn (double degrees){
		while(degrees > 360){
			degrees = degrees - 360;
		}
		while(degrees < -360){
			degrees = degrees + 360;
		}
		
		target = degrees;
		
	}
	
	@Override
	protected void initialize() {
		RobotMap.navx.zeroYaw();
		target = RobotMap.navx.getYaw() + target;
		orientation.setSetPoint(target);
		
		prefs = Preferences.getInstance();
		kP = prefs.getDouble("P", 0.003);
		kI = prefs.getDouble("I", 0);
		kD = prefs.getDouble("D", 0);
	}
	
	
	

	@Override
	protected void execute() {
		System.out.println(RobotMap.navx.getYaw());
		orientation.updatePID(RobotMap.navx.getYaw());
    	
    	RobotMap.motorLeftOne.set(-orientation.getResult());
    	RobotMap.motorLeftTwo.set(-orientation.getResult());
    	
    	RobotMap.motorRightOne.set(-orientation.getResult());
    	RobotMap.motorRightTwo.set(-orientation.getResult());
		
	}

	@Override
	protected void interrupted() {
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
	}
	
}
