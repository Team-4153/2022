package frc.swerverobot.commands.climb;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class PullandGrabCommand extends SequentialCommandGroup{
    private final ClimberSubsystem climb;

    public PullandGrabCommand(ClimberSubsystem climb) {
        this.climb = climb;

        addCommands(
            new WinchLockCommand(climb, States.LOCKED),
            new SpoolCommand(climb),
            new StaticHookCommand(climb, States.LOCKED)
        );
    }

}