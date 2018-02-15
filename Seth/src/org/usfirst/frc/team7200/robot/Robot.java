		/********************************************************************************
		 * DANGER THIS CODE IS NOT COMPLETE see lines 199- 214
		 *********************************************************************************/
package org.usfirst.frc.team7200.robot;
/*
 * DO NOT CHANGE
 */
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Robot extends IterativeRobot {
	/*
	 * DO NOT CHANGE
	 */
	/********************************************************/
	Command autoCommand;								// for auton play
	SendableChooser<Integer> autoChoose;				// menu on Smart dashboard
	SendableChooser<Integer> autoChooser;				// menu on Smart dashboard
	protected int pos = 1;
	protected int mode = 1;								// choice from Smart dashboard
	/********************************************************/
	protected DifferentialDrive m_myRobot;				// chassis motor control
	protected DifferentialDrive m_myArms;
	protected Joystick driverstick = new Joystick(0);	// port 0 joystick
	protected boolean turn180 = false;					// button choices
	protected boolean turn90 = false;					//
	protected boolean turn270 = false;					//
	protected boolean talonDump = false;				// bucket dump
	protected boolean drive = true;
	/********************************************************/
	WPI_TalonSRX talon = new WPI_TalonSRX(15);
//	talon.set(ControlMode.PercentOutput,0);				// code for in methods
	protected Timer timer;								// create timer
	double startTime;									// keep track of start time during bucket dump
	/********************************************************/
	protected Gyro gyro = new ADXRS450_Gyro();			// create gyro
	static double kP = 0.03;							// constant used to drive straight
	protected String gameData;
	/********************************************************/
//	Accelerometer accel; 
//	accel  = new BuiltInAccelerometer(Accelerometer.Range.k4G);		// for use in methods
//	double xAccel = accel.getY();									//for use in methods
	/********************************************************/

	

	@Override
	public void robotInit() {
		/*
		 * DO NOT CHANGE
		 */

		/**********************************************************************
		 * Menu selection for play selection on Smart dashboard CHANGE THIS AS NEEDED
		 *********************************************************************/
		autoChoose = new SendableChooser<Integer>();
		autoChoose.addDefault("Stop", 0);
		autoChoose.addObject("Straight Forward", 1);
		autoChoose.addObject("Dump", 2);
		SmartDashboard.putData("Autonomous Mode Chooser", autoChoose);
		
		autoChooser = new SendableChooser<Integer>();
		autoChooser.addDefault("Left", 0);
		autoChooser.addObject("Center", 1);
		autoChooser.addObject("Right", 2);
		SmartDashboard.putData("Autonomous Position Chooser", autoChooser);
		/**********************************************************************
		 * Chassis and Joystick intialization DO NOT CHANGE THIS
		 *********************************************************************/		
		Spark m_left0 = new Spark(0);
		Spark m_left1 = new Spark(1);
		SpeedControllerGroup m_left = new SpeedControllerGroup(m_left0,m_left1);
		Spark m_right2 = new Spark(2);
		Spark m_right3 = new Spark(3);
		SpeedControllerGroup m_right = new SpeedControllerGroup(m_right2,m_right3);
		m_myRobot = new DifferentialDrive(m_left, m_right);
		Spark left4 = new Spark(4);
		Spark right5 = new Spark(5);
		m_myArms = new DifferentialDrive(left4, right5);
		driverstick.setXChannel(0);
		driverstick.setYChannel(1);
		driverstick.setZChannel(2);
		driverstick.setThrottleChannel(3);
		/**********************************************************************
		 * Other initialization DO NOT CHANGE THIS
		 *********************************************************************/				
		timer = new Timer();
//		CameraServer.getInstance().startAutomaticCapture();  // DO NOT USE FOR COMPETION
		
	
	}
	
	@Override
	public void autonomousInit() {
		/*
		 * DO NOT CHANGE
		 */
		/**********************************************************************
		 * Initialize the autonomous variables
		 *********************************************************************/	
		mode = (int) autoChoose.getSelected();			// find play from dashboard
		pos = (int) autoChooser.getSelected();			// find position from dashboard
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		gyro.reset();									// restart (centre) gyro
		timer.start();									// reset timer
		timer.reset();
	}
	@Override
	public void autonomousPeriodic() {
		double angle = gyro.getAngle();			// get angle
		Scheduler.getInstance().run();			// needed to run mode mode from smart dashboard 
		/*
		 * executes play from smart dash board
		 * CHANGE OPTIONS AS NEEDED BUT ...
		 *     remember to MATCH OPTIONS WITH MENU in public void robotInit()!
		 * Case 1: do nothing
		 * Case 2: Drive straight for ____ sec(CHANGE AS NEEDED)
		 * Case 3: Turn 90 degrees (Change as needed)
		 * 
		 */
		
		switch(mode) {
		case 1:
			// beginning of case 1
			angle = gyro.getAngle();
			if(pos == 0) {
			if(timer.get()<4) {
			m_myRobot.arcadeDrive(0.7, -angle*0.15);
			}
			else if(timer.get()>4) {
				m_myRobot.arcadeDrive(0,0);
			}
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(pos == 2) {
				if(timer.get()<4) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
					else if(timer.get()>4) {
						m_myRobot.arcadeDrive(0,0);
					}				
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='L' && pos == 1) {
				angle = gyro.getAngle();
				if(timer.get()<1.22) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<1.3) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<2.3) {
					if(angle<90) {
					double speed = (90-angle)/90+0.075;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<2.35) {
					gyro.reset();
				}
				else if(timer.get()<4.2) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()<4.3) {
					m_myRobot.arcadeDrive(0,0);
					gyro.reset();
				}
				else if(timer.get()<5.3) {
					if(angle>-90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<5.4) {
					m_myRobot.arcadeDrive(0, 0);
					gyro.reset();
				}
				else if(timer.get()<8) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()>8) {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='R' && pos == 1) {
				angle = gyro.getAngle();
				if(timer.get()<1.22) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<1.3) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<2.3) {
					if(angle>-90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<2.35) {
					gyro.reset();
				}
				else if(timer.get()<4.4) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()<4.5) {
					m_myRobot.arcadeDrive(0,0);
					gyro.reset();
				}
				else if(timer.get()<5.5) {
					if(angle>-90) {
					double speed = (90-angle)/90+0.075;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
					
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				
				else if(timer.get()<5.6) {
					m_myRobot.arcadeDrive(0, 0);
					gyro.reset();
				}
				else if(timer.get()<8.1) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()>8.1) {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			else {
				m_myRobot.arcadeDrive(0, 0);
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			break;
			// end of case 1
		case 2:
			// beginning of case 2
			// beginning of left dump on left side
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='L' && pos == 0) {
			angle = gyro.getAngle();
			if(timer.get()<2.5) {
			m_myRobot.arcadeDrive(0.7, -angle*0.15);
			}
			else if(timer.get()<2.6) {
				m_myRobot.arcadeDrive(0,0);
			}
			else if(timer.get()<3.6) {
				if(angle<90) {
				double speed = (90-angle)/90+0.075;
				m_myRobot.tankDrive(speed, -speed);
				angle = gyro.getAngle();
			}
				else {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			else if(timer.get()<3.65) {
				gyro.reset();
			}
			else if(timer.get()<4.35) {
				m_myRobot.arcadeDrive(0.65, -angle*0.15);
			}
			else if(timer.get()<5.1) {
				talon.set(ControlMode.PercentOutput,-0.9);
				m_myRobot.arcadeDrive(0, 0);
			}
			else if (timer.get()<5.65){
				talon.set(ControlMode.PercentOutput,0.85);
			}
			else if(timer.get()>5.65){
				talon.set(ControlMode.PercentOutput,0);
				m_myRobot.arcadeDrive(0, 0);
			}
		}
			// end of left dump for left side
			// beginning of right dump for right side
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='R' && pos == 2) {
				angle = gyro.getAngle();
				if(timer.get()<2.5) {
				m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<2.55) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<3.55) {
					if(angle>-90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<3.6) {
					gyro.reset();
				}
				else if(timer.get()<4.3) {
					m_myRobot.arcadeDrive(0.65, -angle*0.15);
				}
				else if(timer.get()<5.05) {
					talon.set(ControlMode.PercentOutput,-0.9);
					m_myRobot.arcadeDrive(0, 0);
				}
				else if (timer.get()<5.6){
					talon.set(ControlMode.PercentOutput,0.85);
				}
				else if(timer.get()>5.6){
					talon.set(ControlMode.PercentOutput,0);
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			// end of right dump for right side
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='R' && pos == 0) {
				angle = gyro.getAngle();
				if(timer.get()<1.25) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<1.2) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<2.2) {
					if(angle<90) {
					double speed = (90-angle)/90+0.075;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<2.25) {
					gyro.reset();
				}
				else if(timer.get()<6.15) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);		//For 12 FT Forward
					}
				else if(timer.get()<6.2) {
					m_myRobot.arcadeDrive(0,0);
					gyro.reset();
				}
			else if(timer.get()<7.2) {
				if(angle>-90) {
				double speed = (90+angle)/90+0.075;
				m_myRobot.tankDrive(-speed, speed);
				angle = gyro.getAngle();
			}
				else {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			else if(timer.get()<7.3) {
				m_myRobot.arcadeDrive(0, 0);
				gyro.reset();
			}
			else if(timer.get()<8.6) {
				m_myRobot.arcadeDrive(0.7, -angle*0.15);		//For 12 FT Forward
				}
			else if(timer.get()<9.6) {
				if(angle>-90) {
				double speed = (90+angle)/90+0.075;
				m_myRobot.tankDrive(-speed, speed);
				angle = gyro.getAngle();
			}
				else {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			else if(timer.get()<9.65) {
				gyro.reset();
			}
			else if(timer.get()<10.35) {
				m_myRobot.arcadeDrive(0.65, -angle*0.15);
			}
			else if(timer.get()<11.1) {
				talon.set(ControlMode.PercentOutput,-0.9);
				m_myRobot.arcadeDrive(0, 0);
			}
			else if(timer.get()<11.65){
				talon.set(ControlMode.PercentOutput,0.85);
			}
			else if(timer.get()>11.65){
				talon.set(ControlMode.PercentOutput,0);
				m_myRobot.arcadeDrive(0, 0);
			}
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='L' && pos == 2) {
				angle = gyro.getAngle();
				if(timer.get()<1.25) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<1.3) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<2.3) {
					if(angle<90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<2.35) {
					gyro.reset();
				}
				else if(timer.get()<6.15) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);		//For 12 FT Forward
					}
				else if(timer.get()<6.2) {
					m_myRobot.arcadeDrive(0,0);
					gyro.reset();
				}
			else if(timer.get()<7.2) {
				if(angle>-90) {
				double speed = (90-angle)/90+0.075;
				m_myRobot.tankDrive(speed, -speed);
				angle = gyro.getAngle();
			}
				else {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			else if(timer.get()<7.3) {
				m_myRobot.arcadeDrive(0, 0);
				gyro.reset();
			}
			else if(timer.get()<8.6) {
				m_myRobot.arcadeDrive(0.7, -angle*0.15);		//For 12 FT Forward
				}
			else if(timer.get()<9.6) {
				if(angle>-90) {
				double speed = (90-angle)/90+0.075;
				m_myRobot.tankDrive(speed, -speed);
				angle = gyro.getAngle();
			}
				else {
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			else if(timer.get()<9.65) {
				gyro.reset();
			}
			else if(timer.get()<10.35) {
				m_myRobot.arcadeDrive(0.65, -angle*0.15);
			}
			else if(timer.get()<11.1) {
				talon.set(ControlMode.PercentOutput,-0.9);
				m_myRobot.arcadeDrive(0, 0);
			}
			else if(timer.get()<11.65){
				talon.set(ControlMode.PercentOutput,0.85);
				System.out.println("bucket down");
			}
			else if(timer.get()>11.65){
				talon.set(ControlMode.PercentOutput,0);
				m_myRobot.arcadeDrive(0, 0);
			}
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='R' && pos == 1) {
				angle = gyro.getAngle();
				if(timer.get()<1.25) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<1.3) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<2.3) {
					if(angle<90) {
					double speed = (90-angle)/90+0.075;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<2.35) {
					gyro.reset();
				}
				else if(timer.get()<4.2) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);		//For 12 FT Forward
					}
				else if(timer.get()<4.3) {
					m_myRobot.arcadeDrive(0,0);
					gyro.reset();
				}
				else if(timer.get()<5.3) {
					if(angle>-90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<5.35) {
					m_myRobot.arcadeDrive(0, 0);
					gyro.reset();
				}
				else if(timer.get()<6.65) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()<6.7) {
					m_myRobot.arcadeDrive(0, 0);
				}
				else if(timer.get()<7.7) {
					if(angle>-90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<7.75) {
					gyro.reset();
				}
				else if(timer.get()<8.4) {
					m_myRobot.arcadeDrive(0.65, -angle*0.15);
				}
				else if(timer.get()<9.15) {
					talon.set(ControlMode.PercentOutput,-0.9);
					m_myRobot.arcadeDrive(0, 0);
				}
				else if (timer.get()<9.7){
					talon.set(ControlMode.PercentOutput,0.85);
				}
				else if(timer.get()>9.7){
					talon.set(ControlMode.PercentOutput,0);
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			if(gameData.charAt(0)=='L' && pos == 1) {
				angle = gyro.getAngle();
				if(timer.get()<1.25) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
				}
				else if(timer.get()<1.3) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<2.3) {
					if(angle>-90) {
					double speed = (90+angle)/90+0.075;
					m_myRobot.tankDrive(-speed, speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<2.35) {
					gyro.reset();
				}
				else if(timer.get()<4.4) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()<4.5) {
					m_myRobot.arcadeDrive(0,0);
				}
				else if(timer.get()<5.5) {
					if(angle>-90) {
					double speed = (90-angle)/90+0.075;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<5.55) {
					m_myRobot.arcadeDrive(0, 0);
					gyro.reset();
				}
				else if(timer.get()<6.85) {
					m_myRobot.arcadeDrive(0.7, -angle*0.15);
					}
				else if(timer.get()<6.9) {
					m_myRobot.arcadeDrive(0, 0);
				}
				else if(timer.get()<7.9) {
					if(angle<90) {
					double speed = (90-angle)/90+0.075;
					m_myRobot.tankDrive(speed, -speed);
					angle = gyro.getAngle();
				}
					else {
						m_myRobot.arcadeDrive(0, 0);
					}
				}
				else if(timer.get()<7.95) {
					gyro.reset();
				}
				else if(timer.get()<8.6) {
					m_myRobot.arcadeDrive(0.65, -angle*0.15);
				}
				else if(timer.get()<9.35) {
					talon.set(ControlMode.PercentOutput,-0.9);
					m_myRobot.arcadeDrive(0, 0);
				}
				else if (timer.get()<9.9){
					talon.set(ControlMode.PercentOutput,0.85);
				}
				else if(timer.get()>9.9){
					talon.set(ControlMode.PercentOutput,0);
					m_myRobot.arcadeDrive(0, 0);
				}
			}
			/**********************************************************************
			 * Autonomous
			 *********************************************************************/	
			break;
			// end of case 2
		case 3:
			// beginning of case 3
			
			break;
			// end of case 3
		case 0:
		default:
			m_myRobot.arcadeDrive(0,0);
		}
	}

	@Override
	public void teleopInit() {
		/*
		 *  Change as needed
		 */
		/**********************************************************************
		 * Initialize the autonomous variables
		 *********************************************************************/	
		turn180 = false;			// button commands set to false
		turn270 = false;
		turn90 = false;
		
		timer.reset();				// reset the timer
		timer.start();	
		gyro.reset();				// reset the gyro
	}
	
	@Override
	public void teleopPeriodic() {
		if(driverstick.getRawButton(3)) {
			m_myArms.tankDrive(-1, -1);
		}
		else if(driverstick.getRawButton(4)) {
			m_myArms.tankDrive(1, 1);
		}
		else {
			m_myArms.tankDrive(0, 0);
		}
		/*
		 *  Get button selection
		 *  button 2 (trigger turn on Talon bucket)
		 *  button 7 turn 270
		 *  button 9 kill switch for all buttons
		 *  button 11 turn 90
		 *  
		 */
	
		if(driverstick.getRawButton(2) && talonDump != true) {
			startTime= timer.get();
			talonDump = true;
		}
		else if(driverstick.getRawButton(7)) {
			turn270 = true;
			turn90 = false;
			gyro.reset();
		}
		else if(driverstick.getRawButton(9)) {
			turn270 = false;
			turn90 = false;
			talonDump = false;
			gyro.reset();
		}
		else if(driverstick.getRawButton(11)) {
			turn270 = false;
			turn90 = true;
			gyro.reset();
		}
		/*
		 *  execute button selection over multiple cycles of the method
		 *  Will get a timeout error if you do not use boolean switches are shown above
		 *  
		 */	
		double newTime = timer.get();						// used to control bucket timing
		double timeUp = 0.75;								// how long the motor raises the bucket
		double timeDown = 0.55;								// how long motor drops bucket
		if(talonDump == true) {
			/********************************************************************************
			 * DANGER THIS CODE IS NOT COMPLETE/TESTED see lines 199- 214
			 * Lift bucket 
			 *********************************************************************************/
			if(newTime< startTime +timeUp) {
				talon.set(ControlMode.PercentOutput,-0.9);
			}else if (newTime< startTime+(timeUp+timeDown) && newTime> startTime+timeUp){
				talon.set(ControlMode.PercentOutput,0.85);
			}else if (newTime> startTime+(timeUp+timeDown)) {
				talon.set(ControlMode.PercentOutput,0);
				talonDump = false;
			}
			/********************************************************************************
			 * CODE IS NOT COMPLETE
			 *********************************************************************************/
		}	else if(turn270 == true) {
			double angle = gyro.getAngle();
			if(angle>-90) {
				double speed = (90+angle)/90 + 0.075;
				m_myRobot.tankDrive(-speed, speed);
				angle = gyro.getAngle();
			}else {
				turn270 = false;
				m_myRobot.tankDrive(0, 0);
				gyro.reset();
			}
		}else if(turn90 == true) {
			double angle = gyro.getAngle();
			if(angle<90) {
				double speed = (90-angle)/90 + 0.075;
				m_myRobot.tankDrive(speed, -speed);
				angle = gyro.getAngle();
			} else{
				turn90 = false;
				m_myRobot.tankDrive(0, 0);
				gyro.reset();
			}
		}else if(drive == true){
			double throttle = Math.sqrt(Math.abs((driverstick.getThrottle()-1)/2));	
			double turn = Math.sqrt(Math.abs(driverstick.getZ())/4)*Math.abs(driverstick.getZ())/driverstick.getZ();
			m_myRobot.arcadeDrive(-driverstick.getY()*throttle,(driverstick.getX()+turn)*throttle,true );
			double angle = gyro.getAngle();
			turn180 = false;
			turn270 = false;
			turn90 = false;
		}
	}
}