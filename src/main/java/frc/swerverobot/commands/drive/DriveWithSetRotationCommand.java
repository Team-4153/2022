package frc.swerverobot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;
import org.frcteam2910.common.math.Vector2;

import java.util.function.DoubleSupplier;

@Deprecated

public class DriveWithSetRotationCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrain;
    private DoubleSupplier forward;
    private DoubleSupplier strafe;
    private double setRotation;

    private PidController rotationController = new PidController(new PidConstants(0.5, 0.0, 0.02));

    public DriveWithSetRotationCommand(DrivetrainSubsystem drivetrain, DoubleSupplier forward, DoubleSupplier strafe, double setRotation) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
        this.setRotation = setRotation;

        rotationController.setInputRange(0.0, 2*Math.PI);
        rotationController.setContinuous(true);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        rotationController.reset();
        rotationController.setSetpoint(setRotation);
    }

    @Override
    public void execute() {
        double rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.02);
        drivetrain.drive(new Vector2(forward.getAsDouble(), strafe.getAsDouble()), rotationOutput, true);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }
}