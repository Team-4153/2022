package frc.swerverobot.commands.auto.rando;

import frc.swerverobot.commands.drive.DriveWithSetRotationCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

@Deprecated

public class DriveOffTarmac extends SequentialCommandGroup{
    private final DrivetrainSubsystem drivetrain;
    private double angle;

    public DriveOffTarmac(DrivetrainSubsystem drivetrain, String choice) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);

        determineAngle(choice);

        addCommands(
            new DriveWithSetRotationCommand(drivetrain, () -> -1.0, () -> 0.0, 0.0).withTimeout(2.0)
        );
    }

    private void determineAngle(String choice) {
        switch(choice) {
            case "LEFT":
                angle = 136.5;
            case "CENTER":
                angle = 226.5;
            case "RIGHT":
                angle = 271.5;
            default:
                angle = 0;
        }

        drivetrain.setAngle(angle);
    }

}
