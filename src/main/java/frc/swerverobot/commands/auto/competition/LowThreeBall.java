package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.LED.SetLowGoalAuto;
import frc.swerverobot.commands.LED.SetT;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.drive.GoToAngleCommand;
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

//Hard Coded 3 Ball Low Goal

public class LowThreeBall extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private double angle;

    public LowThreeBall(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;

        addRequirements(drivetrain);

        addCommands(
            new SetLowGoalAuto(),                                                                           //Set the LED's to low goal colors
            new ManualShoot(shooter, -0.45, 0.45, -1).withTimeout(1.3),                                     //Shoot 1st ball
            new IntakeCommand(intake, false).withTimeout(0.1),                                              //Extend Intake
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),  //Drive Backwards
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.5),     //Stop Driving
            new IntakeCommand(intake, true).withTimeout(0.5),                                               //Retract Intake
            new WaitCommand(0.3),                                                                           //Wait 0.3s
            new ManualShoot(shooter, -0.5, 0.75, -1).withTimeout(1.3),                                                        //Shoot both balls into low goal
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, -2*Math.PI/3.25).withTimeout(1.3),           //Turn to 3rd ball
            new IntakeCommand(intake, false).withTimeout(0.2),                                              //Extend intake
            new DriveCommand(drivetrain, () -> 0.15, () -> 0.5, () -> 0, () -> 0, () -> 0).withTimeout(1.5),//Drive to 3rd ball
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),     //Stop Driving
            new IntakeCommand(intake, true).withTimeout(0.5),                                               //Retract Intake
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, Math.PI/2.25).withTimeout(1.3),              //Turn to hub
            new ManualShoot(shooter, -0.5, 0.75, -1).withTimeout(1.3),                                                       //Shoot 3rd ball into low goal
            new SetT()                                                                                      //Change Auto LED's to tele Mode
        );
    }

}

