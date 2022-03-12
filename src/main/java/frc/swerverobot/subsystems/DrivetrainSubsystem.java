package frc.swerverobot.subsystems;

import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.RobotController;
import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.climb.States;
import frc.swerverobot.drivers.T265;

import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.kinematics.ChassisVelocity;
import org.frcteam2910.common.kinematics.SwerveKinematics;
import org.frcteam2910.common.kinematics.SwerveOdometry;
import org.frcteam2910.common.math.RigidTransform2;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.drivers.Mk2SwerveModuleBuilder;
import org.frcteam2910.common.util.HolonomicDriveSignal;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frcteam2910.common.control.*;
import org.frcteam2910.common.util.*;

import java.util.Optional;

import static frc.swerverobot.RobotMap.*;

public class DrivetrainSubsystem extends SubsystemBase implements UpdateManager.Updatable {
    // define the trackwidth (short side in our case) and wheelbase (long side in our case) ratio of the robot
    public static final double TRACKWIDTH = 19.685; //  22.5; // 1.0;
    public static final double WHEELBASE = 27.159; // 22.5; // 1.0;
    private double angle = 0;
    private static boolean locked;

    public static final DrivetrainFeedforwardConstants FEEDFORWARD_CONSTANTS = new DrivetrainFeedforwardConstants(
            0.042746,
            0.0032181,
            0.30764
    );

    public static final TrajectoryConstraint[] TRAJECTORY_CONSTRAINTS = {
        new FeedforwardConstraint(11.0, FEEDFORWARD_CONSTANTS.getVelocityConstant(), FEEDFORWARD_CONSTANTS.getAccelerationConstant(), false),
        new MaxAccelerationConstraint(12.5 * 12.0),
        new CentripetalAccelerationConstraint(15 * 12.0)
    };

    private static final int MAX_LATENCY_COMPENSATION_MAP_ENTRIES = 25;

    private final HolonomicMotionProfiledTrajectoryFollower follower = new HolonomicMotionProfiledTrajectoryFollower(
        new PidConstants(1.0, 0.0, 0.01),
        new PidConstants(1.0, 0.0, 0.01),
        new HolonomicFeedforward(FEEDFORWARD_CONSTANTS)
    );

    // define the pid constants for each rotation motor
    private static final PidConstants rotation2 = new PidConstants(1.0, 0.0, 0.001);
    private static final PidConstants rotation4 = new PidConstants(1.0, 0.0, 0.001);
    private static final PidConstants rotation6 = new PidConstants(1.0, 0.0, 0.5);
    private static final PidConstants rotation8 = new PidConstants(1.0, 0.0, 0.5);


