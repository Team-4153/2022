package frc.swerverobot;

//Shooter Subsystem - Color Sensor
import edu.wpi.first.wpilibj.I2C;

public class RobotMap {
// define all ports and constants

//Shooter Subsytem
    public static final int TopMotorPort = 1; // The Number is the RIO PWM port
    public static final int BottomMotorPort = 2; // The Number is the RIO PWM port
    public static final int StorageMotorPort = 3; // The Number is the RIO PWM port
    public static final I2C.Port i2cPort = I2C.Port.kOnboard;


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
}
