package frc.swerverobot.commands.LED;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.LEDSubsystem;

public class SetHighGoalAuto extends CommandBase{
  private final LEDSubsystem ledSubsystem;

  public SetHighGoalAuto(LEDSubsystem ledSubsystem) {
      this.ledSubsystem = ledSubsystem;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    ledSubsystem.setHighAuto();
  }

  @Override
  public void end(boolean interrupt) {}

  @Override
  public boolean isFinished() {
      return true;
  }
}