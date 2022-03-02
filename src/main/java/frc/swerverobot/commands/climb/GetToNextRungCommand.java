package frc.swerverobot.commands.climb;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ClimberSubsystem;

public class GetToNextRungCommand extends SequentialCommandGroup{
    private double time = 0.01; // subject to change

    public GetToNextRungCommand(ClimberSubsystem climb, States winch, States winch2, States winch3, States arm1, States arm2) {
        addCommands(
            new WinchLockCommand(climb, winch),
            new WaitCommand(time),
            new WinchLockCommand(climb, winch2),

            new ArmPositionCommand(climb,arm1),
            new WinchLockCommand(climb,winch3),
            new ArmPositionCommand(climb, arm2)
        );
    }

}
