package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.Robot;
import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.LED.SetLowGoalAuto;
import frc.swerverobot.commands.LED.SetT;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.shooter.ManualShoot;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.LEDSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

@Deprecated
@SuppressWarnings("unused")

//Hard Coded 2 Ball Low Goal

public class Drive extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final LEDSubsystem LED;
    private double angle;

    public Drive(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, LEDSubsystem LED) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.LED = LED;

        addRequirements(drivetrain);

        addCommands(
            new SetLowGoalAuto(LED),                                                                               //Set the LED's to low goal colors
            new ManualShoot(shooter, -0.475, 0.475, -1),                                                        //Shoot 1st ball
            // new IntakeCommand(intake, false).withTimeout(0.1),                                               //Extend Intake
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.5),      //Drive Backwards
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5),         //Stop Driving
            // // new IntakeCommand(intake, true).withTimeout(0.1),                                             //Retract Intake
            // new DriveCommand(drivetrain, () -> 0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.4),    //Drive Forwards to hub
            // new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5),      //Stop Driving
            // new ManualShoot(shooter, -0.475, 0.475, -1),                                                     //Shoot 2nd ball
            new SetT(LED)                                                                                          //Change Auto LED's to tele Mode
        );
    }

}
