package frc.swerverobot.commands.shooter;

import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.intake.FeedCommand;
import frc.swerverobot.subsystems.ShooterSubsystem2;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//              -----Shoot Command-----
public class ShootCommand extends SequentialCommandGroup{
    private RunShootMotors runShootMotors;
    private RunShootMotors runShootMotors2;
    private RunFeedMotors runFeedMotors1;
    private RunFeedMotors runFeedMotors2;

    // private double topSpeed;
    // private double botSpeed;
    // private double feedSpeed;

    private double waitforSpool = 0.9;
    private double waitForShoot = 0.75;

    //2 Ball Waits
    private double waitforShoot2 = 0.2;
    
    //Modifiers
    private double firstBallMultipler;
    private double secondBallMultipler;
    private double distance;

    public ShootCommand(ShooterSubsystem2 shooter, double topSpeed, double botSpeed, double feedSpeed) {
        this.runShootMotors = new RunShootMotors(shooter, 0, 0);
        this.runShootMotors2 = new RunShootMotors(shooter, 0, 0);
        this.runFeedMotors1 = new RunFeedMotors(shooter, 0);
        this.runFeedMotors2 = new RunFeedMotors(shooter, 0);

        addCommands(
            //First Ball
            runShootMotors,//Get Shoot Motors up to speed
            new WaitCommand(waitforSpool), //Wait for spool up of motors
            runFeedMotors1,//Feed first ball into the shooter
            new WaitCommand(waitforShoot2),//wait for ball to exit shooter 

            //Second Ball
            runShootMotors2,
            new FeedCommand(shooter).withTimeout(1.2),//Feed the second ball into the fiering position
            new WaitCommand(waitforSpool/1.5),//Wait for shooter to get up to speed again
            runFeedMotors2,//Feed the second ball into the shooter
            new WaitCommand(waitForShoot),//Wait for ball to exit shooter

            //End
            new RunFeedMotors(shooter, 0), //Stop feed motors
            new RunShootMotors(shooter, 0,  0) //Stop shooter motors
        );
    }

    public static double lerp(double from, double to, double progress) {
        return from + ((to - from) * progress);
    }

    public void setSpeeds(double topSpeed, double botSpeed, double feedSpeed) {
        distance = SmartDashboard.getNumber("ShooterTarget/TargetDistance", 0);

        firstBallMultipler = lerp(RobotMap.firstBallPowerMultiplierMin, RobotMap.firstBallPowerMultiplierMax, ((distance-RobotMap.AutoAimMinDistance)/(RobotMap.AutoAimMaxDistance-RobotMap.AutoAimMinDistance)));

        secondBallMultipler = lerp(RobotMap.secondBallPowerMultiplierMin, RobotMap.secondBallPowerMultiplierMax, ((distance-RobotMap.AutoAimMinDistance)/(RobotMap.AutoAimMaxDistance-RobotMap.AutoAimMinDistance)));

        runShootMotors.setSpeeds(topSpeed*firstBallMultipler, botSpeed*firstBallMultipler);
        runShootMotors2.setSpeeds(topSpeed*secondBallMultipler, botSpeed*secondBallMultipler);

        runFeedMotors1.setSpeeds(feedSpeed);
        runFeedMotors2.setSpeeds(feedSpeed);
    }

}
