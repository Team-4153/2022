package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot1A extends CommandBase {

  public Shoot1A(ShooterSubsystem subsystem) {
    final ShooterSubsystem m_subsystem = subsystem;
    subsystem.shootingProcess1a();
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
