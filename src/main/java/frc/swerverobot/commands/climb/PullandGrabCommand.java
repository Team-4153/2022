package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class PullandGrabCommand extends SequentialCommandGroup{
    public PullandGrabCommand(ClimberSubsystem climb) {
        addCommands(
            new WinchLockCommand(climb, () -> true),
            new SpoolCommand(climb, () -> true),
            new StaticHookCommand(climb, () -> true)
        );
    }

}