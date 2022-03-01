package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.swerverobot.subsystems.ShooterSubsystem;

public class Shoot1 extends SequentialCommandGroup{
    public Shoot1(ShooterSubsystem shooter) {
        addCommands(
            new Shoot1A(shooter),
            new WaitCommand(0.2),
            new Shoot1B(shooter),
            new WaitCommand(0.1),
            new Shoot1C(shooter),
            new WaitCommand(0.1),
            new Shoot1B(shooter),
            new WaitCommand(0.1),
            new Shoot1E(shooter)
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
