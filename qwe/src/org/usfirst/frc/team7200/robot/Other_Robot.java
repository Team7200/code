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
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//imports for commands previously found in main file
import org.usfirst.frc.team7200.robot.commands.Auto_Drive_Forward;
import org.usfirst.frc.team7200.robot.commands.Controlled_Drive;
import org.usfirst.frc.team7200.robot.commands.Auto_Turn;


// imports from previous fill ... not all valid
//import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Other_Robot extends IterativeRobot {
	Command autonomousCommand;
	SendableChooser autoChooser;
	Victor WormGear = new Victor(7);
	private DifferentialDrive m_myRobot;
	private Joystick driverstick = new Joystick(0);
	private Timer timer;
//	WPI_TalonSRX talon = new WPI_TalonSRX(15);
//	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private double oldTime=0;
//	boolean talonOn=false;
	boolean beginAuto = true;
	boolean driveAuto = false;
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
		
		CameraServer.getInstance().startAutomaticCapture();
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Default Program", new Auto_Drive_Forward());
		autoChooser.addObject("Experimental Auto", new Auto_Turn());
		SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
		

	}
	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
		
		timer.reset();
		timer.start();
//		gyro.reset();
//		gyro.calibrate();
		oldTime = timer.get();
		
	}
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		timer.reset();
		timer.start();	
//		talon.set(ControlMode.PercentOutput,0);
//		gyro.reset();
		
	}
	@Override
	public void teleopPeriodic() {
		m_myRobot.arcadeDrive(-driverstick.getY(),driverstick.getX(),true );
		
//		double angle = gyro.getAngle();
//		System.out.println("Heading" + angle);
		
		double xAccel = accel.getY();
		System.out.println("xaccel" + xAccel);
		
//		if(talonOn==true) {
//			System.out.println("In true loop");
//			if(timer.get()<oldTime+3) {
//				System.out.println("motor running time is" + timer.get() + "oldtime is "+ oldTime);
//			}else {
//				talon.set(ControlMode.PercentOutput,0);
//				talonOn=false;
//			}
//		}
		
		
		if(driverstick.getRawButton(3)) {
//			System.out.println("motor turned on ");
			oldTime = timer.get();
//			talon.set(ControlMode.PercentOutput,0.5);
//			talonOn=true;
		}
		
		if(driverstick.getRawButton(5)) {
			oldTime = timer.get();
			WormGear.set(1);
		}
		
		if(driverstick.getRawButton(6)) {
			oldTime = timer.get();
			WormGear.set(0);
		}

	}

}