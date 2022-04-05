package frc.swerverobot;

//Sensor Library
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.DigitalInput; //Photo Eye
import com.revrobotics.ColorSensorV3;//Color Sensor

//Controller Libraries
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import org.frcteam2910.common.robot.input.DPadButton.Direction;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
//Motor Controller Libraries
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/*Needed Electronics
Solenoids(4)
Motor Controllers (13)
Rasberry Pi (1)
Cameras (2)
Photo Eye(1)
Color Sensor(1)
Blinky Light(1)
Voltage Regulator(1)
Pnumatic Hub(1)
Compressor(1)
*/

public class RobotMap {
//Define all ports and constants of a component, put in correct catagory, label subsystem(s) used

//Config Variables
    public static final double firstBallPowerMultiplier = 1.1;
    public static final double autoAimTopMotorPowerMultipler = 1;
    public static final double autoAimBottomMotorPowerMultipler = 1;
    public static final double autoAimFeedMotorPowerMultipler = 1;

//LEDS 
    public static final int LEDPWMPort = 5;                                     //[LED Subsystem] Port for the LED's

//Sensors
    public static final ADIS16470_IMU imu = new ADIS16470_IMU();                //[DriveTrain Subsystem]Gyro and acceleration sensor
    public static final int PhotoEyePort = 0;                                   //[Shooter Subsystem] The Number is the RIO DIO port (Outputs a Digital Input|Like a Limit Switch)
    public static final int PhotoEye2Port = 3;                                  //[Shooter Subsystem] The Photoeye below the color sensor
    public static final I2C.Port i2cPort = I2C.Port.kOnboard;                   //[Shooter Subsystem] Color Sensor (Only one I2C port on the robot)
    public static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort); //[Shooter/Intake Subsystem] The Number is the I2C port from the RobotMap.java
    public static final int colorSensorDistance = 100;                          //[Shooter/Intake Subsystem] The Number is the distance from the sensor to the target (Small=Close, Big=Far)
    public static final DigitalInput photoEye = new DigitalInput(PhotoEyePort); //[Shooter/Intake Subsystems] Photo Eye (Digital Input|Boolean)
    public static final DigitalInput photoEye2 = new DigitalInput(PhotoEye2Port); //[Shooter/Intake Subsystems] Photo Eye (Digital Input|Boolean)
    public static final int StatHook1Port = 1;
    public static final int StatHook2Port = 2;
    public static final DigitalInput StatHook1 = new DigitalInput(StatHook1Port);//[Shooter/Intake Subsystems] Photo Eye (Digital Input|Boolean)
   public static final DigitalInput StatHook2 = new DigitalInput(StatHook2Port); //[Shooter/Intake Subsystems] Photo Eye (Digital Input|Boolean)

//PWM Motors
    public static final int TopMotorPort = 1;                                   //[Shooter Subsystem] The Number is the RIO DIO port 
    public static final int BottomMotorPort = 2;                                //[Shooter Subsystem] The Number is the RIO DIO port
    public static final int FeedMotorPort = 0;                                  //[Shooter Subsystem] The Number is the RIO DIO port
    public static final Spark feedMotor = new Spark(FeedMotorPort);             //[Shooter / Intake Subsystems]
    public static final int Intake_Motor_PWM = 3;                               //[Intake Subsystem] PWM for intake motor
    public static final PWMVictorSPX Intake_Motor = new PWMVictorSPX(Intake_Motor_PWM);     //[Intake Subsystem] PWM for intake motor
    // public static final int CLIMBER_MOTOR = 4;                                  //[Climber Subsystem] The Number is the RIO DIO port (Winch)

