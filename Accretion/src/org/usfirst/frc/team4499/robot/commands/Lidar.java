package org.usfirst.frc.team4499.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.I2C.Port;


public class Lidar extends Command {
	private I2C i2c;
	private byte[] distance;

	
	private final int LIDAR_ADDR = 0x62;
	private final int LIDAR_CONFIG_REGISTER = 0x00;
	private final int LIDAR_DISTANCE_REGISTER = 0x8f;
	
	int average = 0;
	int lastAverage = 0;
	int[] tracking;
	int count = 0;
	int averageLength = 10;
	
	public Lidar(Port port) {
		i2c = new I2C(port, LIDAR_ADDR);
		distance = new byte[2];
		tracking = new int[averageLength];
	}
	
	// Distance in cm
	public int readBuffer() {
		return (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
	}
	public int getDistance(){
		return average;
	}
	
	
	// Update distance variable
	public void update() {
		i2c.write(LIDAR_CONFIG_REGISTER, 0x04); // Initiate measurement
		Timer.delay(0.04); // Delay for measurement to be taken
		i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance); // Read in measurement
		Timer.delay(0.005); // Delay to prevent over polling
	}
	
	public double takeAverage(){
		double sum = 0;
		for(int i = 0; i < averageLength; i++){
			sum = sum + tracking[i];
		}
		return sum / ((double)averageLength);
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		update();
		lastAverage = readBuffer();
		
	}

	@Override
	protected void execute() {
		update();
		
		tracking[count] = readBuffer();
		count = count+1;
	    if(count >= averageLength){
	       count = 0;
	    }
	    
	    average = (int)(lastAverage + .5 * (takeAverage() - lastAverage));
	    lastAverage = average;
		System.out.println(getDistance());
		
	}

	@Override
	protected boolean isFinished() {
		
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