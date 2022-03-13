package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem2;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//              -----Shoot Command-----
public class ShootCommand extends SequentialCommandGroup{
    private double waitforSpool = 0.5;
    private double waitForShoot = 0.75;

    //2 Ball Waits
    private double waitforShoot2 = 0.2;

    public ShootCommand(ShooterSubsystem2 shooter, double topSpeed, double botSpeed, double feedSpeed) {
        //This shoots all balls in the shooter
        addCommands(
            new RunShootMotors(shooter, topSpeed,  botSpeed), //Spool up shooter motors

            new WaitCommand(waitforSpool), //Wait for spool up of motors
            new RunFeedMotors(shooter, feedSpeed), //Feed 1st ball into shooter

            new WaitCommand(waitforShoot2),
            new RunFeedMotors(shooter, 0), //Stop feed motor
            new WaitCommand(waitforSpool),
            
            new RunFeedMotors(shooter, feedSpeed), //Feed second ball into shooter

            new WaitCommand(waitForShoot),
            
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }

}
