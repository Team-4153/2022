package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.commands.auto.FollowHubCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

@Deprecated
@SuppressWarnings("unused")

public class AutoAim extends SequentialCommandGroup{
    private final ShooterSubsystem2 shooter;
    private final DrivetrainSubsystem drivetrain;
    private final boolean highLow;
    private double[] powers;
    private ShootCommand shootCommand;


    public AutoAim(ShooterSubsystem2 shooter, DrivetrainSubsystem drivetrain, boolean highLow) {
        this.shooter = shooter;
        this.drivetrain = drivetrain;
        this.highLow = highLow;
        this.shootCommand = new ShootCommand(shooter, 0, 0, 0);

        addCommands(
            //Points robot at hub 
            new FollowHubCommand(drivetrain).withTimeout(2),
            //0 = Top Motor, 1 = Bottom Motor, 2 = Feed Motor
            //Shoots at the power from earlier
            shootCommand
        );
    }

    public void initialize() {
        super.initialize();
        powers = shooter.SetMotorDistance(highLow);

        if (powers == null) {
            return;
        }

        shootCommand.setSpeeds(powers[0], powers[1], powers[2]);
    }

}