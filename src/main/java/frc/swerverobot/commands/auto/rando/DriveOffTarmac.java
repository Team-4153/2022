package frc.swerverobot.commands.auto.rando;

import frc.swerverobot.Robot;
import frc.swerverobot.RobotMap;
import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.commands.intake.IntakeCommand;
import frc.swerverobot.commands.shooter.ManualShoot;
import frc.swerverobot.commands.shooter.ShootCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

@Deprecated
@SuppressWarnings("unused")

public class DriveOffTarmac extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private final ShooterSubsystem2 shooter;
    private final IntakeSubsystem intake;
    private double angle;

    public DriveOffTarmac(DrivetrainSubsystem drivetrain, ShooterSubsystem2 shooter, IntakeSubsystem intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;

        addRequirements(drivetrain);

        // determineAngle(choice);

        addCommands(
            // new IntakeCommand(intake, false).withTimeout(3),
            // new WaitCommand(1),
            new ManualShoot(shooter, -0.4, 0.5, -1),
            new DriveWithSetRotationCommand(drivetrain, () -> -0.5, () -> 0.0, 0.0).withTimeout(2),
            new DriveWithSetRotationCommand(drivetrain, () -> -0.0, () -> 0.0, 0.0).withTimeout(1)
            // new WaitCommand(1),
            // new IntakeCommand(intake, true),
            // new WaitCommand(1),
            // new ShootCommand(shooter, RobotMap.DEFAULT_TOP_MOTOR_SPEED, RobotMap.DEFAULT_BOTTOM_MOTOR_SPEED, RobotMap.DEFAULT_FEED_MOTOR_SPEED)
        );
    }

    // private void determineAngle(String choice) {
    //     switch(choice) {
    //         case "LEFT":
    //             angle = 136.5;
    //         case "CENTER":
    //             angle = 226.5;
    //         case "RIGHT":
    //             angle = 271.5;
    //         default:
    //             angle = 0;
    //     }

        // drivetrain.setAngle(angle);
    // }

}
