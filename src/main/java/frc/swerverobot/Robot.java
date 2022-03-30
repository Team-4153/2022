package frc.swerverobot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

@Deprecated

public class Robot extends TimedRobot {
    private RobotContainer container;
    private Command autonomousCommand;
    private Command testCommand;

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
        autonomousCommand.schedule();
      }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
      SmartDashboard.putString("Mode", "tele");
      if (autonomousCommand != null) {
          autonomousCommand.cancel();
      }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {
      testCommand = container.getTestCommand();

      if (testCommand != null) {
        testCommand.schedule();
      }
    }

    @Override
    public void testPeriodic() {}

}
