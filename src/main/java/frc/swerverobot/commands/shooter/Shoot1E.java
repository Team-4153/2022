package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot1E extends CommandBase {

  public Shoot1E(ShooterSubsystem subsystem) {
    final ShooterSubsystem m_subsystem = subsystem;
    subsystem.shootingProcess1e();
    addRequirements(m_subsystem);
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
