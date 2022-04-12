package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.LED.SetHighGoalAuto;
import frc.swerverobot.commands.LED.SetT;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.drive.GoToAngleCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
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

//Hard Coded 3 Ball Auto High Goal

public class HighThreeBall extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final LEDSubsystem LED;
    private double angle;

    public HighThreeBall(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, LEDSubsystem LED) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.LED = LED;

        addRequirements(drivetrain);

        addCommands(
            new SetHighGoalAuto(LED),                                                                          //Set the LED's to High goal colors
            new IntakeCommand(intake, false).withTimeout(0.1),                                              //Extend intake
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.1),  //Drive backwards
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1),       //Stop Driving
            new IntakeCommand(intake, true).withTimeout(0.5),                                               //Retract Intake
            new WaitCommand(0.3),                                                                           //Wait 0.3s
            new ManualShoot(shooter, -0.725, 0.725, -1),                                                    //Shoot both into high goal
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, -2*Math.PI/3),                               //Turn towrds the 3rd ball
            new IntakeCommand(intake, false).withTimeout(0.2),                                              //Extend intake
            new DriveCommand(drivetrain, () -> 0.25, () -> 0.5, () -> 0, () -> 0, () -> 0).withTimeout(1.5),//Drive to 3rd ball
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),     //Stop Driving
            new IntakeCommand(intake, true).withTimeout(0.5),                                               //Retract intake
            new GoToAngleCommand(drivetrain, () -> 0, () -> 0, Math.PI/3),                                  //Turn towrds hub
            new ManualShoot(shooter, -0.725, 0.725, -1),                                                    //Shoot 3rd ball
            new SetT(LED)                                                                                      //Change Auto LED's to tele Mode
        );
    }

}

