package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.LED.SetHighGoalAuto;
import frc.swerverobot.commands.LED.SetT;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.shooter.AutoAim;
import frc.swerverobot.commands.shooter.ManualShoot;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.LEDSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@Deprecated
@SuppressWarnings("unused")

//Hard Coded 2 Ball Auto High Goal

public class HighTwoBall extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final LEDSubsystem LED;
    private double angle;

    public HighTwoBall(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, LEDSubsystem LED) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.LED = LED;

        addRequirements(drivetrain);

        addCommands(
            // new SetHighGoalAuto(),                                                                          //Set the LED's to High goal colors
            // new IntakeCommand(intake, false).withTimeout(0.1),                                              //Extend Intake
            // new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),  //Drive to second ball
            // new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.5),     //Stop Driving
            // new IntakeCommand(intake, true).withTimeout(0.1),                                               //Retract Intake
            // new ManualShoot(shooter, -0.4, 1, -0.6),                                                        //Shoot balls into high goal
            // new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.2),  //Drive backwards a little more to make sure it is out of tape
            // new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1),       //Stop Driving backwards
            // new SetT()                                                                                      //Change Auto LED's to tele Mode
            new SetHighGoalAuto(LED),                                                                          //Set the LED's to High goal colors
            // new IntakeCommand(intake, false).withTimeout(0.1),                                              //Extend Intake
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.3),  //Drive to second ball
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.5),     //Stop Driving
            new IntakeCommand(intake, true).withTimeout(0.1),                                               //Retract Intake
            // new AutoAim(shooter, drivetrain, true).withTimeout(10),                                                 //Shoot balls into high goal
            new SetT(LED)                                                                                      //Change Auto LED's to tele Mode
        );
    }

}
