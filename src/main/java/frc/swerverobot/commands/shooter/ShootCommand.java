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

    private double feedSpeed = -0.5;

    private double waitforSpool = 0.5;
    private double waitforShoot1ball = 0.5;
    private double waitforShoot2balls = 1.;

    double waitForShoot = waitforShoot2balls; //This variable is changed for the number of balls in the shooter

    public ShootCommand(ShooterSubsystem2 shooter, double topSpeed, double botSpeed) {
        this.shooter = shooter;
        this.topSpeed = topSpeed;
        this.botSpeed = botSpeed;

        //Changes the wait time for the number of balls in the shooter
        if (shooter.ballCount() < 1) {
            //if there are 2 balls use the 2 ball wait time
            waitForShoot = waitforShoot2balls;
        }
        else {
            //if there is 1 ball use the 1 ball wait time
            waitForShoot = waitforShoot1ball;
        }

        //This shoots all balls in the shooter
        addCommands(
            new RunShootMotors(shooter, topSpeed,  botSpeed), //Spool up shooter motors

            new WaitCommand(waitforSpool), //Wait for spool up of motors
            new RunFeedMotors(shooter, feedSpeed), //Feed both balls into shooter

            new WaitCommand(waitForShoot), //Wait for 2 balls to exit shooter
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }

}
