package frc.swerverobot.commands.auto.competition;

import frc.swerverobot.Robot;
import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.LED.SetLowGoalAuto;
import frc.swerverobot.commands.LED.SetT;
import frc.swerverobot.commands.drive.DriveCommand;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
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

//Hard Coded 1 Ball Low Goal

public class LowOneBall extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private final LEDSubsystem LED;
    private double angle;

    public LowOneBall(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake, LEDSubsystem LED) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        this.LED = LED;

        addRequirements(drivetrain);

        addCommands(
            new SetLowGoalAuto(LED),                                                                        //Set the LED's to low goal colors
            new ManualShoot(shooter, -0.45, 0.45, -1),                                                      //Shoot 1st ball
            new DriveCommand(drivetrain, () -> -0.5, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.5),  //Drive backwards
            new SetT(LED)                                                                                   //Change Auto LED's to tele Mode
        );
    }

}
