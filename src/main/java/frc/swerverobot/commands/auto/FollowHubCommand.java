package frc.swerverobot.commands.auto;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.math.Vector2;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;

import java.lang.Math;

@Deprecated
@SuppressWarnings("unused")

public class FollowHubCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrain;
    private double targetAngle;
    private double rotationOutput;
    private long lastCorrection;

    // create a pid controller for robot rotation
    // private PidController rotationController = new PidController(new PidConstants(0.8/DrivetrainSubsystem.WHEELBASE, 0, 0.01/DrivetrainSubsystem.WHEELBASE)); // 0.8, 0.0, 0.01)); //0.5 0.0 0.008
    private PidController rotationController = new PidController(new PidConstants(1.0, 0.0, 0.01)); // 0.8, 0.0, 0.01)); //0.5 0.0 0.008

    public FollowHubCommand(DrivetrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;
        rotationController.setInputRange(0.0, 2*Math.PI);
        rotationController.setContinuous(true);

        addRequirements(drivetrain);
    }


    @Override
    public void initialize() {
        rotationController.reset();
        calculateCorrection(RobotController.getFPGATime());
    }

    @Override
    public void execute() {
        long tstamp = RobotController.getFPGATime();
        calculateCorrection(tstamp);

        rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.01);

        // drive command, change values here to change robot speed or field oriented
            drivetrain.drive(
                    new Vector2(0, 0),
                    -rotationOutput,
                    false
            );
    }

    @Override
    public void end(boolean interrupted) {
//        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }

    public boolean isFinished() {
        return (Math.abs(rotationOutput) < 0.05); // && Math.abs(rotationOutput) < 0.02;
        // return false;
    }

    protected void calculateCorrection(long timestamp) {
        // if (timestamp < lastCorrection + 100000) {  // 100 ms per correction
        //     return;
        // }

        lastCorrection = timestamp;

        double target_x = SmartDashboard.getNumber("ShooterTarget/TargetOff", 0);
//        double by = SmartDashboard.getNumber("IntakeBall/BallY", 0);
//        double tr = SmartDashboard.getNumber("IntakeBall/BallRadius", 0);

        double angleOffset = target_x * 30 * Math.PI / 180; // horizontal field of view for HD3000 is 60 degrees
        targetAngle = -angleOffset + drivetrain.getPose().rotation.toRadians();

        rotationController.setSetpoint(targetAngle);
    }
}
