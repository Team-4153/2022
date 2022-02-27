package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ShooterSubsystem;

public class EjectBallCMD extends SequentialCommandGroup{
    public EjectBallCMD(ShooterSubsystem shooter) {
        addCommands(
            new EjectBall(),
            new WaitCommand(0.2),
            new EjectBalla()
        );
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
