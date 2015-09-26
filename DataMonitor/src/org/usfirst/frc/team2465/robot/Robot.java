package org.usfirst.frc.team2465.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
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
        stick = new Joystick(0);
        try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            ahrs = new AHRS(SerialPort.Port.kMXP);
            /* Construct Digital I/O Objects */
            pwm_out_9 = new Victor(        getChannelFromPin( PinType.PWM,       9 ));
            pwm_out_8 = new Jaguar(        getChannelFromPin( PinType.PWM,       8 ));
            pwm_out_7 = new Victor(        getChannelFromPin( PinType.PWM,       7 ));
            pwm_out_6 = new Jaguar(        getChannelFromPin( PinType.PWM,       6 ));
            pwm_out_5 = new Victor(        getChannelFromPin( PinType.PWM,       5 ));
            pwm_out_4 = new Jaguar(        getChannelFromPin( PinType.PWM,       4 ));
            pwm_out_3 = new Victor(        getChannelFromPin( PinType.PWM,       3 ));
            pwm_out_2 = new Jaguar(        getChannelFromPin( PinType.PWM,       2 ));
            pwm_out_1 = new Victor(        getChannelFromPin( PinType.PWM,       1 ));
            pwm_out_0 = new Jaguar(        getChannelFromPin( PinType.PWM,       0 ));
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        /* Configure I/O Objects */
        pwm_out_9.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_8.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_7.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_6.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_5.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_4.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_3.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_2.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_1.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        pwm_out_0.setSafetyEnabled(false); /* Disable Motor Safety for Demo */
        
        pwm_out_9.set(-1.0);
        pwm_out_8.set(-0.8);
        pwm_out_7.set(-0.6);
        pwm_out_6.set(-0.4);
        pwm_out_5.set(-0.2);
        pwm_out_4.set(-0.0);
        pwm_out_3.set(-0.2);
        pwm_out_2.set(-0.4);
        pwm_out_1.set(-0.6);
        pwm_out_0.set(-0.8);
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
        while (isOperatorControl() && isEnabled()) {
            
            Timer.delay(0.020);		/* wait for one motor update time period (50Hz)     */
            
            boolean zero_yaw_pressed = stick.getTrigger();
            if ( zero_yaw_pressed ) {
                ahrs.zeroYaw();
            }

            /* Display 6-axis Processed Angle Data                                      */
            SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
            SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
            SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
            SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
            SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
            
            /* Display tilt-corrected, Magnetometer-based heading (requires             */
            /* magnetometer calibration to be useful)                                   */
            
            SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
            
            /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
            SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

            /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
            /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
            
            SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
            SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());
            System.out.println("TotalYaw="+ahrs.getAngle());
            /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
            
            SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
            SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
            SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
            SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

            /* Display estimates of velocity/displacement.  Note that these values are  */
            /* not expected to be accurate enough for estimating robot position on a    */
            /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
            /* of these errors due to single (velocity) integration and especially      */
            /* double (displacement) integration.                                       */
            
            SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
            SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
            SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
            SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
            
            /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
            /* NOTE:  These values are not normally necessary, but are made available   */
            /* for advanced users.  Before using this data, please consider whether     */
            /* the processed data (see above) will suit your needs.                     */
            
            SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
            SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
            SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
            SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
            SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
            SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
            SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
            SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
            SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
            SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());
            
            /* Omnimount Yaw Axis Information                                           */
            /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
            AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
            SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
            SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
            
            /* Sensor Board Information                                                 */
            SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());
            
            /* Quaternion Data                                                          */
            /* Quaternions are fascinating, and are the most compact representation of  */
            /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
            /* from the Quaternions.  If interested in motion processing, knowledge of  */
            /* Quaternions is highly recommended.                                       */
            SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
            SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
            SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
            SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());
            
            /* Connectivity Debugging Support                                           */
            SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
            SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
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
