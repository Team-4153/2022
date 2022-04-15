package frc.swerverobot.commands.LED;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.LEDSubsystem;

public class TickLEDS extends CommandBase{
  private final LEDSubsystem ledSubsystem;

  public TickLEDS(LEDSubsystem ledSubsystem) {
      this.ledSubsystem = ledSubsystem;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    ledSubsystem.moveRainbow();
  }

  @Override
  public void end(boolean interrupt) {}

  @Override
  public boolean isFinished() {
      return true;
  }
}