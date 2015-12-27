package org.usfirst.frc.team4499.robot.commands;

import org.usfirst.frc.team4499.robot.OI;
import org.usfirst.frc.team4499.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Gimbal extends Command {
	double cameraYawValue;
	double cameraPitchValue;
	@Override
	protected void initialize() {
		cameraYawValue = .5;
		cameraPitchValue = .25;
	}

	@Override
	protected void execute() {
		int pov = OI.controllerOne.getPOV();
    	if(pov == 0){
    		if(cameraPitchValue < 1){
    			cameraPitchValue = cameraPitchValue + .01;
    		}
    	}
    	else if(pov == 90){
    		if(cameraYawValue < 180 ){
    			cameraYawValue = cameraYawValue + .01;
    		}
    	}
    	else if(pov == 180){
    		if(cameraPitchValue > 0 ){
    			cameraPitchValue = cameraPitchValue - .01;
    		}
    	}
    	else if(pov == 270){
    		if(cameraYawValue > 0 ){
    			cameraYawValue = cameraYawValue - .01;
    		}
    	}
    	else{
    	}
    	RobotMap.cameraPitch.set(cameraPitchValue);
    	RobotMap.cameraYaw.set(cameraYawValue);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
