package frc.swerverobot.commands.LED;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.LEDSubsystem;

public class LEDColorSet extends CommandBase{
    private final LEDSubsystem m_subsystem;

    public LEDColorSet(LEDSubsystem subsystem, int i){
        m_subsystem = subsystem;
        
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}