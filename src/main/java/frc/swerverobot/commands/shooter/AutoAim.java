package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

public class AutoAim extends SequentialCommandGroup{
    private final ShooterSubsystem2 shooter;
    private final DrivetrainSubsystem drivetrain;

    public AutoAim(ShooterSubsystem2 shooter, DrivetrainSubsystem drivetrain) {
        this.shooter = shooter;
        this.drivetrain = drivetrain;

        double powers[] = shooter.SetMotorDistance();

        addCommands(
            new FollowHubCommand(drivetrain),
            //0 = Top Motor, 1 = Bottom Motor, 2 = Feed Motor
            new ShootCommand(shooter, powers[0], powers[1], powers[2])
        );
    }

}