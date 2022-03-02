package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class PullandGrabCommand extends SequentialCommandGroup{
    public PullandGrabCommand(ClimberSubsystem climb, States winch, States spool, States hook) {
        addCommands(
            new WinchLockCommand(climb, winch),
            new SpoolCommand(climb, spool),
            new StaticHookCommand(climb, hook)
        );
    }

}