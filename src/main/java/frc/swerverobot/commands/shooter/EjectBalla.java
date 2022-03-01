package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.swerverobot.RobotMap.*;

public class EjectBalla extends CommandBase {
  private ShooterSubsystem shooter;

  public EjectBalla(ShooterSubsystem shooter) {
    this.shooter = shooter;
  }

  @Override
  public void initialize() {
    //Set Top Motor to 0.175 (Barely enough to move it)
    topShooterMotor.set(0);
    //Set Bottom Motor to 0.175 (Barely enough to move it)
    bottomShooterMotor.set(0);
    //Set Bottom Motor to 0.2
    feedMotor.set(0);

    System.out.println("Start Shooter Motors");

  }

  @Override
  public void execute() {
    //Set Top Motor to 0.175 (Barely enough to move it)
    topShooterMotor.set(0);
    //Set Bottom Motor to 0.175 (Barely enough to move it)
    bottomShooterMotor.set(0);
    //Set Bottom Motor to 0.2
    feedMotor.set(0);

    System.out.println("Start Shooter Motors");
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
      return true;
  }
}
