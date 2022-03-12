package frc.swerverobot.commands.auto;

import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.*;
import frc.swerverobot.RobotMap;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

@Deprecated

public class AutonomousCommand extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private double angle;

    public AutonomousCommand(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, PossibleAutos choice) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain, shooter, intake);

        determineAngle(choice);

        addCommands(
            new IntakeCommand(intake, false),
            new DriveWithSetRotationCommand(drivetrain, () -> -1.0, () -> 0.0, 0.0).withTimeout(2.0),
            new WaitCommand(2.0),
            
            new ShootCommand(shooter, RobotMap.DEFAULT_TOP_MOTOR_SPEED, RobotMap.DEFAULT_BOTTOM_MOTOR_SPEED, RobotMap.DEFAULT_FEED_MOTOR_SPEED)
        );
    }

    private void determineAngle(PossibleAutos choice) {
        switch(choice) {
            case LEFT:
                angle = 136.5;
            case CENTER:
                angle = 226.5;
            case RIGHT:
                angle = 271.5;
            default:
                angle = 0;
        }

        drivetrain.setAngle(angle);
    }

}
