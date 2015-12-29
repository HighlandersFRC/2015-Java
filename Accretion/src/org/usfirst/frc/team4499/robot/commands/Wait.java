package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command{
	private double time;
	private double startTime;
	public Wait(double time){
		this.time = time;
	}
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
		
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return startTime + time < Timer.getFPGATimestamp();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
