package frc.swerverobot;

//Sensor Library
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.ADIS16470_IMU;

//Controller Libraries
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class RobotMap {
//Define all ports and constants of a component, put in correct catagory, label subsystem(s) used

//Sensors
    public static final ADIS16470_IMU imu = new ADIS16470_IMU(); //Gyro and acceleration sensor
    public static final I2C.Port i2cPort = I2C.Port.kOnboard; //[Shooter Subsystem] Color Sensor (Only one I2C port on the robot)

//PWM Motors
    public static final int TopMotorPort = 0; //[Shooter Subsystem] The Number is the RIO PWM port 
    public static final int BottomMotorPort = 1; //[Shooter Subsystem] The Number is the RIO PWM port
    public static final int FeedMotorPort = 2; //[Shooter Subsystem] The Number is the RIO PWM port
    public static final int Intake_Motor_PWM = 3; //[Intake Subsystem] PWM for intake motor
    public static final int CLIMBER_MOTOR = 9; //[Climber Subsystem] The Number is the RIO PWM port

    //Controls
    public static final Controller Driver_controller = new XboxController(0);//[Controller]
    public static final Axis Intake_Extension = Driver_controller.getLeftTriggerAxis();//[Intake Subsystem]
    public static final Button Intake_Roller = Driver_controller.getRightBumperButton();//[Intake Subsystem]

    //Pneumatics
    public static final int PH_CAN_ID = 1;//[Pneumatic hub CAN address]    
    public static final int INTAKE_SOLa = 1;//[Intake Subsystem]
    public static final int INTAKE_SOLb = 2;//[Intake Subsystem]
    public static final int CLIMBER_ARMa = 3;//[Climber Subsystem]
    public static final int CLIMBER_ARMb = 4;//[Climber Subsystem]
    public static final int HOOKa = 5;//[Climber Subsystem]
    public static final int HOOKb = 6;//[Climber Subsystem]
    public static final int WINCH_SOLa = 7;//[Climber Subsystem]
    public static final int WINCH_SOLb = 8;//[Climber Subsystem]

//Drive Train -- uses spark max CAN ID 1-8 
// EVEN numbers are angle motors, ODD numbers are drive motors
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_MOTOR = 2;
    public static final double DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_OFFSET = -Math.toRadians(236.5);
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_DRIVE_MOTOR = 1;
    public static final double DRIVETRAIN_FRONT_RIGHT_MODULE_ENCODER_VOLTAGE_MAX = 3.300;

    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_MOTOR = 4;
    public static final double DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_OFFSET = -Math.toRadians(73.1);
    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_DRIVE_MOTOR = 3;
    public static final double DRIVETRAIN_BACK_RIGHT_MODULE_ENCODER_VOLTAGE_MAX = 3.330;

    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_MOTOR = 6;
    public static final double DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_OFFSET = -Math.toRadians(76.9);
    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_DRIVE_MOTOR = 5;
    public static final double DRIVETRAIN_FRONT_LEFT_MODULE_ENCODER_VOLTAGE_MAX = 3.36;

    public static final int DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_MOTOR = 8;
    public static final double DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_OFFSET = -Math.toRadians(203.0-180);
    public static final int DRIVETRAIN_BACK_LEFT_MODULE_DRIVE_MOTOR = 7;
    public static final double DRIVETRAIN_BACK_LEFT_MODULE_ENCODER_VOLTAGE_MAX = 3.256;

//Climber Stuff TODO: fix
    public static final int CLIMBER_SWITCH = 2;
}
