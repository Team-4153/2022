package frc.swerverobot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.math.Vector2;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;

import java.util.function.DoubleSupplier;
import java.lang.Math;

public class PathCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrain;
    private final double forward;
    private final double strafe;
    private final double rotation;
//    private final double setPoint;
    private double rotationOutput;
    private double xOutput;
    private double yOutput;

    // create a pid controller for robot rotation
    private PidController rotationController = new PidController(new PidConstants(0.8, 0.0, 0.01)); //0.5 0.0 0.008
    private PidController xController = new PidController(new PidConstants(0.4, 0, 0.01));
    private PidController yController = new PidController(new PidConstants(0.4, 0, 0.01));


    public PathCommand(DrivetrainSubsystem drivetrain,
                        double forward,
                        double strafe,
                        double rotation) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.strafe = strafe;
//        this.setPoint = setPoint;
        this.rotation = rotation;

        rotationController.setInputRange(0.0, 2*Math.PI);
        rotationController.setContinuous(true);

//        xController.setInputRange(0.0, 2*Math.PI);
//        xController.setContinuous(true);

//        yController.setInputRange(0.0, 2*Math.PI);
//        yController.setContinuous(true);

        addRequirements(drivetrain);
    }


    @Override
    public void initialize() {
        rotationController.reset();
        xController.reset();
        yController.reset();

        double currentX = drivetrain.getPose().translation.x;
        double currentY = drivetrain.getPose().translation.y;

        rotationController.setSetpoint(rotation);
        xController.setSetpoint(currentX + forward);
        yController.setSetpoint(currentY + strafe);
    }

    @Override
    public void execute() {
        double currentX = drivetrain.getPose().translation.x;
        double currentY = drivetrain.getPose().translation.y;

        // rotationController.setSetpoint(rotation);
        // xController.setSetpoint(currentX + forward);
        // yController.setSetpoint(currentY + strafe);


        rotationOutput = rotationController.calculate(drivetrain.getPose().rotation.toRadians(), 0.01);
        xOutput = xController.calculate(currentX, 0.01);
        yOutput = yController.calculate(currentY, 0.01);


        // drive command, change values here to change robot speed or field oriented
            drivetrain.drive(
                    new Vector2(xOutput, yOutput),
                    -rotationOutput,
                    true
            );

    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(Vector2.ZERO, 0.0, false);
    }

    public boolean isFinished() {
        return Math.abs(rotationOutput) < 0.01 && Math.abs(xOutput) < 0.01 && Math.abs(yOutput) < 0.01;
    }

}
