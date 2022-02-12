package frc.swerverobot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.ADIS16470_IMU;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Robot Map
import static frc.swerverobot.RobotMap.*;


public class Robot extends TimedRobot {
    private final RobotContainer container = new RobotContainer();

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {
         
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("IMU angle", RobotMap.imu.getAngle());
    }



}
