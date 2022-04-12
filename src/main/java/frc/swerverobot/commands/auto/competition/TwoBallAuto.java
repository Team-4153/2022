package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.commands.LED.SetHighGoalAuto;
import frc.swerverobot.commands.LED.SetT;
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

//Raspi Auto 2 Ball High Goal

public class TwoBallAuto extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final LEDSubsystem LED;
    private double angle;

    public TwoBallAuto(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, LEDSubsystem LED) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.LED = LED;

        addRequirements(drivetrain, shooter, intake);

        addCommands(
            new SetHighGoalAuto(LED),                                                                   //Set the LED's to High goal colors
            new IntakeCommand(intake, false).withTimeout(0.3),                                          //Extend Intake
            new WaitCommand(0.5),                                                                       //Wait 0.5s
            new FollowBallCommand(drivetrain, () -> 0.3),                                               //Turn to the ball
            new DriveCommand(drivetrain, () -> 0, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(0.5), //Drive to the ball
            new IntakeCommand(intake, true),                                                            //Retract Intake
            new AutoAim(shooter, drivetrain, true),                                                     //Auto aim and shoot for the high goal
            new SetT(LED)                                                                               //Change Auto LED's to tele Mode
        );

    }

}
