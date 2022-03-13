package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem2;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//              -----Drop Ball Command-----
public class DropBall extends SequentialCommandGroup{
    private double waitforSpool = 0.25;
    private double waitforShoot2 = 0.2;

    public DropBall(ShooterSubsystem2 shooter) {
        //This shoots all balls in the shooter
        double topSpeed = 0.3;
        double botSpeed = 0.3;
        double feedSpeed = -1;
        addCommands(
            new RunShootMotors(shooter, topSpeed,  botSpeed), //Spool up shooter motors

            new WaitCommand(waitforSpool), //Wait for spool up of motors
            new RunFeedMotors(shooter, feedSpeed), //Feed 1st ball into shooter

            new WaitCommand(waitforShoot2),
            
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }
}
