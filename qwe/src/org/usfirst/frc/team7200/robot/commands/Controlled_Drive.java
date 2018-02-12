package org.usfirst.frc.team7200.robot.commands;

// imports
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;


public class Controlled_Drive extends IterativeRobot{
	private DifferentialDrive m_myRobot;
	private Joystick driverstick = new Joystick(0);
	Accelerometer accel = new BuiltInAccelerometer(Accelerometer.Range.k4G);

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
	}

	public Controlled_Drive() {
		m_myRobot.arcadeDrive(-driverstick.getY(),driverstick.getX(),true );
		double xAccel = accel.getY();
		System.out.println("xaccel" + xAccel);
		// TODO Auto-generated constructor stub
	}

}
