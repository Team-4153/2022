package frc.swerverobot.commands;

import frc.swerverobot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DecreaseShootDistance extends CommandBase {

  public DecreaseShootDistance(ShooterSubsystem subsystem) {
    final ShooterSubsystem m_subsystem = subsystem;
    subsystem.manualShooterDistanceDecrease();
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
