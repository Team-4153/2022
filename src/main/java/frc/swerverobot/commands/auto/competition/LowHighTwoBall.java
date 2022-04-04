package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.Robot;
import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.LED.SetT;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.shooter.ManualShoot;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@Deprecated
@SuppressWarnings("unused")

//Hard Coded 2 Ball Low Goal

public class LowHighTwoBall extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private double angle;

    public LowHighTwoBall(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;

        addRequirements(drivetrain);

        SmartDashboard.putString("Mode", "auto-low");                                                       //Set the LED's to low goal colors

        addCommands(
            new ManualShoot(shooter, -0.475, 0.475, -1).withTimeout(1.5),                                   //Shoot 1st ball
            new IntakeCommand(intake, false).withTimeout(0.1),                                              //Extend Intake
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),  //Drive Backwards
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),     //Stop Driving
            new IntakeCommand(intake, true).withTimeout(1.0),                                               //Retract Intake
            // new DriveCommand(drivetrain, () -> 0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.4),   //Drive Forwards to hub
            // new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5),     //Stop Driving
            new ManualShoot(shooter, -0.6, 0.75, -1),                                                       //Shoot 2nd ball
            // new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5),  //Drive Backwards
            // new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),     //Stop Driving
            new SetT()                                                                                      //Change Auto LED's to tele Mode
        );
    }

}
