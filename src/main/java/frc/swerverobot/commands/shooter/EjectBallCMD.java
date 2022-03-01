package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ShooterSubsystem;

public class EjectBallCMD extends SequentialCommandGroup{
    public EjectBallCMD() {
        addCommands(
            new EjectBall().withTimeout(0.2),
            new EjectBalla()
        );
    }
}
