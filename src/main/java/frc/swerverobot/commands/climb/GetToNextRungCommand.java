package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class GetToNextRungCommand extends SequentialCommandGroup{
    private double time = 0.01; // subject to change
    private final ClimberSubsystem climb;

    public GetToNextRungCommand(ClimberSubsystem climb) {
        this.climb = climb;
        addCommands(
            new WinchLockCommand(climb, States.UNLOCKED),
            new WaitCommand(time),
            new WinchLockCommand(climb, States.LOCKED),

            new ArmPositionCommand(climb, States.UNLOCKED),
            new WinchLockCommand(climb, States.UNLOCKED),
            new ArmPositionCommand(climb, States.LOCKED)
        );
    }

}
