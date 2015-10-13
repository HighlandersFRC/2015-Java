package org.usfirst.frc.team2465.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program providing a real-time display of navX
 * MXP values.
 *
 * In the operatorControl() method, all data from the navX sensor is retrieved
 * and output to the SmartDashboard.
 *
 * The output data values include:
 *
 * - Yaw, Pitch and Roll angles
 * - Compass Heading and 9-Axis Fused Heading (requires Magnetometer calibration)
 * - Linear Acceleration Data
 * - Motion Indicators
 * - Estimated Velocity and Displacement
 * - Quaternion Data
 * - Raw Gyro, Accelerometer and Magnetometer Data
 *
 * As well, Board Information is also retrieved; this can be useful for debugging
 * connectivity issues after initial installation of the navX MXP sensor.
 *
 */
public class Robot extends SampleRobot {
	BuiltInAccelerometer accel;
	double startTime;
	double currentTime;
	double lastTime;
	double currentAccel;
	double currentVelocity;
	double currentPosition;
	double Acc1; 
	double LastVelocity;
	double aVelocity;
	double calY;
	int positive;
	double movement;
	double maxNegitive;
	boolean canChange;
    AHRS ahrs;
    Joystick stick;
    Victor pwm_out_9;           /* E.g., PWM out to motor controller  */
    Jaguar pwm_out_8;           /* E.g., PWM out to motor controller  */
    Victor pwm_out_7;           /* E.g., PWM out to motor controller  */
    Jaguar pwm_out_6;           /* E.g., PWM out to motor controller  */
    Victor pwm_out_5;           /* E.g., PWM out to motor controller  */
    Jaguar pwm_out_4;           /* E.g., PWM out to motor controller  */
    Victor pwm_out_3;           /* E.g., PWM out to motor controller  */
    Jaguar pwm_out_2;           /* E.g., PWM out to motor controller  */
    Victor pwm_out_1;           /* E.g., PWM out to motor controller  */
    Jaguar pwm_out_0;           /* E.g., PWM out to motor controller  */
 
    public Robot() {
    	accel = new BuiltInAccelerometer();
        stick = new Joystick(0);
        startTime = Timer.getFPGATimestamp();
        currentPosition = 0;
        try {
            ahrs = new AHRS(SerialPort.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        currentPosition = 0;
        for (int i = 0; i < 100; i++ ){
        	calY = calY + ahrs.getRawAccelY();
        	Timer.delay(.01);
        }
        calY = calY /100;
    }

    /**
     * Runs during autonomous mode
     */
    public void autonomous() {
        Timer.delay(2.0);		//    for 2 seconds
    }

    /**
     * Display navX MXP Sensor Data on Smart Dashboard
     */
    public void operatorControl() {
    	currentPosition = 0;
        while (isOperatorControl() && isEnabled()) {
            
            Timer.delay(0.020);		/* wait for one motor update time period (50Hz)     */
            
            boolean zero_yaw_pressed = stick.getTrigger();
            if ( zero_yaw_pressed ) {
                ahrs.zeroYaw();
            }
            currentTime = Timer.getFPGATimestamp();
            Acc1 = ahrs.getWorldLinearAccelY();
            if (Math.abs(Acc1)<0.005){
            	Acc1=0;
            }
            //currentVelocity = ahrs.getVelocityY();
            //System.out.println(Acc1);
            currentVelocity += Acc1 * (currentTime-lastTime);
            movement = currentVelocity;
        	if (movement < 0 && canChange){
        		positive = -1;
        	}
        	if (movement > 0 && canChange){
        		positive = 1;
        	}
            
            if (Math.abs(currentVelocity) > 0 ){
            		if (positive ==1  && currentVelocity < 0){
            			currentVelocity = 0;
            		}
            		if (positive ==-1 && currentVelocity > 0){
            			currentVelocity = 0;
            		}
            		//new
            		Timer.delay (0.02);	
            		canChange = false;
            }
            else{
            	positive = 0;
            	canChange = true;
            }
            
            Acc1 = 0;
            currentPosition +=  currentVelocity * (currentTime - lastTime);
            System.out.println(currentPosition);
            lastTime = currentTime;
            
            
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
    public enum PinType { DigitalIO, PWM, AnalogIn, AnalogOut };
    
    public final int MAX_NAVX_MXP_DIGIO_PIN_NUMBER      = 9;
    public final int MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER   = 3;
    public final int MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER  = 1;
    public final int NUM_ROBORIO_ONBOARD_DIGIO_PINS     = 10;
    public final int NUM_ROBORIO_ONBOARD_PWM_PINS       = 10;
    public final int NUM_ROBORIO_ONBOARD_ANALOGIN_PINS  = 4;
    
    /* getChannelFromPin( PinType, int ) - converts from a navX MXP */
    /* Pin type and number to the corresponding RoboRIO Channel     */
    /* Number, which is used by the WPI Library functions.          */
    
    public int getChannelFromPin( PinType type, int io_pin_number ) 
               throws IllegalArgumentException {
        int roborio_channel = 0;
        if ( io_pin_number < 0 ) {
            throw new IllegalArgumentException("Error:  navX MXP I/O Pin #");
        }
        switch ( type ) {
        case DigitalIO:
            if ( io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER ) {
                throw new IllegalArgumentException("Error:  Invalid navX MXP Digital I/O Pin #");
            }
            roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_DIGIO_PINS + 
                              (io_pin_number > 3 ? 4 : 0);
            break;
        case PWM:
            if ( io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER ) {
                throw new IllegalArgumentException("Error:  Invalid navX MXP Digital I/O Pin #");
            }
            roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_PWM_PINS;
            break;
        case AnalogIn:
            if ( io_pin_number > MAX_NAVX_MXP_ANALOGIN_PIN_NUMBER ) {
                throw new IllegalArgumentException("Error:  Invalid navX MXP Analog Input Pin #");
            }
            roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_ANALOGIN_PINS;
            break;
        case AnalogOut:
            if ( io_pin_number > MAX_NAVX_MXP_ANALOGOUT_PIN_NUMBER ) {
                throw new IllegalArgumentException("Error:  Invalid navX MXP Analog Output Pin #");
            }
            roborio_channel = io_pin_number;            
            break;
        }
        return roborio_channel;
    }
    
}
