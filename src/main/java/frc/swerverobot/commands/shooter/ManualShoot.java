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

    private double waitforSpool = 0.75;
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
            //First Ball
            runShootMotors,//Get Shoot Motors up to speed
            new WaitCommand(waitforSpool), //Wait for spool up of motors
            runFeedMotors1,//Feed first ball into the shooter
            new WaitCommand(waitforShoot2),//wait for ball to exit shooter 

            //Second Ball
            new FeedCommand(shooter).withTimeout(1),//Feed the second ball into the fiering position
            new WaitCommand(waitforSpool/2),//Wait for shooter to get up to speed again
            runFeedMotors2,//Feed the second ball into the shooter
            new WaitCommand(waitForShoot),//Wait for ball to exit shooter

            //End
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }

}
