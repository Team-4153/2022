package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ShooterSubsystem2;

public class ShootCommand extends SequentialCommandGroup{
    private double time = 0.01; // subject to change
    private final ShooterSubsystem2 shooter;
    private double topSpeed;
    private double botSpeed;
    private double feedSpeed = -0.5;
    private double waitforSpool = 1.5;
    private double waitforShoot1ball = 1.5;
    private double waitforShoot2balls = 0.75;

    public ShootCommand(ShooterSubsystem2 shooter, double topSpeed, double botSpeed) {
        this.shooter = shooter;
        this.topSpeed = topSpeed;
        this.botSpeed = botSpeed;

        if (shooter.getBallCount() < 1) {
            //For 2 Balls
            addCommands(
                new RunShootMotors(shooter, topSpeed,  botSpeed),
    
                new WaitCommand(waitforSpool),
                new RunFeedMotors(shooter, feedSpeed),
    
                new WaitCommand(waitforShoot2balls),
                new RunFeedMotors(shooter, 0),
                new RunShootMotors(shooter, 0,  0)
            );
        }
        else {
            //For 1 Ball
            addCommands(
                new RunShootMotors(shooter, topSpeed,  botSpeed),

                new WaitCommand(waitforSpool),
                new RunFeedMotors(shooter, feedSpeed),

                new WaitCommand(waitforShoot1ball),
                new RunFeedMotors(shooter, 0),
                new RunShootMotors(shooter, 0,  0)
                
            );
        }

    }

}
