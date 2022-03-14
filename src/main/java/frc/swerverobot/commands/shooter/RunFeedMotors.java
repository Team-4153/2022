package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ShooterSubsystem2;

public class RunFeedMotors extends CommandBase{
    private final ShooterSubsystem2 shooter;
    private double feedMotorSpeed;

    public RunFeedMotors(ShooterSubsystem2 shooter, double feedMotorSpeed) {
        this.shooter = shooter;
        this.feedMotorSpeed = feedMotorSpeed;

        addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        shooter.setFeedMotorSpeeds(feedMotorSpeed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public void setSpeeds(double feedSpeed) {
        feedMotorSpeed = feedSpeed;
    }

}