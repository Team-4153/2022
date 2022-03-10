package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem2;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//              -----Shoot Command-----
/*
 - Count balls in shooter
 - If there are 2 balls, shoot
*/
public class ShootCommand extends SequentialCommandGroup{
    private final ShooterSubsystem2 shooter;
    private double topSpeed;
    private double botSpeed;

    private double feedSpeed; //Max feed motor speed is 0.6 any higher and it gets inacurate

    private double waitforSpool = 0.5;
    private double waitforShoot1ball = 0.75;

    private double waitForShoot = 0.75;

    //2 Ball Waits
    private double waitforShoot2balls = 0.75;
    private double waitforShoot2 = 0.175;

    public ShootCommand(ShooterSubsystem2 shooter, double topSpeed, double botSpeed, double feedSpeed) {
        this.shooter = shooter;
        this.topSpeed = topSpeed;
        this.botSpeed = botSpeed;
        this.feedSpeed = feedSpeed;

        //This shoots all balls in the shooter
        addCommands(
            new RunShootMotors(shooter, topSpeed,  botSpeed), //Spool up shooter motors

            new WaitCommand(waitforSpool), //Wait for spool up of motors
            new RunFeedMotors(shooter, feedSpeed), //Feed both balls into shooter

            new WaitCommand(waitforShoot2),
            new RunFeedMotors(shooter, 0), //Feed both balls into shooter
            new WaitCommand(waitforSpool),
            
            new RunFeedMotors(shooter, feedSpeed), //Feed both balls into shooter

            new WaitCommand(waitForShoot),
            
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }

}
