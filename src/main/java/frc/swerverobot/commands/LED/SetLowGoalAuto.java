package frc.swerverobot.commands.LED;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.LEDSubsystem;

public class SetLowGoalAuto extends CommandBase{
  private final LEDSubsystem ledSubsystem;

  public SetLowGoalAuto(LEDSubsystem ledSubsystem) {
      this.ledSubsystem = ledSubsystem;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    ledSubsystem.setLowAuto();
  }

  @Override
  public void end(boolean interrupt) {}

  @Override
  public boolean isFinished() {
      return true;
  }
}