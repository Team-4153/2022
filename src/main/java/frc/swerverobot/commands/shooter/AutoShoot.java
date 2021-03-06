package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.auto.FollowHubCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

@Deprecated
@SuppressWarnings("unused")

public class AutoShoot extends SequentialCommandGroup{
    private final ShooterSubsystem2 shooter;
    private final DrivetrainSubsystem drivetrain;
    private final boolean highLow;
    private double[] powers;
    private ShootCommand shootCommand;
    private RunShootMotors runShootMotors;


    public AutoShoot(ShooterSubsystem2 shooter, DrivetrainSubsystem drivetrain, boolean highLow) {
        this.shooter = shooter;
        this.drivetrain = drivetrain;
        this.highLow = highLow;
        this.shootCommand = new ShootCommand(shooter, 0, 0, 0);

        addCommands(
            shootCommand//Shoots at the power defined in initalization
        );
    }

    public void initialize() {
        super.initialize();

        //Initalize Powers
        powers = shooter.SetMotorDistance(highLow);

        if (powers == null) {
            //if the powers are undefined return
            return;
        }

        //1 = Top Motor, 2 = Bottom Motor, 3 = Feed Motor
        shootCommand.setSpeeds(powers[0]*RobotMap.autoAimTopMotorPowerMultipler, powers[1]*RobotMap.autoAimBottomMotorPowerMultipler, powers[2]*RobotMap.autoAimFeedMotorPowerMultipler);
    }

}