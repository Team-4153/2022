package frc.swerverobot;

//Shooter Subsystem - Color Sensor
import edu.wpi.first.wpilibj.I2C;



import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class RobotMap {
// define all ports and constants

//Shooter Subsytem
    public static final int TopMotorPort = 0; // The Number is the RIO PWM port
    public static final int BottomMotorPort = 1; // The Number is the RIO PWM port
    public static final int StorageMotorPort = 2; // The Number is the RIO PWM port
    public static final I2C.Port i2cPort = I2C.Port.kOnboard; //Color Sensor (Only one I2C port on the robot)

//Intake Subsystem
    public static final int Intake_Motor_PWM = 3; // PWM for intake motor

    // controls
    public static final Controller Driver_controller = new XboxController(0);
    public static final Button Intake_Extension = Driver_controller.getLeftBumperButton();
    public static final Button Intake_Roller = Driver_controller.getRightBumperButton();



// EVEN numbers are angle motors, ODD numbers are drive motors
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_MOTOR = 2;
    public static final double DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_OFFSET = -Math.toRadians(191.3);
    public static final int DRIVETRAIN_FRONT_RIGHT_MODULE_DRIVE_MOTOR = 1;

    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_MOTOR = 4;
    public static final double DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_OFFSET = -Math.toRadians(272.9);
    public static final int DRIVETRAIN_BACK_RIGHT_MODULE_DRIVE_MOTOR = 3;

    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_MOTOR = 6;
    public static final double DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_OFFSET = -Math.toRadians(165.7+180);
    public static final int DRIVETRAIN_FRONT_LEFT_MODULE_DRIVE_MOTOR = 5;

    public static final int DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_ENCODER = -1;
    public static final int DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_MOTOR = 8;
    public static final double DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_OFFSET = -Math.toRadians(357.2);
    public static final int DRIVETRAIN_BACK_LEFT_MODULE_DRIVE_MOTOR = 7;

    //Climber Stuff
    public static final int CLIMBER_MOTOR = 9;
    public static final int CLIMBER_PISTON = 1;
    public static final int CLIMBER_SWITCH = 0;
}
