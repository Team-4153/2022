package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

@Deprecated

public class PullandGrabCommand extends SequentialCommandGroup{
    public PullandGrabCommand(ClimberSubsystem climb) {
        addCommands(
            new WinchLockCommand(climb, States.LOCKED),
            new SpoolCommand(climb),
            new StaticHookCommand(climb, States.LOCKED)
        );
    }

}