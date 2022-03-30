package frc.swerverobot.commands.auto;

import java.lang.Thread.State;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.swerverobot.commands.climb.ArmPositionCommand;
import frc.swerverobot.commands.climb.SpoolCommand;
import frc.swerverobot.commands.climb.States;
import frc.swerverobot.commands.climb.StaticHookCommand;
import frc.swerverobot.commands.climb.UnspoolCommand;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.shooter.ManualShoot;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.ClimberSubsystem;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

@Deprecated
@SuppressWarnings("unused")


public class TestCommand extends SequentialCommandGroup{
    public TestCommand(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, ClimberSubsystem climb) {
        addCommands(
            new DriveCommand(drivetrain, () -> 0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(2),
            new DriveCommand(drivetrain, () -> 0, () -> 0.5, () -> 0, () -> 0, () -> 0).withTimeout(2),
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.2),

            new IntakeCommand(intake, false).withTimeout(2),
            new IntakeCommand(intake, true).withTimeout(1),

            new ManualShoot(shooter, -0.5, 0.5, -0.5),
            new WaitCommand(0.5),

            new ArmPositionCommand(climb, States.UNLOCKED),
            new WaitCommand(1),
            new ArmPositionCommand(climb, States.LOCKED),

            new UnspoolCommand(climb).withTimeout(1.5),
            new SpoolCommand(climb).withTimeout(1.5),

            new StaticHookCommand(climb, States.LOCKED),
            new WaitCommand(0.5),
            new StaticHookCommand(climb, States.UNLOCKED)
        );
    }

}
