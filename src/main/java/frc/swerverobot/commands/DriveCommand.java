package frc.swerverobot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.math.Vector2;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrain;
    private final DoubleSupplier forward;
    private final DoubleSupplier strafe;
    private final DoubleSupplier rotation;
    private final DoubleSupplier leftTrigger;
    private final DoubleSupplier rightTrigger;
    private boolean rotating;

    // create a pid controller for robot rotation
    private PidController rotationController = new PidController(new PidConstants(0.8, 0.0, 0.01)); //0.5 0.0 0.008

    public DriveCommand(DrivetrainSubsystem drivetrain,
                        DoubleSupplier forward,
                        DoubleSupplier strafe,
                        DoubleSupplier rotation,
                        DoubleSupplier leftTrigger,
                        DoubleSupplier rightTrigger) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;

        rotationController.setInputRange(0.0, 2*Math.PI);
        rotationController.setContinuous(true);

        addRequirements(drivetrain);
    }


    @Override
    public void initialize() {
        rotationController.reset();
        rotationController.setSetpoint(0.0);
        rotating = true;
    }

    @Override
    public void execute() {
        boolean keepAngle = true;
        double minVal = 0.07;

        double fw = forward.getAsDouble();
        double stf = strafe.getAsDouble();
        double rot = rotation.getAsDouble();

        double speed;

        if (leftTrigger.getAsDouble() > 0.05) {
            speed = 0.5;
        }
        else if (rightTrigger.getAsDouble() > 0.05) {
            speed = 1.5;
        }
        else {
            speed = 1;
        }

        if (Math.abs(fw) < minVal) {
            fw = 0.0;
        }

        if (Math.abs(stf) < minVal) {
            stf = 0.0;
        }

        if (Math.abs(rot) < minVal) {
            rot = 0.0001;
        }

//        if (Math.abs(fw) < minVal && Math.abs(stf) < minVal && Math.abs(rot) < minVal) {
//            drivetrain.drive(Vector2.ZERO, 0.0001, false);
//            return;
//        }

        SmartDashboard.putNumber("forward", fw);
        SmartDashboard.putNumber("strafe", stf);
        SmartDashboard.putNumber("rotation", rot);


        // if the driver isn't rotating the robot, use pid to keep robot orientation constant (rotation = 0)
        if (keepAngle && Math.abs(rot) < minVal) {
            if (rotating || drivetrain.isAngleReset()) {
//                rotationController.setSetpoint(drivetrain.getPose().rotation.toRadians());
                rotationController.setSetpoint(drivetrain.getPose().rotation.toRadians());
                rotating = false;
                drivetrain.unsetAngleReset();
            }

            double rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.01);


        // drive command, change values here to change robot speed or field oriented
            drivetrain.drive(
                    new Vector2(
                            fw * speed,
                            stf * speed
                    ),
                    -rotationOutput,
                    true
            );
        }

        // if the driver is rotating the robot, just get the rotation value and plug it into the .drive()
        else{
            rotating = true;
            drivetrain.drive(
                    new Vector2(fw * speed, stf * speed),
                    rot,
                    true
            );
        }

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }

}
