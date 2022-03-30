package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.commands.auto.FollowBallCommand;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.drive.GoToAngleCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.intake.IntakeSequence;
import frc.swerverobot.commands.shooter.AutoAim;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.*;
import frc.swerverobot.RobotMap;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

@Deprecated
@SuppressWarnings("unused")

//Raspi Auto 3 Ball High Goal

public class ThreeBallAuto extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private double angle;

    public ThreeBallAuto(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;

        addRequirements(drivetrain, shooter, intake);
        
        SmartDashboard.putString("Mode", "auto-high");                                                  //Set the LED's to high goal colors

        addCommands(
            new IntakeCommand(intake, false).withTimeout(0.3),                                          //Extend Intake
            new WaitCommand(0.5),                                                                       //Wait 0.5s
            new FollowBallCommand(drivetrain, () -> 0.3),                                               //Turn to the ball
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5), //Drive to the ball
            new IntakeCommand(intake, true),                                                            //Retract the intake
            new AutoAim(shooter, drivetrain, true),                                                     //Auto aim to the high goal
            new WaitCommand(0.5),                                                                       //Wait 0.5s

            new GoToAngleCommand(drivetrain, () -> 0.0, () -> 0.0, () -> 1, () -> 0),                   //Turn 
            new WaitCommand(0.5),                                                                       //Wait 0.5s
            new IntakeCommand(intake, false).withTimeout(0.3),                                          //Extend Intake
            new WaitCommand(0.5),                                                                       //Wait 0.5s
            new FollowBallCommand(drivetrain, () -> 0.3),                                               //Turn to the ball
            new WaitCommand(0.5),                                                                       //Wait 0.5s
            new IntakeCommand(intake, true),                                                            //Retract the intake
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, () -> 1, () -> 0),                       //Turn roughly to shooter
            new AutoAim(shooter, drivetrain, true)                                                      //Autoaim for the high goal
        );

    }

}
