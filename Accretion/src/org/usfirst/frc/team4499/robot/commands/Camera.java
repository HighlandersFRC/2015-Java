package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

public class Camera {
	CameraServer server;
	public Camera(int quality, String camera){
		
		server = CameraServer.getInstance();
        server.setQuality(quality);
        server.startAutomaticCapture(camera);
		
	}
}