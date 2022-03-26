package frc.swerverobot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

@Deprecated

public class Robot extends TimedRobot {
    private RobotContainer container;
    private Command autonomousCommand;

    @Override
    public void robotInit() {
        container = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
    }
  
    @Override
    public void disabledPeriodic() {
      CommandScheduler.getInstance().run();
      container.checkColor();
    }

    @Override
    public void autonomousInit() {
      autonomousCommand = container.getAutonomousCommand();
  
      // schedule the autonomous command (example)
      if (autonomousCommand != null) {
        SmartDashboard.putString("Mode", "auto");
        autonomousCommand.schedule();
      }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
      if (autonomousCommand != null) {
          autonomousCommand.cancel();
      }
      SmartDashboard.putString("Mode", "tele");
    }

    @Override
    public void teleopPeriodic() {}

}
