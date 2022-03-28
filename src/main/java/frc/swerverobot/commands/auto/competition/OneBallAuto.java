package frc.swerverobot.commands.auto.competition;

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

public class OneBallAuto extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private double angle;

    public OneBallAuto(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        
        SmartDashboard.putString("Mode", "auto-high");

        addRequirements(drivetrain, shooter, intake);

        addCommands(
            new DriveCommand(drivetrain, () -> -0.3, () -> 0, () -> 0, () -> 0, () -> 0).withTimeout(1.2),
//            new AutoAim(shooter, drivetrain, true)
            new ShootCommand(shooter, RobotMap.DEFAULT_TOP_MOTOR_SPEED, RobotMap.DEFAULT_BOTTOM_MOTOR_SPEED, RobotMap.DEFAULT_FEED_MOTOR_SPEED)
        );

    }

}
