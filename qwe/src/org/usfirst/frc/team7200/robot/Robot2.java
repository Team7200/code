/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7200.robot;

import edu.wpi.first.wpilibj.DriverStation;
// non depricated packages 
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Robot2 extends IterativeRobot {

	Victor WormGear = new Victor(7);
	private DifferentialDrive m_myRobot;
	private Joystick driverstick = new Joystick(0);
	private Timer timer;
//	WPI_TalonSRX talon = new WPI_TalonSRX(15);
	public Gyro gyro = new ADXRS450_Gyro();
	private double oldTime=0;
//	boolean talonOn=false;
	boolean beginAuto = true;
	boolean driveAuto = false;
	boolean turn180 = false;
	boolean turn90 = false;
	boolean turn270 = false;
	//private static final int kGyroPort = 1;	
	Accelerometer accel = new BuiltInAccelerometer(Accelerometer.Range.k4G);
//	accel  = new BuiltInAccelerometer(Accelerometer.Range.k4G);

	
	

	@Override
	public void robotInit() {
		
		Spark m_left0 = new Spark(0);
		Spark m_left1 = new Spark(1);
		SpeedControllerGroup m_left = new SpeedControllerGroup(m_left0,m_left1);
		Spark m_right2 = new Spark(2);
		Spark m_right3 = new Spark(3);
		SpeedControllerGroup m_right = new SpeedControllerGroup(m_right2,m_right3);
		m_myRobot = new DifferentialDrive(m_left, m_right);
		driverstick.setXChannel(0);
		driverstick.setYChannel(1);
		timer = new Timer();
//		CameraServer.getInstance().startAutomaticCapture();
	
	}
	
	@Override
	public void autonomousInit() {

		timer.reset();
		timer.start();
		oldTime = timer.get();
		
	}
	@Override
	public void autonomousPeriodic() {
		
		//do not set speed below 0.15, will not do anything
		if(timer.get()<1) {
//			System.out.println("In Left loop");
			m_myRobot.tankDrive(0,0,false);
		}
		else if(timer.get()<2){
			m_myRobot.tankDrive(0.69, 0.67);
		}
		else if(timer.get()<2.5) {
			m_myRobot.tankDrive(0,0);
		}
		else if(timer.get()<3.5) {
			m_myRobot.tankDrive(0.345,-0.325,false);
		}
		else if(timer.get()<4) {
			m_myRobot.tankDrive(0, 0);
		}
		else if(timer.get()<6) {
			m_myRobot.tankDrive(0.948, 0.923);
		}
		else {
		   m_myRobot.tankDrive(0,0,false);
		}
		
//		if(gameData.charAt(0)=='L') {
//			if(timer.get()<1) {
//				System.out.println("In Left loop");
//				m_myRobot.tankDrive(0.2,0.2);
//			}else {
//				System.out.println("Ending Left loop");
//			}
//		}else {
//			if(timer.get()<oldTime+1) {
//				System.out.println("In right loop");
//			}else {
//				System.out.println("Ending Right loop");
//			}
//		}	
	}

	@Override
	public void teleopInit() {
		turn180 = false;
		turn270 = false;
		turn90 = false;
		
		timer.reset();
		timer.start();	
		gyro.reset();
//		talon.set(ControlMode.PercentOutput,0);
		
	}
	
	@Override
	public void teleopPeriodic() {

		
			if(driverstick.getRawButton(7)) {
				turn180 = false;
				turn270 = true;
				turn90 = false;
				gyro.reset();
//				System.out.println("Left");
//				System.out.println("3 button pressed"+angle + turn);
			}
			else if(driverstick.getRawButton(9)) {
				turn180 = true;
				turn270 = false;
				turn90 = false;
				gyro.reset();
//				System.out.println("180");
			}
			else if(driverstick.getRawButton(11)) {
				turn180 = false;
				turn270 = false;
				turn90 = true;
				gyro.reset();
//				System.out.println("Right");
			}

			
			
			
			if(turn180 == true) {
				double angle = gyro.getAngle();
				
				if(angle<180) {
					double speed = (180-angle)/180;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
				else if(angle>180){
					turn180 = false;
					m_myRobot.tankDrive(0, 0);
					gyro.reset();
				}
			}

			
			else if(turn270 == true) {
				double angle = gyro.getAngle();
				if(angle>-45) {
					m_myRobot.tankDrive(-0.75, 0.75);
					angle = gyro.getAngle();
				}
				else if(angle>-60) {
					m_myRobot.tankDrive(-0.6, 0.6);
					angle = gyro.getAngle();
				}
				else if(angle>-75) {
					m_myRobot.tankDrive(-0.45, 0.45);
					angle = gyro.getAngle();
				}
				else if(angle>-90) {
					m_myRobot.tankDrive(-0.25, 0.25);
					angle = gyro.getAngle();
				}
				else {
					turn270 = false;
					m_myRobot.tankDrive(0, 0);
					gyro.reset();
				}
			}
			
			else if(turn90 == true) {
				double angle = gyro.getAngle();
				if(angle<45) {
					m_myRobot.tankDrive(0.75, -0.75);
					angle = gyro.getAngle();
				}
				else if(angle<60) {
					m_myRobot.tankDrive(0.6, -0.6);
					angle = gyro.getAngle();
				}
				else if(angle<75) {
					m_myRobot.tankDrive(0.45, -0.45);
					angle = gyro.getAngle();
				}
				else if(angle<90) {
					m_myRobot.tankDrive(0.25, -0.25);
					angle = gyro.getAngle();
				}
				else {
					turn90 = false;
					m_myRobot.tankDrive(0, 0);
					gyro.reset();
				}
			}

			else {
				m_myRobot.arcadeDrive(-driverstick.getY(),driverstick.getX(),true );
				double angle = gyro.getAngle();
				System.out.println("Angle = " + angle);
				turn180 = false;
				turn270 = false;
				turn90 = false;
				}
			
		
		double xAccel = accel.getY();
//		System.out.println("xaccel" + xAccel);
		
//		if(talonOn==true) {
//			System.out.println("In true loop");
//			if(timer.get()<oldTime+3) {
//				System.out.println("motor running time is" + timer.get() + "oldtime is "+ oldTime);
//			}else {
//				talon.set(ControlMode.PercentOutput,0);
//				talonOn=false;
//			}
//		}
		
		
//		if(driverstick.getRawButton(3)) {
//			System.out.println("motor turned on ");
//			oldTime = timer.get();
//			talon.set(ControlMode.PercentOutput,0.5);
//			talonOn=true;
//		}
		
	}

}

