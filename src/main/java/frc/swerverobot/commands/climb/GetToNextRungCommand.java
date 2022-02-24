package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class GetToNextRungCommand extends SequentialCommandGroup{
    private double time = 0.01; // subject to change

    public GetToNextRungCommand(ClimberSubsystem climb) {
        addCommands(
            new WinchLockCommand(climb, () -> true),
            new WaitCommand(time),
            new WinchLockCommand(climb, () -> true),

            new ArmPositionCommand(climb, () -> true),
            new WinchLockCommand(climb, () -> true),
            new ArmPositionCommand(climb, () -> true)
        );
    }

}
