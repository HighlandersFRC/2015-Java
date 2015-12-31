package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestSequence extends CommandGroup {
	
	public TestSequence(){
		addSequential(new DriveForward(20));
		addSequential(new Wait(1));
		addSequential(new Turn(-180));
		addSequential(new Wait(1));
		addSequential(new DriveForward(20));
		
		
		
		
		
	}

}
