package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

@Deprecated

public class AutoAim extends SequentialCommandGroup{
    public AutoAim(ShooterSubsystem2 shooter, DrivetrainSubsystem drivetrain) {
        double powers[] = shooter.SetMotorDistance();

        addCommands(
            //Points robot at hub 
            new FollowHubCommand(drivetrain),
            //0 = Top Motor, 1 = Bottom Motor, 2 = Feed Motor
            //Shoots at the power from earlier
            new ShootCommand(shooter, powers[0], powers[1], powers[2])
        );
    }

}