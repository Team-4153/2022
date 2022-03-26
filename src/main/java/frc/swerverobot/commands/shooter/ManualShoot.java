package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem2;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.commands.intake.FeedCommand;

@SuppressWarnings("unused")

//              -----Shoot Command-----
public class ManualShoot extends SequentialCommandGroup{
    private RunShootMotors runShootMotors;
    private RunFeedMotors runFeedMotors1;
    private RunFeedMotors runFeedMotors2;

    private double topSpeed;
    private double botSpeed;
    private double feedSpeed;

    private double waitforSpool = 0.5;
    private double waitForShoot = 0.75;

    //2 Ball Waits
    private double waitforShoot2 = 0.2;

    public ManualShoot(ShooterSubsystem2 shooter, double topSpeed, double botSpeed, double feedSpeed) {
        this.topSpeed = topSpeed;
        this.botSpeed = botSpeed;
        this.feedSpeed = feedSpeed;

        this.runShootMotors = new RunShootMotors(shooter, topSpeed, botSpeed);
        this.runFeedMotors1 = new RunFeedMotors(shooter, feedSpeed);
        this.runFeedMotors2 = new RunFeedMotors(shooter, feedSpeed);


        addCommands(
            runShootMotors,

            new WaitCommand(waitforSpool), //Wait for spool up of motors
            runFeedMotors1,

            new WaitCommand(waitforShoot2),
            
            new FeedCommand(shooter),
            // new RunFeedMotors(shooter, 0), //Stop feed motor
            new WaitCommand(waitforSpool),
            
            runFeedMotors2,

            new WaitCommand(waitForShoot),
            
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }

}
