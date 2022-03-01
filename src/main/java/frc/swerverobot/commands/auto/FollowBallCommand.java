package frc.swerverobot.commands.auto;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.math.Vector2;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.lang.Math;

public class FollowBallCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrain;
    private final DoubleSupplier forward;
    private final BooleanSupplier pressed;
    private double targetAngle;
    private double rotationOutput;
    private long lastCorrection;

    // create a pid controller for robot rotation
    private PidController rotationController = new PidController(new PidConstants(0.8/DrivetrainSubsystem.WHEELBASE, 0, 0.01/DrivetrainSubsystem.WHEELBASE)); // 0.8, 0.0, 0.01)); //0.5 0.0 0.008

    public FollowBallCommand(DrivetrainSubsystem drivetrain, DoubleSupplier fwd, BooleanSupplier pressed) {
        this.drivetrain = drivetrain;
        this.forward = fwd;
        this.pressed = pressed;
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
        double minVal = 0.07;

        calculateCorrection(RobotController.getFPGATime());
        double fw = 0, st = 0;
        if (forward != null) {
            double v = forward.getAsDouble();
            if (Math.abs(fw) < minVal) {
                fw = 0;
            }

            if (targetAngle != 0) {
                fw = -v; //-v / Math.tan(targetAngle);
                st = 0; // v * Math.tan(targetAngle);
//                SmartDashboard.putNumber("IntakeBall/FW", fw);
//                SmartDashboard.putNumber("IntakeBall/ST", st);
            } else {
                fw = -v;
                st = 0;
            }
        }
 
        rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.01);

        // drive command, change values here to change robot speed or field oriented
            drivetrain.drive(
                    new Vector2(fw, st),
                    -rotationOutput,
                    false
            );
        }

    @Override
    public void end(boolean interrupted) {
//        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }

    public boolean isFinished() {
        return !pressed.getAsBoolean(); // && Math.abs(rotationOutput) < 0.02;
    }

    protected void calculateCorrection(long timestamp) {
        if (timestamp < lastCorrection + 100000) {  // 100 ms per correction
            return;
        }

        lastCorrection = timestamp;
        double minVal = 0.01;

        double bx = SmartDashboard.getNumber("IntakeBall/BallX", 0);
//        double by = SmartDashboard.getNumber("IntakeBall/BallY", 0);
        double br = SmartDashboard.getNumber("IntakeBall/BallRadius", 0);

        if (br > minVal) {
            double angleOffset = bx * 30 * Math.PI / 180;   // horizontal field of view for HD3000 is 60 degrees
            targetAngle = -angleOffset + drivetrain.getPose().rotation.toRadians();
 //           SmartDashboard.putNumber("IntakeBall/TargetAngle", targetAngle * 180 / Math.PI);
 //           SmartDashboard.putNumber("IntakeBall/BallAngle", angleOffset * 180 / Math.PI);
 //           SmartDashboard.putNumber("IntakeBall/Angle", drivetrain.getPose().rotation.toDegrees());
            rotationController.setSetpoint(targetAngle);
        }
    }
}
