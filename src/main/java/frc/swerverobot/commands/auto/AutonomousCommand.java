package frc.swerverobot.commands.auto;

import java.sql.DriverAction;
import java.util.function.BooleanSupplier;
import frc.swerverobot.subsystems.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutonomousCommand extends CommandBase{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final PossibleAutos choice;

    public AutonomousCommand(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, PossibleAutos choice) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.choice = choice;

        addRequirements(drivetrain, shooter, intake);
    }


    @Override
    public void initialize() {
        switch(choice) {
            case LEFT:
            case CENTER:
            case RIGHT:
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

}
