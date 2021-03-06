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

//Hard Coded 1 Ball Low 1 Ball High Goal

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
            new SetHighGoalAuto(LED),                                                                       //Set the LED's to low goal colors
            new IntakeCommand(intake, false).withTimeout(0.1),                                              //Extend Intake
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.1),  //Drive Backwards
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.0),     //Stop Driving
            new IntakeCommand(intake, true).withTimeout(1.0),                                               //Retract Intake
            new ManualShoot(shooter, -0.75, 0.77, -1),                                                      //Shoot 2nd ball
            new SetT(LED)                                                                                   //Change Auto LED's to tele Mode
        );
    }
 
}
