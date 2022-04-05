package frc.swerverobot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.math.Vector2;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;

import java.util.function.DoubleSupplier;
import java.lang.Math;

@Deprecated

public class GoToAngleCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrain;
    private final DoubleSupplier forward;
    private final DoubleSupplier strafe;
    private final DoubleSupplier rotation_x;
    private final DoubleSupplier rotation_y;
    private double angle;
//    private final double setPoint;
    private double rotationOutput;
    private boolean usingAngle;
    private double angleTarget;

    // create a pid controller for robot rotation
    private PidController rotationController = new PidController(new PidConstants(0.8/DrivetrainSubsystem.WHEELBASE, 0.0, 0.01/DrivetrainSubsystem.WHEELBASE)); //0.5 0.0 0.008

    public GoToAngleCommand(DrivetrainSubsystem drivetrain,
                        DoubleSupplier forward,
                        DoubleSupplier strafe,
                        DoubleSupplier rotation_x,
                        DoubleSupplier rotation_y) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
        this.rotation_x = rotation_x;
        this.rotation_y = rotation_y;


        rotationController.setInputRange(0.0, 2*Math.PI);
        rotationController.setContinuous(true);

        addRequirements(drivetrain);
    }

    public GoToAngleCommand(DrivetrainSubsystem drivetrain,
            DoubleSupplier forward,
            DoubleSupplier strafe,
            double angle) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
        this.rotation_x = null;
        this.rotation_y = null;
        this.angle = angle;

        rotationController.setInputRange(0.0, 2 * Math.PI);
        rotationController.setContinuous(true);

        addRequirements(drivetrain);
    }


    @Override
    public void initialize() {
        rotationController.reset();
        rotationController.setSetpoint(0.0);

        if (rotation_x == null) {
            double a = angle + drivetrain.getPose().rotation.toRadians();
            if (a < -2*Math.PI) {
                a += 2*Math.PI;
            }
            else if (a > 2*Math.PI) {
                a -= 2*Math.PI;
            }

            usingAngle = true;
            angleTarget = a;
        }
    }

    @Override
    public void execute() {
        double minVal = 0.07;

        double fw = forward.getAsDouble();
        double stf = strafe.getAsDouble();
        double rot_x;
        double rot_y;    

        if(rotation_x != null) {
            rot_x = rotation_x.getAsDouble();
            rot_y = rotation_y.getAsDouble();    
        }
        else {
            rot_x = 0;
            rot_y = 0;
        }

        if (Math.abs(fw) < minVal) {
            fw = 0.0;
        }

        if (Math.abs(stf) < minVal) {
            stf = 0.0;
        }

        if (usingAngle) {
            if (Math.abs(angleTarget) >= minVal) {
                rotationController.setSetpoint(angleTarget);
            }
        }
        else {
            if (Math.abs(rot_x) >= minVal || Math.abs(rot_y) >= minVal) {
                rotationController.setSetpoint(Math.atan2(rot_x, rot_y));
            }
        }


        rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.01);


        // drive command, change values here to change robot speed or field oriented
            drivetrain.drive(
                    new Vector2(fw, stf),
                    -rotationOutput,
                    true
            );

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }

    // public boolean isFinished() {
    //     return Math.abs(rotationOutput) < 0.07;
    //     // return false;
    // }

    // public synchronized void resetPose() {
    //     drivetrain.resetPose();
    //     rotationController.setSetpoint(0); //drivetrain.getPose().rotation.toRadians());
    // }
}
