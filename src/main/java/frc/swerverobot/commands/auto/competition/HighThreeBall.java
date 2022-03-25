package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.drive.GoToAngleCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.shooter.ManualShoot;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@Deprecated
@SuppressWarnings("unused")

public class HighThreeBall extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private double angle;

    public HighThreeBall(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;

        addRequirements(drivetrain);

        addCommands(
            new IntakeCommand(intake, false).withTimeout(0.1),
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.1),
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1),
            new IntakeCommand(intake, true).withTimeout(0.5),
            new WaitCommand(0.3),
            new ManualShoot(shooter, -0.4, 0.5, -1),    // set to low goal for testing SWITCH TO ACTUAL NUMBERS LATER!!!!!!
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, -2*Math.PI/3),
            new IntakeCommand(intake, false).withTimeout(0.2),
            new DriveCommand(drivetrain, () -> 0.25, () -> 0.5, () -> 0, () -> 0, () -> 0).withTimeout(1.5),
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),
            new IntakeCommand(intake, true).withTimeout(0.5),
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, Math.PI/3),
            new ManualShoot(shooter, -0.4, 0.5, -1)
        );
    }

}

