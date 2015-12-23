package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.RobotMap;
import org.usfirst.frc.team4499.robot.tools.PID;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command {

	
	private double kI = 0;
	private double kP = .5;
	private double kD = 0;
	private double target = 0;
	
	PID orientation = new PID(kP,kI,kD);
	public Turn (double degrees){
		target = degrees;
		
	}
	
	
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		System.out.println(RobotMap.navx.getYaw());
		// TODO Auto-generated method stub
		orientation.updatePID(RobotMap.navx.getYaw());
    	
    	RobotMap.motorLeftOne.set(-orientation.getResult());
    	RobotMap.motorLeftTwo.set(-orientation.getResult());
    	
    	RobotMap.motorRightOne.set(orientation.getResult());
    	RobotMap.motorRightTwo.set(orientation.getResult());
		
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		target = RobotMap.navx.getYaw() + target;
		orientation.setSetPoint(target);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