//Controls
    //DriveTrain Controller (Kendall)
    public static final Controller Driver_controller = new XboxController(0);               //[Driver's Controller]
    public static final Axis Speed_Increase = Driver_controller.getLeftTriggerAxis();       //[DriveTrain Subsystem](L-Trigger|Increase Speed)
    public static final Axis Speed_Decrease = Driver_controller.getRightTriggerAxis();      //[DriveTrain Subsystem](R-Trigger|Decrease Speed)
    public static final Button Intake_Extension = Driver_controller.getLeftBumperButton();  //[Intake Subsystem](L-Bumper|Extend Intake)
    public static final Button Intake_Retract = Driver_controller.getRightBumperButton();   //[Intake Subsystem](R-Bumper|Retract Intake)
    public static final Button ResetGyro = Driver_controller.getBackButton();               //[DriveTrain Subsystem](Back-Button|Reset Gyro)
    
    //Shooter/Climber Controller (Noah)
    public static final Controller Shooter_controller = new XboxController(1);                  //[Shooter's Controller]
    public static final Button ManualShoot = Shooter_controller.getXButton();                   //[Shooter Subsystem] (X|Manual Shoot)
    public static final Button DropBallBTN = Shooter_controller.getBButton();                   //[Shooter Subsystem] (B|Drop Ball)
    public static final Axis AimShootHigh = Shooter_controller.getRightTriggerAxis();           //[Shooter Subsystem] (R-Trigger|Aim & Shoot High Goal) 
    public static final Axis AimShootLow = Shooter_controller.getLeftTriggerAxis();             //[Shooter Subsystem] (L-Trigger|Aim & Shoot Low Goal)
    public static final Button ClimbOut = Shooter_controller.getDPadButton(Direction.LEFT);     //[Climber Subsystem] (DPad-Left|Climber Out)
    public static final Button ClimbIn = Shooter_controller.getDPadButton(Direction.RIGHT);     //[Climber Subsystem] (DPad-Right|Climber In)
    public static final Button StatHookClose = Shooter_controller.getYButton();                 //[Climber Subsystem] (Y|Close Hook)
    public static final Button StatHookOpen = Shooter_controller.getAButton();                  //[Climber Subsystem] (A|Open Hook)
    public static final Button Unspool = Shooter_controller.getLeftBumperButton();              //[Climber Subsystem] (Left Bumper|Unspool Climber)
    public static final Button Spool = Shooter_controller.getRightBumperButton();               //[Climber Subsystem] (Right Bumper|Spool Climber)
    public static final Button WinchLock = Shooter_controller.getDPadButton(Direction.UP);      //[Climber Subsystem] (DPad-Up|Lock Winch)
    public static final Button WinchUnlock = Shooter_controller.getDPadButton(Direction.DOWN);  //[Climber Subsystem] (DPad-Down|Lock Unwinch)
    public static final Button GetToNextRung = Shooter_controller.getStartButton();             //[Climber Subsystem] (Start|GetToNextRung Command)
    // public static final Button PullAndGrab = Shooter_controller.getBackButton();                //[Climber Subsystem] (Back|PullAndGrab Command)
    public static final Button TestButton = Shooter_controller.getBackButton();                //[Climber Subsystem] (Back|PullAndGrab Command)


    /* Example Analog Trigger Code NOTE: put this inside your subsystem
    public void periodic() {
        // This method will be called once per scheduler run
        if (Intake_Extension.get() > 0.5)
        {
            if (!triggerDone)
            {
            this.Sol_toggle();
            triggerDone = true;
            }
        }
        else
        {
            triggerDone = false;
        }
    }
    */

//Pneumatics
    public static final int PH_CAN_ID = 1;      //[Pneumatic hub CAN address]    
    public static final int INTAKE_SOLa = 0;    //[Intake Subsystem]
    public static final int INTAKE_SOLb = 8;    //[Intake Subsystem]

    public static final int ARM_FORWARD = 4;    //[Climber Subsystem]
    public static final int ARM_BACK = 5;       //[Climber Subsystem]

    public static final int HOOK_CLOSE = 2;     //[Climber Subsystem] #2
    public static final int HOOK_OPEN = 3;      //[Climber Subsystem] #1

    public static final int WINCH_LOCK = 6;     //[Climber Subsystem]
    public static final int WINCH_UNLOCK = 7;   //[Climber Subsystem]

//Drive Train -- uses spark max CAN ID 1-8 
// EVEN numbers are angle motors, ODD numbers are drive motors
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_MOTOR = 2;
    public static final double DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_OFFSET = -Math.toRadians(66.5+180);
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_DRIVE_MOTOR = 1;
    public static final double DRIVETRAIN_FRONT_RIGHT_MODULE_ENCODER_VOLTAGE_MAX = 3.300;

    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_MOTOR = 4;
    public static final double DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_OFFSET = -Math.toRadians(248.1-180);
    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_DRIVE_MOTOR = 3;
    public static final double DRIVETRAIN_BACK_RIGHT_MODULE_ENCODER_VOLTAGE_MAX = 3.330;

    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_MOTOR = 6;
    public static final double DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_OFFSET = -Math.toRadians(157.0+180);
    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_DRIVE_MOTOR = 5;
    public static final double DRIVETRAIN_FRONT_LEFT_MODULE_ENCODER_VOLTAGE_MAX = 3.36;

    public static final int DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_MOTOR = 8;
    public static final double DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_OFFSET = -Math.toRadians(126.7);
    public static final int DRIVETRAIN_BACK_LEFT_MODULE_DRIVE_MOTOR = 7;
    public static final double DRIVETRAIN_BACK_LEFT_MODULE_ENCODER_VOLTAGE_MAX = 3.256;

    public static final int CLIMBER_MOTOR = 9;

//Shooter constants
// Defaults are used for shooting during the autonomous period (distance tuned for preplaced cargo to hub)
    public static final double DEFAULT_BOTTOM_MOTOR_SPEED = 1.0;
    public static final double DEFAULT_TOP_MOTOR_SPEED = -0.75;
    public static final double DEFAULT_FEED_MOTOR_SPEED = -0.4;
}