package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ShooterSubsystem;

public class EjectBallCMD extends SequentialCommandGroup{
    public EjectBallCMD(ShooterSubsystem shooter) {
        addCommands(
            new EjectBall(shooter),
            new WaitCommand(0.2),
            new EjectBalla(shooter)
        );
    }
}
