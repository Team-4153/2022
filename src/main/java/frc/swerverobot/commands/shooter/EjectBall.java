package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.swerverobot.RobotMap.*;

public class EjectBall extends CommandBase {

  public EjectBall() {
      //Set Top Motor to 0.175 (Barely enough to move it)
      topShooterMotor.set(0.175);
      //Set Bottom Motor to 0.175 (Barely enough to move it)
      bottomShooterMotor.set(0.175);
      //Set Bottom Motor to 0.2
      feedMotor.set(-0.2);
      System.out.println("Start Shooter Motors");
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
