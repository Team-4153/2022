package frc.swerverobot.commands.LED;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetLowGoalAuto extends CommandBase{
  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    SmartDashboard.putString("Mode", "auto-low");
  }

  @Override
  public void end(boolean interrupt) {}

  @Override
  public boolean isFinished() {
      return true;
  }
}