    //
    // initialize each module using Mk2SwerveModuleBuilder
    //
            // check org/frcteam2910/common/robot/drivers/Mk2SwerveModuleBuilder
            // to see the initialization arguments
    private final SwerveModule frontLeftModule =
            new Mk2SwerveModuleBuilder(new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0))
                    .angleMotor(
                            new CANSparkMax(DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            rotation6, 18.0 / 1.0, DRIVETRAIN_FRONT_LEFT_MODULE_ANGLE_OFFSET, DRIVETRAIN_FRONT_LEFT_MODULE_ENCODER_VOLTAGE_MAX)
                    .driveMotor(
                            new CANSparkMax(DRIVETRAIN_FRONT_LEFT_MODULE_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            Mk2SwerveModuleBuilder.MotorType.NEO)
                    .build();
    private final SwerveModule frontRightModule =
            new Mk2SwerveModuleBuilder(new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
                    .angleMotor(
                            new CANSparkMax(DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            rotation2, 18.0/1.0, DRIVETRAIN_FRONT_RIGHT_MODULE_ANGLE_OFFSET, DRIVETRAIN_FRONT_RIGHT_MODULE_ENCODER_VOLTAGE_MAX)    
                    .driveMotor(
                            new CANSparkMax(DRIVETRAIN_FRONT_RIGHT_MODULE_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            Mk2SwerveModuleBuilder.MotorType.NEO)
                    .build();
    private final SwerveModule backLeftModule =
            new Mk2SwerveModuleBuilder(new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0))
                    .angleMotor(
                            new CANSparkMax(DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            rotation8, 18.0/1.0, DRIVETRAIN_BACK_LEFT_MODULE_ANGLE_OFFSET, DRIVETRAIN_BACK_LEFT_MODULE_ENCODER_VOLTAGE_MAX)
                    .driveMotor(
                            new CANSparkMax(DRIVETRAIN_BACK_LEFT_MODULE_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            Mk2SwerveModuleBuilder.MotorType.NEO)
                    .build();
    private final SwerveModule backRightModule =
            new Mk2SwerveModuleBuilder(new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
                    .angleMotor(
                            new CANSparkMax(DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            rotation4, 18.0/1.0, DRIVETRAIN_BACK_RIGHT_MODULE_ANGLE_OFFSET, DRIVETRAIN_BACK_RIGHT_MODULE_ENCODER_VOLTAGE_MAX)
                    .driveMotor(
                            new CANSparkMax(DRIVETRAIN_BACK_RIGHT_MODULE_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                            Mk2SwerveModuleBuilder.MotorType.NEO)
                    .build();

    private final SwerveModule[] modules = {frontLeftModule, frontRightModule, backLeftModule, backRightModule};

    // set wheel positions
    private final SwerveKinematics kinematics = new SwerveKinematics(
            new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0), // Front Left
            new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0), // Front Right
            new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0), // Back Left
            new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0) // Back Right
    );
    private final SwerveOdometry odometry = new SwerveOdometry(kinematics, RigidTransform2.ZERO);

    @GuardedBy("kinematicsLock")
    private Vector2 velocity = Vector2.ZERO;
    @GuardedBy("kinematicsLock")
    private double angularVelocity = 0.0;
    @GuardedBy("kinematicsLock")
    private final InterpolatingTreeMap<InterpolatingDouble, RigidTransform2> latencyCompensationMap = new InterpolatingTreeMap<>();

    private final Object sensorLock = new Object();
    @GuardedBy("sensorLock")
    public final ADIS16470_IMU adisGyro = RobotMap.imu;

    public final T265 t265 = new T265();
    private final boolean useT265 = false;

    private final Object kinematicsLock = new Object();
    @GuardedBy("kinematicsLock")
    private RigidTransform2 pose = RigidTransform2.ZERO;

    private final Object stateLock = new Object();
    @GuardedBy("stateLock")
    private HolonomicDriveSignal driveSignal = null;

    // Logging stuff
    private NetworkTableEntry poseXEntry;
    private NetworkTableEntry poseYEntry;
    private NetworkTableEntry poseAngleEntry;

    private NetworkTableEntry[] moduleAngleEntries = new NetworkTableEntry[modules.length];

    public DrivetrainSubsystem() {
        synchronized (sensorLock) { 
//            navX.setInverted(true);
        }

        ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");
        poseXEntry = tab.add("Pose X", 0.0)
                .withPosition(0, 0)
                .withSize(1, 1)
                .getEntry();
        poseYEntry = tab.add("Pose Y", 0.0)
                .withPosition(0, 1)
                .withSize(1, 1)
                .getEntry();
        poseAngleEntry = tab.add("Pose Angle", 0.0)
                .withPosition(0, 2)
                .withSize(1, 1)
                .getEntry();
                

        // this section adds each swerve module to the driver station's shuffleboard
        ShuffleboardLayout frontLeftModuleContainer = tab.getLayout("Front Left Module", BuiltInLayouts.kList)
                .withPosition(1, 0)
                .withSize(2, 3);
        moduleAngleEntries[0] = frontLeftModuleContainer.add("Angle", 0.0).getEntry();

        ShuffleboardLayout frontRightModuleContainer = tab.getLayout("Front Right Module", BuiltInLayouts.kList)
                .withPosition(3, 0)
                .withSize(2, 3);
        moduleAngleEntries[1] = frontRightModuleContainer.add("Angle", 0.0).getEntry();

        ShuffleboardLayout backLeftModuleContainer = tab.getLayout("Back Left Module", BuiltInLayouts.kList)
                .withPosition(5, 0)
                .withSize(2, 3);
        moduleAngleEntries[2] = backLeftModuleContainer.add("Angle", 0.0).getEntry();

        ShuffleboardLayout backRightModuleContainer = tab.getLayout("Back Right Module", BuiltInLayouts.kList)
                .withPosition(7, 0)
                .withSize(2, 3);
        moduleAngleEntries[3] = backRightModuleContainer.add("Angle", 0.0).getEntry();
    }

    public RigidTransform2 getPose() {
        synchronized (kinematicsLock) {
            return pose;
        }
    }

    public void resetPose() {
            synchronized (kinematicsLock) {
                    pose = RigidTransform2.ZERO;

            odometry.resetPose(pose);
            t265.reset();
            resetGyroAngle();
            }
    }

/*
    public void resetPose(RigidTransform2 pose) {
        synchronized (kinematicsLock) {
            this.pose = pose;
            odometry.resetPose(pose);
        }
    }
*/

    public Vector2 getVelocity() {
        synchronized (kinematicsLock) {
            return velocity;
        }
    }

    public double getAngularVelocity() {
        synchronized (kinematicsLock) {
            return angularVelocity;
        }
    }

    public void drive(Vector2 translationalVelocity, double rotationalVelocity, boolean fieldOriented) {
        synchronized (stateLock) {
            driveSignal = new HolonomicDriveSignal(translationalVelocity, rotationalVelocity, fieldOriented);
        }
    }

    public void resetGyroAngle() {
        synchronized (sensorLock) {
                adisGyro.reset();
        }
    }

    public void setAngle(double angle) {
        this.angle = angle;
        t265.setRotation(angle);
    }

    public double getGyroAngle() {
        synchronized (sensorLock) {
            if(useT265) {
                return t265.getRotation();
            }
            else {
                return adisGyro.getAngle() + angle;
            }
        }
    }

    @Override
    public void update(double timestamp, double dt) {
        // updates the odometry and drive signal for the robot at time intervals of length dt
        updateOdometry(timestamp, dt);

        HolonomicDriveSignal driveSignal;
        Optional<HolonomicDriveSignal> trajectorySignal = follower.update(
                getPose(),
                getVelocity(),
                getAngularVelocity(),
                timestamp,
                dt
        );
        if (trajectorySignal.isPresent()) {
            driveSignal = trajectorySignal.get();
            driveSignal = new HolonomicDriveSignal(
                     driveSignal.getTranslation().scale(1.0 / RobotController.getBatteryVoltage()),
                     driveSignal.getRotation() / RobotController.getBatteryVoltage(),
                     driveSignal.isFieldOriented()
             );
             SmartDashboard.putNumber("drive signal x", driveSignal.getTranslation().x);
             SmartDashboard.putNumber("drive signal y", driveSignal.getTranslation().y);
             SmartDashboard.putNumber("drive signal rot", driveSignal.getRotation());

        } else {
            synchronized (stateLock) {
                driveSignal = this.driveSignal;
            }
        }


        updateModules(driveSignal, dt);
    }

    private void updateOdometry(double timestamp, double dt) {
        // updates the module velocities at time intervals of length dt
        Vector2[] moduleVelocities = new Vector2[modules.length];
        for (int i = 0; i < modules.length; i++) {
            var module = modules[i];
            module.updateSensors();

            moduleVelocities[i] = Vector2.fromAngle(Rotation2.fromRadians(module.getCurrentAngle())).scale(module.getCurrentVelocity());
        }

        // updates robot angle based on gyro at intervals of length dt
        Rotation2 angle;
        double angularVelocity;
        synchronized (sensorLock) {
            if (useT265) {
                angle = Rotation2.fromRadians(getGyroAngle());
                angularVelocity = t265.getAngularVelocity();
            } else {
                angle = Rotation2.fromDegrees(getGyroAngle());
                angularVelocity = adisGyro.getRate();
            }
        }

        SmartDashboard.putNumber(String.format("gyro reading"), angle.toDegrees());     // put the gyro reading into the smartdashboard
        synchronized (kinematicsLock) {
            if (useT265) {
                this.pose = t265.getPose();
                this.velocity = t265.getVelocity(); //velocity.getTranslationalVelocity();
                this.angularVelocity = angularVelocity; // t265.getAngularVelocity(); // angularVelocity;
            } else {
                ChassisVelocity velocity = kinematics.toChassisVelocity(moduleVelocities);
                this.pose = odometry.update(angle, dt, moduleVelocities);
                this.velocity = velocity.getTranslationalVelocity();
                this.angularVelocity = angularVelocity;
            }
        }
    }

    private void updateModules(HolonomicDriveSignal signal, double dt) {
        ChassisVelocity velocity;
        if (signal == null) {
            velocity = new ChassisVelocity(Vector2.ZERO, 0.0);
        } else if (signal.isFieldOriented()) {
        // if the robot is field oriented, shift translation to account for the rotation
 //               double rot = signal.getRotation() - getPose().rotation.toRadians();
                
            velocity = new ChassisVelocity(
                    signal.getTranslation().rotateBy(getPose().rotation.inverse()),
                    signal.getRotation()
            );
        } else {
        // if the robot is robot oriented, just get translation and rotation
            velocity = new ChassisVelocity(signal.getTranslation(), signal.getRotation());
        }

        Vector2[] moduleOutputs = kinematics.toModuleVelocities(velocity);
        SwerveKinematics.normalizeModuleVelocities(moduleOutputs, 1.0);

        for (int i = 0; i < modules.length; i++) {
            var module = modules[i];
            module.setTargetVelocity(moduleOutputs[i]);
            module.updateState(dt);
        }
    }

    @Override
    public void periodic() {
        // this function is called once per scheduler run
        var pose = getPose();
        poseXEntry.setDouble(pose.translation.x);
        poseYEntry.setDouble(pose.translation.y);
        poseAngleEntry.setDouble(pose.rotation.toDegrees());

        for (int i = 0; i < modules.length; i++) {
            var module = modules[i];
            if(locked){
                moduleAngleEntries[i].setDouble(((i+1)*90)-45);
            }
            else{
                moduleAngleEntries[i].setDouble(Math.toDegrees(module.getCurrentAngle()));
            }
        }
    }
    public void lock(States in){
        switch(in){
            case LOCKED:
                locked = true;
                break;
            case UNLOCKED:
                locked = false;
                break;
            case TOGGLE:
                locked = !locked;
                break;
        }
    }

}
