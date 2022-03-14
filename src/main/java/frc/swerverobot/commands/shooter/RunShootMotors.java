package frc.swerverobot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.swerverobot.subsystems.ShooterSubsystem2;

public class RunShootMotors extends CommandBase {
    private final ShooterSubsystem2 shooter;
    private double topMotorSpeed;
    private double botMotorSpeed;

    public RunShootMotors(ShooterSubsystem2 shooter, double topMotorSpeed, double botMotorSpeed) {
        this.shooter = shooter;
        this.topMotorSpeed = topMotorSpeed;
        this.botMotorSpeed = botMotorSpeed;

        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.setShootMotorSpeeds(0, 0);
    }

    @Override
    public void execute() {
        shooter.setShootMotorSpeeds(topMotorSpeed, botMotorSpeed);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }

    public void setSpeeds(double topSpeed, double botSpeed) {
        topMotorSpeed = topSpeed;
        botMotorSpeed = botSpeed;
    }

}
