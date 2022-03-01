package frc.swerverobot.commands.shooter;

import frc.swerverobot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.swerverobot.RobotMap.*;

public class EjectBalla extends CommandBase {
  public EjectBalla() {
      //Set Top Motor to 0.175 (Barely enough to move it)
      topShooterMotor.stopMotor();
      //Set Bottom Motor to 0.175 (Barely enough to move it)
      bottomShooterMotor.stopMotor();
      //Set Bottom Motor to 0.2
      feedMotor.stopMotor();
      System.out.println("Stop SHooter Motors");
  }
}
