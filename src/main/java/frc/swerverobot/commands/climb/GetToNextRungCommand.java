package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.swerverobot.subsystems.ClimberSubsystem;

@Deprecated

public class GetToNextRungCommand extends SequentialCommandGroup{
    private double time = 0.2;

    public GetToNextRungCommand(ClimberSubsystem climb) {
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
