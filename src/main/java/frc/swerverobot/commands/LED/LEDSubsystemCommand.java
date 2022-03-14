package frc.swerverobot.commands.LED;

import frc.swerverobot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

@SuppressWarnings("unused")

/** An example command that uses an example subsystem. */
public class LEDSubsystemCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LEDSubsystem LED;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LEDSubsystemCommand(LEDSubsystem LED) {
    this.LED = LED;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(LED);
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
