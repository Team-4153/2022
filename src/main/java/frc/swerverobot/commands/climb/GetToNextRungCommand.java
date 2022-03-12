package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class GetToNextRungCommand extends SequentialCommandGroup{
    public GetToNextRungCommand(ClimberSubsystem climb) {
        addCommands(
            new UnspoolCommand(climb).withTimeout(2.0),

            new ArmPositionCommand(climb, States.UNLOCKED),
            new UnspoolCommand(climb).withTimeout(4.0),
            new ArmPositionCommand(climb, States.LOCKED)
        );
    }

}
