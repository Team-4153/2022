package frc.swerverobot.commands.intake;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import frc.swerverobot.subsystems.ClimberSubsystem;
import frc.swerverobot.subsystems.IntakeSubsystem;
import frc.swerverobot.subsystems.ShooterSubsystem2;

public class IntakeSequence extends SequentialCommandGroup{
    private double time = 0.01; // subject to change
    private final IntakeSubsystem intake;

    public IntakeSequence(IntakeSubsystem intake, ShooterSubsystem2 shooter) {
        this.intake = intake;

        addCommands(
            new IntakeCommand(intake, false),
            new FeedCommand(shooter)
        );
    }

}
