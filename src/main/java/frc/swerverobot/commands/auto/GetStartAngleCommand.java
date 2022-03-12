package frc.swerverobot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.commands.drive.*;
import java.lang.Math;

public class GetStartAngleCommand extends CommandBase{
    private final DrivetrainSubsystem drivetrain;
    private final PossibleAutos choice;
    private double angle;

    public GetStartAngleCommand(DrivetrainSubsystem drivetrain, PossibleAutos choice){
        this.drivetrain = drivetrain;
        this.choice = choice;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        switch (choice){
            case LEFT:
                angle = 223.5;
                break;
            case CENTER:
                angle = 226.5;
                break;
            case RIGHT:
                angle = 271.5;
                break;
            default:
                angle = 0;
        }

        new GoToAngleCommand(drivetrain, () -> 0.0, () -> 0.0, () -> 1.0, () -> -Math.tan(90-angle));
        drivetrain.resetGyroAngle();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

}