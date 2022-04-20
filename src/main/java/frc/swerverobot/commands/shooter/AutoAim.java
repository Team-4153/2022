package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.swerverobot.RobotMap;
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
    private RunShootMotors runShootMotors;
    private double firstBallMultipler;
    private double secondBallMultipler;
    private double distance;


    public AutoAim(ShooterSubsystem2 shooter, DrivetrainSubsystem drivetrain, boolean highLow) {
        this.shooter = shooter;
        this.drivetrain = drivetrain;
        this.highLow = highLow;
        this.shootCommand = new ShootCommand(shooter, 0, 0, 0);
        this.runShootMotors = new RunShootMotors(shooter, 0, 0);

        addCommands(
            runShootMotors,//Starts the shooting motors early
            new FollowHubCommand(drivetrain).withTimeout(1.6),//Points robot at hub 
            new AutoShoot(shooter, drivetrain, highLow).withTimeout(5)
        );
    }

    public static double lerp(double from, double to, double progress) {
        return from + ((to - from) * progress);
    }

    public void initialize() {
        super.initialize();

        distance = SmartDashboard.getNumber("ShooterTarget/TargetDistance", 0);

        //Initalize Powers
        powers = shooter.SetMotorDistance(highLow);

        if (powers == null) {
            //if the powers are undefined return
            return;
        }

        firstBallMultipler = lerp(RobotMap.firstBallPowerMultiplierMin, RobotMap.firstBallPowerMultiplierMax, ((distance-RobotMap.AutoAimMinDistance)/(RobotMap.AutoAimMaxDistance-RobotMap.AutoAimMinDistance)));
        secondBallMultipler = lerp(RobotMap.secondBallPowerMultiplierMin, RobotMap.secondBallPowerMultiplierMax, ((distance-RobotMap.AutoAimMinDistance)/(RobotMap.AutoAimMaxDistance-RobotMap.AutoAimMinDistance)));

        //Used for early motor spinup
        runShootMotors.setSpeeds(powers[0]*RobotMap.autoAimTopMotorPowerMultipler*firstBallMultipler, powers[1]*RobotMap.autoAimBottomMotorPowerMultipler*firstBallMultipler);
    }

}