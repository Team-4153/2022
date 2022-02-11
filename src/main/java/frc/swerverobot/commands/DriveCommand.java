package frc.swerverobot.commands;

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
    private boolean rotating;

    // create a pid controller for robot rotation
    private PidController rotationController = new PidController(new PidConstants(0.8, 0.0, 0.005)); //0.5 0.0 0.008

    public DriveCommand(DrivetrainSubsystem drivetrain,
                        DoubleSupplier forward,
                        DoubleSupplier strafe,
                        DoubleSupplier rotation) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
        this.rotation = rotation;

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

        // if the driver isn't rotating the robot, use pid to keep robot orientation constant (rotation = 0)
        if (-0.03 <= rotation.getAsDouble() && rotation.getAsDouble() <= 0.03) {
            if (rotating) {
                rotationController.setSetpoint(drivetrain.getPose().rotation.toRadians());
                rotating = false;
            }

            double rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.01);

        // drive command, change values here to change robot speed or field oriented
            drivetrain.drive(
                    new Vector2(
                            forward.getAsDouble(),
                            strafe.getAsDouble()
                    ),
                    -rotationOutput,
                    true
            );
        }

        // if the driver is rotating the robot, just get the rotation value and plug it into the .drive()
        else{
            rotating = true;
            drivetrain.drive(
                    new Vector2(
                            forward.getAsDouble(),
                            strafe.getAsDouble()
                    ),
                    rotation.getAsDouble(),
                    true
            );
        }

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }
}
